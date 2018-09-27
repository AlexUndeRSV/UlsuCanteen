package com.example.formi.ulsukitchen.presentation.pay;


import com.example.formi.ulsukitchen.other.events.TitleEvent;
import com.example.formi.ulsukitchen.presentation.pay.PayView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class PayPresenter extends MvpPresenter<PayView> {

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }
}
