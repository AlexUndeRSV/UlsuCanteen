package com.lynx.formi.ulsucanteen.presentation.container;

import com.arellomobile.mvp.MvpView;

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
