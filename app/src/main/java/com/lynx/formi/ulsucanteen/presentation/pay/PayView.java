package com.lynx.formi.ulsucanteen.presentation.pay;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface PayView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void notifyOrderAdded(String key);
}
