package com.lynx.formi.ulsucanteen.presentation.menu.eat;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.List;

public interface EatView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setFoodList(List<Food> foodList);
}
