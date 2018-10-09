package com.lynx.formi.ulsucanteen.presentation.detail;


import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarEvent;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DetailEatPresenter extends MvpPresenter<DetailEatView> {

    private Router router;

    public DetailEatPresenter(Router router) {
        this.router = router;
    }

    public void hideToolbarAndBNV() {
        EventBus.getDefault().post(new HideBottomNavigationEvent());
        EventBus.getDefault().post(new HideToolbarEvent());
    }

    public void getDate(String id) {
        Food food = App.getDBRepository().getFoodById(id);
        getViewState().setTitle(food.title);
        getViewState().setImage(food.imgUrl);
        getViewState().setDescription(food.description);
    }

    public void onBackPressed(){
        router.exit();
    }
}
