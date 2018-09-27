package com.example.formi.ulsukitchen.presentation.menu.eat;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.events.HideLoaderEvent;
import com.example.formi.ulsukitchen.other.events.ShowLoaderEvent;
import com.example.formi.ulsukitchen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class EatPresenter extends MvpPresenter<EatView> {

    private Router router;

    private String id = null;

    public EatPresenter(Router router) {
        this.router = router;
    }

    public void onCreate(String id) {
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getEatListFromDB(id);
    }

    public void getEatListFromDB(String id) {
        getViewState().setEatList(App.getDBRepository().getEatList(id));
    }

    public void onBackButtonPressed() {
        router.exit();
    }

    public void setTitle(String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

}
