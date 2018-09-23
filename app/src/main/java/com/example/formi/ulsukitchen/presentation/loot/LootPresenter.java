package com.example.formi.ulsukitchen.presentation.loot;


import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.events.HideBottomNavigationEvent;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;

import org.greenrobot.eventbus.EventBus;

@InjectViewState
public class LootPresenter extends MvpPresenter<LootView> {

    public void goToPay() {
        App.getGlobalRouter().navigateTo(Screen.PAY.name());
        EventBus.getDefault().post(new HideBottomNavigationEvent());
    }

    public void getLootList() {
        EventBus.getDefault().post(new ShowLoaderEvent());
        getViewState().setLootList(App.getDBRepository().getLootEatList());
        EventBus.getDefault().post(new HideLoaderEvent());
    }

    public void deleteItem(int position, Eat eat) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        App.getDBRepository().deleteFromLoot(eat.id);
        getViewState().notifyItemDeleted(eat.title, position);
        EventBus.getDefault().post(new HideLoaderEvent());
    }
}
