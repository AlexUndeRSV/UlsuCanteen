package com.lynx.formi.ulsucanteen.presentation.tabcontainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.cicerone.LocalNavigator;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.Screen;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;

public class TabContainerFragment extends MvpAppCompatFragment implements TabContainerView, BackButtonListener, RouterProvider, TitleProvider {
    public static final String TAG = "TabContainerFragment";
    @InjectPresenter
    TabContainerPresenter presenter;

    private Navigator navigator;

    private String title = null;

    private String containerName = null;

    public static TabContainerFragment newInstance(String name) {
        TabContainerFragment fragment = new TabContainerFragment();

        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.CONTAINER_NAME_KEY, name);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            Bundle args = getArguments();

            containerName = args.getString(Constants.BundleKeys.CONTAINER_NAME_KEY);
        }
    }

    public Navigator getNavigator() {
        if (navigator == null) {
            navigator = new LocalNavigator(getActivity(), getChildFragmentManager(), R.id.fragment_container_local);
        }
        return navigator;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.fragment_container_local) == null) {
            switch (containerName) {
                case "MAIN":
                    presenter.replaceScreen(Screen.MAIN.name(), containerName);
                    title = "Главная";
                    break;
                case "MENU":
                    presenter.replaceScreen(Screen.CATEGORIES.name(), containerName);
                    title = "Меню";
                    break;
                case "BUCKET":
                    presenter.replaceScreen(Screen.BUCKET.name(), containerName);
                    title = "Корзина";
                    break;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.getCicerone(containerName).getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.getCicerone(containerName).getNavigatorHolder().removeNavigator();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_container_local);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            App.getGlobalRouter().exit();
            return true;
        }
    }

    @Override
    public Router getRouter() {
        return presenter.getRouter();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
