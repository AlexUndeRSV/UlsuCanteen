package com.example.formi.ulsukitchen.presentation.loot;

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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.itemdecorators.LinearItemDecorator;
import com.example.formi.ulsukitchen.other.utils.TitleProvider;
import com.example.formi.ulsukitchen.presentation.loot.adapter.LootAdapter;

import java.util.List;

public class LootFragment extends MvpAppCompatFragment implements LootView, TitleProvider {
    public static final String TAG = "LootFragment";
    private final String TITLE = "Корзина";
    @InjectPresenter
    LootPresenter presenter;

    private Button btnGoToPay;

    private RecyclerView recView;
    private LootAdapter adapter;

    private List<Eat> eatList;

    private View emptyLoot;
    private TextView txtAddEat;

    public static LootFragment newInstance(Bundle args) {
        LootFragment fragment = new LootFragment();
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_loot, container, false);
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Eat eat = eatList.get(viewHolder.getAdapterPosition());
            // TODO кастомизировать AlertDialog
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity())
                    .setTitle("Подтверждение действия")
                    .setMessage("Вы действительно хотите удалить \"" + eat.title + "\" из корзины?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        presenter.deleteItem(viewHolder.getAdapterPosition(), eat);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Нет", ((dialog, which) -> {
                        adapter.notifyDataSetChanged();
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

        adapter = new LootAdapter(getActivity());
        recView.setAdapter(adapter);

        presenter.showBNV();

        btnGoToPay = view.findViewById(R.id.btnGoToPay);
        btnGoToPay.setOnClickListener((v) -> presenter.goToPay());

        itemTouchHelper.attachToRecyclerView(recView);
    }

    @Override
    public void notifyItemDeleted(String title, int position) {
        eatList.remove(position);
        adapter.notifyDataSetChanged();
        Snackbar.make(recView, title + " удален(а) из корзины", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", (v) -> {
                })
                .show();
        if (eatList.isEmpty()) {
            emptyLoot.setVisibility(View.VISIBLE);
            recView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }

    @Override
    public void setLootList(List<Eat> eatList) {
        this.eatList = eatList;
        adapter.setEatList(eatList);
        adapter.notifyDataSetChanged();
        if (eatList.isEmpty()) {
            emptyLoot.setVisibility(View.VISIBLE);
            recView.setVisibility(View.GONE);
        } else {
            emptyLoot.setVisibility(View.GONE);
            recView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
