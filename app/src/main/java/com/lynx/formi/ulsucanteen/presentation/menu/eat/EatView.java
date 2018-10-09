package com.lynx.formi.ulsucanteen.presentation.menu.eat;

import com.arellomobile.mvp.MvpView;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.List;

public interface EatView extends MvpView {
    void setFoodList(List<Food> foodList);
}
