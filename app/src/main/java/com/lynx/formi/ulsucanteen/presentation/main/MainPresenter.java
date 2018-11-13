package com.lynx.formi.ulsucanteen.presentation.main;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void setTitle(final String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void hideToolbarIcon() {
        EventBus.getDefault().post(new HideToolbarIcon());
    }
}
