package com.lynx.formi.ulsucanteen;

import android.app.Application;

import com.lynx.formi.ulsucanteen.data.repositories.DBRepository;
import com.lynx.formi.ulsucanteen.other.cicerone.LocalCiceroneHolder;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App INSTANCE;

    private final Cicerone<Router> ciceroneGlobal = Cicerone.create();

    private final LocalCiceroneHolder ciceroneHolder = new LocalCiceroneHolder();

    private final DBRepository dbRepository = new DBRepository(this);

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
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

    public static DBRepository getDBRepository() {
        return INSTANCE.dbRepository;
    }

}