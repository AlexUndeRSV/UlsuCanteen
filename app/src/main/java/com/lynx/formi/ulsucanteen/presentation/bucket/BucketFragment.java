package com.lynx.formi.ulsucanteen.presentation.bucket;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.itemdecorators.LinearItemDecorator;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.lynx.formi.ulsucanteen.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.lynx.formi.ulsucanteen.presentation.bucket.adapter.BucketAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class BucketFragment extends MvpAppCompatFragment implements BucketView, TitleProvider, BucketAdapter.OnCountChangeListener {
    public static final String TAG = "BucketFragment";
    private final String TITLE = "Корзина";
    @InjectPresenter
    BucketPresenter presenter;

    @ProvidePresenter
    BucketPresenter provideLootPresenter() {
        return new BucketPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    private Button btnGoToPay;

    private RecyclerView recView;
    private BucketAdapter adapter;

    private List<Food> foodList;

    private View emptyLoot;
    private TextView txtAddEat;

    private int totalPrice = 0;
    private String totalPriceText;


    public static BucketFragment newInstance(Bundle args) {
        BucketFragment fragment = new BucketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bucket, container, false);
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final Food food = foodList.get(viewHolder.getAdapterPosition());
            // TODO кастомизировать AlertDialog
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity())
                    .setTitle("Подтверждение действия")
                    .setMessage("Вы действительно хотите удалить \"" + food.title + "\" из корзины?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        presenter.deleteItem(viewHolder.getAdapterPosition(), food);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Нет", ((dialog, which) -> {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }))
                    .setCancelable(false);
            final AlertDialog alertDialog = ab.create();
            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.black));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
            });
            alertDialog.show();
        }
    });

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyLoot = view.findViewById(R.id.empty_loot);

        txtAddEat = view.findViewById(R.id.txtAddToLoot);
        txtAddEat.setOnClickListener((v) -> presenter.navigateToEat());

        recView = view.findViewById(R.id.recViewLoot);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.addItemDecoration(new LinearItemDecorator(20));

        adapter = new BucketAdapter(getActivity());
        recView.setAdapter(adapter);

        presenter.showBNV();
        presenter.hideToolbarIcon();

        btnGoToPay = view.findViewById(R.id.btnGoToPay);
        btnGoToPay.setOnClickListener((v) -> presenter.goToPay());

        itemTouchHelper.attachToRecyclerView(recView);

        adapter.setOnCountChangeListener(this);

        presenter.showClearLootItem();
    }

    @Override
    public void notifyItemDeleted(final String title, final int position) {
        if (foodList.isEmpty()) return;
        totalPrice = presenter.getTotalPrice();
//        totalPrice -= Integer.valueOf(foodList.get(position).price) * Integer.valueOf(foodList.get(position).count);
        foodList.remove(position);
        notifyTotalPriceChanged();
        adapter.setFoodList(foodList);
        adapter.notifyDataSetChanged();
        Snackbar.make(recView, title + " удален(а) из корзины", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", (v) -> {
                })
                .show();
        if (foodList.isEmpty()) {
            emptyLoot.setVisibility(View.VISIBLE);
            recView.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifyLootCleared() {
        if (foodList.isEmpty()){
            EventBus.getDefault().post(new HideLoaderEvent());
            return;
        }
        totalPrice = presenter.getTotalPrice();
        foodList.clear();
        notifyTotalPriceChanged();
        adapter.notifyDataSetChanged();

        emptyLoot.setVisibility(View.VISIBLE);
        recView.setVisibility(View.GONE);

        EventBus.getDefault().post(new HideLoaderEvent());
    }

    @Override
    public void requestForClear() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity())
                .setTitle("Подтверждение действия")
                .setMessage("Вы действительно хотите очистить корзину?")
                .setPositiveButton("Да", (dialog, which) -> {
                    presenter.clearLoot();
                    dialog.dismiss();
                })
                .setNegativeButton("Нет", ((dialog, which) -> {
                    dialog.dismiss();
                }))
                .setCancelable(false);
        AlertDialog alertDialog = ab.create();
        alertDialog.setOnShowListener(dialog -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
        });
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }

    @Override
    public void setLootList(final List<Food> foodList) {
        this.foodList = foodList;
        adapter.setFoodList(foodList);
        adapter.notifyDataSetChanged();
        if (foodList.isEmpty()) {
            emptyLoot.setVisibility(View.VISIBLE);
            recView.setVisibility(View.GONE);
        } else {
            emptyLoot.setVisibility(View.GONE);
            recView.setVisibility(View.VISIBLE);
        }
        totalPrice = presenter.getTotalPrice();
        notifyTotalPriceChanged();
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void onIncrementClick(final String id, final int price) {
        totalPrice += price;
        presenter.incrementDatabaseCount(id);
        notifyTotalPriceChanged();
    }

    @Override
    public void onDecrementClick(final String id, final int price) {
        totalPrice -= price;
        presenter.decrementDatabaseCount(id);
        notifyTotalPriceChanged();
    }

    private void notifyTotalPriceChanged() {
        totalPriceText = getActivity().getResources().getString(R.string.go_to_pay) + " (" + String.valueOf(totalPrice) + " руб.)";
        btnGoToPay.setText(totalPriceText);
    }
}
