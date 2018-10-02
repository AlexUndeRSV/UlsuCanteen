package com.lynx.formi.ulsucanteen.presentation.menu.categories;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.other.itemdecorators.GridItemDecorator;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.presentation.menu.categories.adapter.CategoryAdapter;

import java.util.List;

public class CategoriesFragment extends MvpAppCompatFragment implements CategoriesView, CategoryAdapter.OnCategoryClickListener, TitleProvider {
    public static final String TAG = "CategoriesFragment";
    private final String TITLE = "Меню";
    @InjectPresenter
    CategoriesPresenter presenter;

    @ProvidePresenter
    CategoriesPresenter provideCategoriesPresenter() {
        return new CategoriesPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    private RecyclerView recView;
    private CategoryAdapter adapter;

    private List<Category> categoryList;

    private DatabaseReference dbReference;


    public static CategoriesFragment newInstance(Bundle args) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.hideToolbarIcon();

        recView = view.findViewById(R.id.recViewCategories);
        recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recView.addItemDecoration(new GridItemDecorator(
                20));

        adapter = new CategoryAdapter(getActivity());
        adapter.setOnCategoryClickListener(this);
        recView.setAdapter(adapter);

        boolean isNeedLoad = false;
        if (categoryList != null && categoryList.isEmpty()) isNeedLoad = true;

        dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child("categories").addValueEventListener(presenter.getValueEventListener(categoryList == null || isNeedLoad));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
    }


    @Override
    public void setCategories(List<Category> categoryList) {
        this.categoryList = categoryList;
        adapter.setCategoryList(categoryList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(String id, String title) {
        presenter.navigateToEatFragment(id, title);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
