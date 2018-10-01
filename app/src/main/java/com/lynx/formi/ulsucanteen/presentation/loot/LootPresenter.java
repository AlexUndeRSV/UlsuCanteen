package com.lynx.formi.ulsucanteen.presentation.loot;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.NewItemInLootEvent;
import com.lynx.formi.ulsucanteen.other.events.SelectItemEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class LootPresenter extends MvpPresenter<LootView> {

    private Router router;

    public LootPresenter(Router router){
        this.router = router;
    }
    
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getLootList();
    }

    public void goToPay() {
        router.navigateTo(Screen.PAY.name());
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
