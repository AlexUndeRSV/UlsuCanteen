package com.example.formi.ulsukitchen;

import android.app.Application;

import com.example.formi.ulsukitchen.data.repositories.DBRepository;
import com.example.formi.ulsukitchen.other.cicerone.LocalCiceroneHolder;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App INSTANCE;

    private Cicerone<Router> ciceroneGlobal;

    private LocalCiceroneHolder ciceroneHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        ciceroneGlobal = Cicerone.create();
        ciceroneHolder = new LocalCiceroneHolder();
    }

    public static LocalCiceroneHolder getCiceroneHolder(){
        return INSTANCE.ciceroneHolder;
    }

    public static NavigatorHolder getGlobalNavigatorHolder() {
        return INSTANCE.ciceroneGlobal.getNavigatorHolder();
    }

    public static Router getGlobalRouter() {
        return INSTANCE.ciceroneGlobal.getRouter();
    }

    final private DBRepository dbRepository = new DBRepository(this);
    public static DBRepository getDBRepository() {
        return INSTANCE.dbRepository;
    }

}