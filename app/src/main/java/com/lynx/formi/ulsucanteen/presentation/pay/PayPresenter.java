package com.lynx.formi.ulsucanteen.presentation.pay;


import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideClearLootItemEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;
import com.lynx.formi.ulsucanteen.presentation.pay.PayView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PayPresenter extends MvpPresenter<PayView> {

    private Router router;

    public PayPresenter(Router router){
        this.router = router;
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public void onBackPressed(){
        router.exit();
    }

    public void showToolbarIcon() {
        EventBus.getDefault().post(new ShowToolbarIcon());
    }

    public void hideClearLootItem() {
        EventBus.getDefault().post(new HideClearLootItemEvent());
    }

    public void hideBNV() {
        EventBus.getDefault().post(new HideBottomNavigationEvent());
    }
}
