package com.example.formi.ulsukitchen.presentation.menu.categories;

import com.arellomobile.mvp.MvpView;
import com.example.formi.ulsukitchen.domain.dataclass.Category;

import java.util.List;

public interface CategoriesView extends MvpView {

    void setCategories(List<Category> categoryList);

}
