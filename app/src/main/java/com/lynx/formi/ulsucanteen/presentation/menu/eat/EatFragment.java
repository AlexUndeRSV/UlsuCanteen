package com.lynx.formi.ulsucanteen.presentation.menu.eat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.itemdecorators.LinearItemDecorator;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.presentation.adding.AddingDialogFragment;
import com.lynx.formi.ulsucanteen.presentation.menu.eat.adapter.EatAdapter;

import java.util.List;

public class EatFragment extends MvpAppCompatFragment implements EatView, EatAdapter.EatClickListener, BackButtonListener {
    public static final String TAG = "EatFragment";
    @InjectPresenter
    EatPresenter presenter;

    @ProvidePresenter
    EatPresenter provideEatPresenter() {
        return new EatPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    private RecyclerView recView;
    private EatAdapter adapter;

    private List<Food> foodList;

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

        presenter.setTitle(title);
        presenter.showToolbarIcon();
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
        adapter.setEatClickListener(this);

        presenter.showToolbarAndBNV();

        recView.setAdapter(adapter);
    }

    @Override
    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        adapter.setFoodList(foodList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onButtonAddClickListener(int position) {
        Bundle args = new Bundle();
        Food food = foodList.get(position);
        args.putParcelable(Constants.BundleKeys.EAT_KEY, food);

        AddingDialogFragment addingDialogFragment = AddingDialogFragment.newInstance(args);
        addingDialogFragment.setCancelable(false);
        addingDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onItemClickListener(int position) {
        Food food = foodList.get(position);
        presenter.navigateToDetail(food.id);
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackButtonPressed();
        return true;
    }
}
