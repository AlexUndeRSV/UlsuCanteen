package com.example.formi.ulsukitchen.presentation.menu.lcontainer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.BackButtonListener;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.router.CustomNavigator;

public class MenuContainerFragment extends MvpAppCompatFragment implements MenuContainerView, BackButtonListener {
    public static final String TAG = "MenuContainerFragment";
    @InjectPresenter
    MenuContainerPresenter presenter;


    public static MenuContainerFragment newInstance(Bundle args) {
        MenuContainerFragment fragment = new MenuContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private CustomNavigator navigator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        navigator = new CustomNavigator(getChildFragmentManager(), R.id.menu_container, null) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                return Screen.valueOf(screenKey).create((Bundle) data);
            }

            @Override
            protected void showSystemMessage(String message, int type) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                getActivity().finish();
            }

            @Override
            protected int getEnterAnimation(String oldScreenKey, String newScreenKey) {
                return 0;
            }

            @Override
            protected int getExitAnimation(String oldScreenKey, String newScreenKey) {
                return 0;
            }

            @Override
            protected int getPopEnterAnimation(String oldScreenKey, String newScreenKey) {
                return 0;
            }

            @Override
            protected int getPopExitAnimation(String oldScreenKey, String newScreenKey) {
                return 0;
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getLocalNavigatorHolder().setNavigator(navigator);
        presenter.setRootScreen();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_container, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        App.getLocalNavigatorHolder().removeNavigator();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackButtonPressed();
        return true;
    }
}
