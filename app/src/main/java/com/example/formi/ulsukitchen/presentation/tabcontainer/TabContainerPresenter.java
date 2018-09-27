package com.example.formi.ulsukitchen.presentation.tabcontainer;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.App;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TabContainerPresenter extends MvpPresenter<TabContainerView> {

    private Cicerone<Router> cicerone = null;

    public Cicerone<Router> getCicerone(String containerName) {
        this.cicerone = App.getCiceroneHolder().getCicerone(containerName);
        return cicerone;
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public void replaceScreen(String screenKey, String containerName) {
        if(cicerone == null){
            cicerone = App.getCiceroneHolder().getCicerone(containerName);
        }
        cicerone.getRouter().replaceScreen(screenKey);
    }
}
