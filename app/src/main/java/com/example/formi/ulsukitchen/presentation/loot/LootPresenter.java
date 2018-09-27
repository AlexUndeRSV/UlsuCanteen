package com.example.formi.ulsukitchen.presentation.loot;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.events.HideBottomNavigationEvent;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.NewItemInLootEvent;
import com.example.formi.ulsukitchen.other.events.SelectItemEvent;
import com.example.formi.ulsukitchen.other.events.ShowBottomNavigationEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;
import com.example.formi.ulsukitchen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@InjectViewState
public class LootPresenter extends MvpPresenter<LootView> {
    
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getLootList();
    }

    public void goToPay() {
        App.getGlobalRouter().navigateTo(Screen.PAY.name());
        EventBus.getDefault().post(new HideBottomNavigationEvent());
    }

    private void getLootList() {
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


    public void onStart() {
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNotifyLoot(NewItemInLootEvent event) {
        getViewState().setLootList(App.getDBRepository().getLootEatList());
    }

    public void showBNV() {
        EventBus.getDefault().post(new ShowBottomNavigationEvent());
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void navigateToEat() {
        EventBus.getDefault().post(new SelectItemEvent(R.id.item_menu));
    }
}
