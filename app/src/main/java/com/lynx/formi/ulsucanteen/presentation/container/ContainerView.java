package com.lynx.formi.ulsucanteen.presentation.container;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ContainerView extends MvpView {
    void showLoader();
    void hideLoader();
    void showMessage(String str);
    void hideBottomNavigation();
    void showBottomNavigation();
    void setActionbarTitle(String title);
    void selectItem(int itemId);
    void hideBaseToolbar();
    void showBaseToolbar();
    void showNavigationIcon();
    void hideNavigationIcon();
    void showClearLootItem();
    void hideClearLootItem();
}
