package com.example.formi.ulsukitchen.data.presentation.adding;

import android.view.View;

import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.data.presentation.adding.AddingDialogView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class AddingDialogPresenter extends MvpPresenter<AddingDialogView> {

    /*public void addToLoot(Eat eat, int count) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().addEatToLoot(eat);
        getViewState().eatAdded(eat.title, String.valueOf(count));
        EventBus.getDefault().post(new HideLoaderEvent());
    }*/
}
