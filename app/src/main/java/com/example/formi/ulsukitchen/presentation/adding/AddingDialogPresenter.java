package com.example.formi.ulsukitchen.presentation.adding;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.NewItemInLootEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class AddingDialogPresenter extends MvpPresenter<AddingDialogView> {

    /*public void addToLoot(Eat eat, int count) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        eat.count = String.valueOf(count);
        App.getDBRepository().addEatToLoot(eat);
        EventBus.getDefault().post(new NewItemInLootEvent());
        getViewState().notifyEatAdded(eat.title, String.valueOf(count));
        EventBus.getDefault().post(new HideLoaderEvent());
    }*/
}
