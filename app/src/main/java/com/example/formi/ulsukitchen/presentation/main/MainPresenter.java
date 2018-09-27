package com.example.formi.ulsukitchen.presentation.main;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }
}
