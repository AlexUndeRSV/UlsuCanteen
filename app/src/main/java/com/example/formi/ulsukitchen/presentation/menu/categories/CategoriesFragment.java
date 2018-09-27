package com.example.formi.ulsukitchen.presentation.menu.categories;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.formi.ulsukitchen.other.utils.TitleProvider;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.other.utils.RouterProvider;
import com.example.formi.ulsukitchen.domain.dataclass.Category;
import com.example.formi.ulsukitchen.other.events.TitleEvent;
import com.example.formi.ulsukitchen.other.itemdecorators.GridItemDecorator;
import com.example.formi.ulsukitchen.presentation.menu.categories.adapter.CategoryAdapter;

import org.greenrobot.eventbus.EventBus;

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

    List<Category> categoryList;

    public static CategoriesFragment newInstance(Bundle args) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recView = view.findViewById(R.id.recViewCategories);
        recView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recView.addItemDecoration(new GridItemDecorator(
                20));

        /*App.getDBRepository().addCategory(new Category("https://wallbox.ru/wallpapers/main/201241/eda-ed56f41d6f95.jpg", "Первое", "1"));
        App.getDBRepository().addCategory(new Category("http://puzzleit.ru/files/puzzles/110/110227/_original.jpg", "Второе", "2"));
        App.getDBRepository().addCategory(new Category("https://w-dog.ru/wallpapers/5/4/535619069087430.jpg", "Салаты", "3"));
        App.getDBRepository().addCategory(new Category("https://mota.ru/upload/wallpapers/source/2013/10/24/19/03/37906/9PSSW1BFnn.jpg", "Десерты", "4"));
        App.getDBRepository().addCategory(new Category("http://mastereat.ru/wp-content/uploads/2010/03/%D0%97%D0%B0%D0%BA%D1%83%D1%81%D0%BA%D0%B0-%D0%B8%D0%B7-%D0%B1%D0%B0%D0%BA%D0%BB%D0%B0%D0%B6%D0%B0%D0%BD%D0%BE%D0%B2..jpg", "Закуски", "5"));

        Eat eat = new Eat();
        eat.price = "122";
        eat.id = "1";
        eat.categoryId = "1";
        eat.title = "Суп гороховый";
        eat.imgUrl = "https://www.gastronom.ru/binfiles/images/20150327/b068e980.jpg";
        App.getDBRepository().addEat(eat);

        eat.price = "321";
        eat.id = "2";
        eat.categoryId = "2";
        eat.title = "Пюрешка";
        eat.imgUrl = "http://kuking.net/resize/197/600/w/uploads/content/1403255083.jpg";
        App.getDBRepository().addEat(eat);

        Eat eat1 = new Eat();
        eat1.price = "70";
        eat1.id = "3";
        eat1.categoryId = "2";
        eat1.title = "Мокороны 'Al-dsa-ja-perdole, sipodal tapok sosichko'";
        eat1.imgUrl = "https://i.ytimg.com/vi/6IpR7_MKsoQ/hqdefault.jpg";
        App.getDBRepository().addEat(eat1);*/

        adapter = new CategoryAdapter(getActivity());
        adapter.setOnCategoryClickListener(this);
        recView.setAdapter(adapter);
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
