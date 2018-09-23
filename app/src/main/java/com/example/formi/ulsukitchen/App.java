package com.example.formi.ulsukitchen;

import android.app.Application;

import com.example.formi.ulsukitchen.data.repositories.DBRepository;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App INSTANCE;

    private Cicerone<Router> ciceroneGlobal;
    private Cicerone<Router> ciceroneLocal;


    //----------------SharedPrefRep--------------------------------
    /*private SharedPrefRepository sharedPrefRepository;

    public static SharedPrefRepository getSharPref(){
        return INSTANCE.sharedPrefRepository;
    }*/
    //----------------SharedPrefRep--------------------------------

    //----------------ciceroneGlobal-------------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
//        sharedPrefRepository = new SharedPrefRepository(getApplicationContext());
        INSTANCE = this;
        ciceroneGlobal = Cicerone.create();
        ciceroneLocal = Cicerone.create();
    }

    public static NavigatorHolder getGlobalNavigatorHolder() {
        return INSTANCE.ciceroneGlobal.getNavigatorHolder();
    }

    public static Router getGlobalRouter() {
        return INSTANCE.ciceroneGlobal.getRouter();
    }

    public static NavigatorHolder getLocalNavigatorHolder() {
        return INSTANCE.ciceroneLocal.getNavigatorHolder();
    }

    public static Router getLocalRouter() {
        return INSTANCE.ciceroneLocal.getRouter();
    }
    //---------------ciceroneGlobal--------------------------------------

    //-----------------------------------------------------

    /*final private NetworkService networkService = new NetworkService();
    public static NetworkService getNetworkService() {
        return INSTANCE.networkService;
    }*/

    final private DBRepository dbRepository = new DBRepository(this);
    public static DBRepository getDBRepository() {
        return INSTANCE.dbRepository;
    }

}