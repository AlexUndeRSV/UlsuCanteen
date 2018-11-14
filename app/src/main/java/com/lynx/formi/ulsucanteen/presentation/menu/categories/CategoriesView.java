package com.lynx.formi.ulsucanteen.presentation.menu.categories;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;

import java.util.List;

public interface CategoriesView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCategories(List<Category> categoryList);

}
