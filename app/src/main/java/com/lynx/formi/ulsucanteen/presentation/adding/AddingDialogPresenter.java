package com.lynx.formi.ulsucanteen.presentation.adding;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.NewItemInLootEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;

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
