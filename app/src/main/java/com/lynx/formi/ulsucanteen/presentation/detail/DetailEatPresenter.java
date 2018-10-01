package com.lynx.formi.ulsucanteen.presentation.detail;


import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarEvent;
import com.lynx.formi.ulsucanteen.presentation.detail.DetailEatView;
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
        Eat eat = App.getDBRepository().getEatById(id);
        getViewState().setTitle(eat.title);
        getViewState().setImage(eat.imgUrl);
        getViewState().setDescription(eat.description);
    }

    public void onBackPressed(){
        router.exit();
    }
}
