package com.example.formi.ulsukitchen.presentation.gcontainer;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.formi.ulsukitchen.App;
import com.example.formi.ulsukitchen.other.utils.BackButtonListener;
import com.example.formi.ulsukitchen.other.utils.TitleProvider;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.other.utils.RouterProvider;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.events.TitleEvent;
import com.example.formi.ulsukitchen.other.router.CustomNavigator;
import com.example.formi.ulsukitchen.presentation.tabcontainer.TabContainerFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ru.terrakok.cicerone.Router;

public class ContainerActivity extends MvpAppCompatActivity implements ContainerView, RouterProvider {

    @InjectPresenter
    ContainerPresenter presenter;

    private View loader;

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_main:
                    selectTab("MAIN");
                    break;
                case R.id.item_menu:
                    selectTab("MENU");
                    break;
                case R.id.item_loot:
                    selectTab("LOOT");
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.item_main);

        loader = findViewById(R.id.pre_loader);

        App.getGlobalNavigatorHolder().setNavigator(navigator);
        presenter.setDefaultTitle("Главная");
    }

    private void selectTab(String tab) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = null;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    currentFragment = f;
                    break;
                }
            }
        }
        Fragment newFragment = fm.findFragmentByTag(tab);

        if (currentFragment != null && newFragment != null && currentFragment == newFragment)
            return;

        FragmentTransaction transaction = fm.beginTransaction();
        if (newFragment == null) {
            transaction.add(R.id.fragment_container, TabContainerFragment.newInstance(tab), tab);
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            toolbar.setTitle(((TitleProvider)newFragment).getTitle());
            transaction.show(newFragment);
        }
        transaction.commitNow();
    }


    private CustomNavigator navigator = new CustomNavigator(getSupportFragmentManager(), R.id.fragment_container, null) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            return Screen.valueOf(screenKey).create((Bundle) data);
        }

        @Override
        protected void showSystemMessage(String message, int type) {
            Toast.makeText(ContainerActivity.this, message, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void exit() {
            finish();
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

    @Override
    public void onBackPressed() {
        // TODO Подправить Clean Architecture

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    fragment = f;
                    break;
                }
            }
        }

        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            presenter.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getGlobalNavigatorHolder().removeNavigator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String str) {
        Snackbar.make(loader, str, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", (v) -> {
                })
                .show();
    }

    @Override
    public void hideBottomNavigation() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void showBottomNavigation() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setActionbarTitle(String title) {
        toolbar.setTitle(title);
    }


    @Override
    public Router getRouter() {
        return App.getGlobalRouter();
    }
}
