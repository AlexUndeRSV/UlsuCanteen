package com.example.formi.ulsukitchen.presentation.gcontainer;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.events.HideBottomNavigationEvent;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowBottomNavigationEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@InjectViewState
public class ContainerPresenter extends MvpPresenter<ContainerView> {

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onShowLoaderEvent(ShowLoaderEvent event){
        getViewState().showLoader();
    }

    @Subscribe
    public void onHideLoaderEvent(HideLoaderEvent event){
        getViewState().hideLoader();
    }

    @Subscribe
    public void onShowBottomNavigation(ShowBottomNavigationEvent event){
        getViewState().showBottomNavigation();
    }

    @Subscribe
    public void onHideBottomNavigation(HideBottomNavigationEvent event){
        getViewState().hideBottomNavigation();
    }

    @Subscribe
    public void onShowMessage(ShowMessageEvent event){
        getViewState().showMessage(event.message);
    }

    public void setRootScreen() {
        App.getGlobalRouter().newRootScreen(Screen.MAIN.name());
    }

    public void navigateToMenu() {
        App.getGlobalRouter().newRootScreen(Screen.MENU.name());
    }

    public void navigateToMain() {
        App.getGlobalRouter().newRootScreen(Screen.MAIN.name());
    }

    public void navigateToLoot(){
        App.getGlobalRouter().newRootScreen(Screen.LOOT.name());
    }

    public void onBackPressed() {
        App.getGlobalRouter().exit();
    }
}
