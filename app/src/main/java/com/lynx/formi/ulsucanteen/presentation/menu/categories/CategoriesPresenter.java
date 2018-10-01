package com.lynx.formi.ulsucanteen.presentation.menu.categories;


import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CategoriesPresenter extends MvpPresenter<CategoriesView> {

    private Router router;

    public CategoriesPresenter(Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCategories(App.getDBRepository().getCategoryList());
    }

    public void navigateToEatFragment(String id, String title) {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.ID_KEY, id);
        args.putString(Constants.BundleKeys.TITLE_KEY, title);
        router.navigateTo(Screen.EAT.name(), args);
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }
}
