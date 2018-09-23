package com.example.formi.ulsukitchen.presentation.menu.eat;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class EatPresenter extends MvpPresenter<EatView> {

    public void getEatListFromDB(String id) {
        getViewState().setEatList(App.getDBRepository().getEatList(id));
    }

    public void onBackButtonPressed() {
        App.getGlobalRouter().exit();
    }
}
