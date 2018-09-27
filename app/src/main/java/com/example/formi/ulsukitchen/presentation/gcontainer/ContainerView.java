package com.example.formi.ulsukitchen.presentation.gcontainer;

import com.arellomobile.mvp.MvpView;

public interface ContainerView extends MvpView {
    void showLoader();
    void hideLoader();
    void showMessage(String str);
    void hideBottomNavigation();
    void showBottomNavigation();
    void setActionbarTitle(String title);
}
