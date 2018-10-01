package com.lynx.formi.ulsucanteen.presentation.menu.categories;

import com.arellomobile.mvp.MvpView;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;

import java.util.List;

public interface CategoriesView extends MvpView {

    void setCategories(List<Category> categoryList);

}
