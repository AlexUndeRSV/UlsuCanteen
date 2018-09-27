package com.example.formi.ulsukitchen.presentation.menu.eat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.formi.ulsukitchen.other.utils.BackButtonListener;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.other.utils.RouterProvider;
import com.example.formi.ulsukitchen.presentation.adding.AddingDialogFragment;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.Constants;
import com.example.formi.ulsukitchen.other.events.TitleEvent;
import com.example.formi.ulsukitchen.other.itemdecorators.LinearItemDecorator;
import com.example.formi.ulsukitchen.presentation.menu.eat.adapter.EatAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class EatFragment extends MvpAppCompatFragment implements EatView, EatAdapter.OnButtonAddClickListener, BackButtonListener {
    public static final String TAG = "EatFragment";
    @InjectPresenter
    EatPresenter presenter;

    @ProvidePresenter
    EatPresenter provideEatPresenter() {
        return new EatPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    private RecyclerView recView;
    private EatAdapter adapter;

    private List<Eat> eatList;

    private String id;
    private String title = null;

    public static EatFragment newInstance(Bundle args) {
        EatFragment fragment = new EatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            id = args.getString(Constants.BundleKeys.ID_KEY);
            title = args.getString(Constants.BundleKeys.TITLE_KEY);
        }
        presenter.onCreate(id);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (title != null) {
            presenter.setTitle(title);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eat, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recView = view.findViewById(R.id.recViewEat);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.addItemDecoration(new LinearItemDecorator(20));

        adapter = new EatAdapter(getActivity());
        adapter.setOnButtonAddClickListener(this);

        recView.setAdapter(adapter);
    }

    @Override
    public void setEatList(List<Eat> eatList) {
        this.eatList = eatList;
        adapter.setEatList(eatList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onButtonAddClickListener(int position) {
        Bundle args = new Bundle();
        Eat eat = eatList.get(position);
        args.putParcelable(Constants.BundleKeys.EAT_KEY, eat);

        AddingDialogFragment addingDialogFragment = AddingDialogFragment.newInstance(args);
        addingDialogFragment.setCancelable(false);
        addingDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackButtonPressed();
        return true;
    }
}
