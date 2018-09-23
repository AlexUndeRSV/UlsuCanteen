package com.example.formi.ulsukitchen.presentation.loot;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.itemdecorators.LinearItemDecorator;
import com.example.formi.ulsukitchen.other.events.ShowBottomNavigationEvent;
import com.example.formi.ulsukitchen.presentation.gcontainer.ContainerActivity;
import com.example.formi.ulsukitchen.presentation.loot.adapter.LootAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class LootFragment extends MvpAppCompatFragment implements LootView {
    public static final String TAG = "LootFragment";
    @InjectPresenter
    LootPresenter presenter;

    private Button btnGoToPay;

    private RecyclerView recView;
    private LootAdapter adapter;

    private List<Eat> eatList;

    public static LootFragment newInstance(Bundle args) {
        LootFragment fragment = new LootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
            presenter.deleteItem(viewHolder.getAdapterPosition(), eat);
        }
    });

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recView = view.findViewById(R.id.recViewLoot);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.addItemDecoration(new LinearItemDecorator(20));

        adapter = new LootAdapter(getActivity());
        recView.setAdapter(adapter);

        EventBus.getDefault().post(new ShowBottomNavigationEvent());

        presenter.getLootList();

        btnGoToPay = view.findViewById(R.id.btnGoToPay);
        btnGoToPay.setOnClickListener((v) -> presenter.goToPay());

        itemTouchHelper.attachToRecyclerView(recView);
    }

    @Override
    public void notifyItemDeleted(String title, int position) {
        eatList.remove(position);
        adapter.notifyDataSetChanged();
        Snackbar.make(recView, title + " удален(а) из корзины", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", (v) -> {})
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((ContainerActivity) getActivity()).setActionBarTitle("Корзина");
    }

    @Override
    public void setLootList(List<Eat> eatList) {
        this.eatList = eatList;
        adapter.setEatList(eatList);
        adapter.notifyDataSetChanged();
    }
}
