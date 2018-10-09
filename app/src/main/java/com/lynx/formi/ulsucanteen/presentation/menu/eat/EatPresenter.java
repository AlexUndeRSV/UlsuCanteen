package com.lynx.formi.ulsucanteen.presentation.menu.eat;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.ShowBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class EatPresenter extends MvpPresenter<EatView> {

    private Router router;

    private String id = null;

    public EatPresenter(Router router) {
        this.router = router;
    }

    public void onCreate(String id) {
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getEatListFromDB(id);
    }

    public void getEatListFromDB(String id) {
        getViewState().setFoodList(App.getDBRepository().getFoodList(id));
    }

    public void onBackButtonPressed() {
        router.exit();
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void navigateToDetail(String id) {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.ID_KEY, id);
        router.navigateTo(Screen.DETAIL.name(), args);
    }

    public void showToolbarAndBNV() {
        EventBus.getDefault().post(new ShowBottomNavigationEvent());
        EventBus.getDefault().post(new ShowToolbarEvent());
    }

    public void showToolbarIcon() {
        EventBus.getDefault().post(new ShowToolbarIcon());
    }
}
