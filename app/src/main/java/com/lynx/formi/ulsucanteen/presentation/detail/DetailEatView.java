package com.lynx.formi.ulsucanteen.presentation.detail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailEatView extends MvpView {
    void setTitle(String title);
    void setImage(String image);
    void setDescription(String description);
}
