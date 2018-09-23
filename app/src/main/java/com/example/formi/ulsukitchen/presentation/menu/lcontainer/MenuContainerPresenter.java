package com.example.formi.ulsukitchen.presentation.menu.lcontainer;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.other.Screen;

@InjectViewState
public class MenuContainerPresenter extends MvpPresenter<MenuContainerView> {

    public void setRootScreen() {
        App.getLocalRouter().newRootScreen(Screen.CATEGORIES.name());
    }

    public void onBackButtonPressed() {
        App.getLocalRouter().exit();
    }
}
