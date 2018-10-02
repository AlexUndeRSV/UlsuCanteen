package com.lynx.formi.ulsucanteen.presentation.gcontainer;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.SelectItemEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowMessageEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

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
    public void onHideToolbar(HideToolbarEvent event){
        getViewState().hideBaseToolbar();
    }

    @Subscribe
    public void onShowMessage(ShowMessageEvent event){
        getViewState().showMessage(event.message);
    }

    @Subscribe
    public void onSetTitle(TitleEvent event){
        getViewState().setActionbarTitle(event.title);
    }

    @Subscribe
    public void onSelectBNVItem(SelectItemEvent event){
        getViewState().selectItem(event.itemId);
    }

    @Subscribe
    public void onShowToolbar(ShowToolbarEvent event){
        getViewState().showBaseToolbar();
    }

    @Subscribe
    public void onShowNavigationIcon(ShowToolbarIcon event){
        getViewState().showNavigationIcon();
    }

    @Subscribe
    public void onHideNavigationIcon(HideToolbarIcon event){
        getViewState().hideNavigationIcon();
    }

    public void onBackPressed() {
        App.getGlobalRouter().exit();
    }

    public void setDefaultTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }
}
