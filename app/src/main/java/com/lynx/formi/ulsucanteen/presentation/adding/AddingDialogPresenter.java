package com.lynx.formi.ulsucanteen.presentation.adding;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class AddingDialogPresenter extends MvpPresenter<AddingDialogView> {

    /*public void addToLoot(Food eat, int count) {
        EventBus.getDefault().post(new ShowLoaderEvent());
        eat.count = String.valueOf(count);
        App.getDBRepository().addFoodToLoot(eat);
        EventBus.getDefault().post(new NewItemInLootEvent());
        getViewState().notifyEatAdded(eat.title, String.valueOf(count));
        EventBus.getDefault().post(new HideLoaderEvent());
    }*/
}
