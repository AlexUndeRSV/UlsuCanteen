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
import com.example.formi.ulsukitchen.BackButtonListener;
import com.example.formi.ulsukitchen.R;
import com.example.formi.ulsukitchen.other.Screen;
import com.example.formi.ulsukitchen.other.router.CustomNavigator;
import com.example.formi.ulsukitchen.presentation.loot.LootFragment;
import com.example.formi.ulsukitchen.presentation.main.MainFragment;
import com.example.formi.ulsukitchen.presentation.menu.categories.CategoriesFragment;
import com.example.formi.ulsukitchen.presentation.menu.lcontainer.MenuContainerFragment;

import java.util.List;

public class ContainerActivity extends MvpAppCompatActivity implements ContainerView {

    @InjectPresenter
    ContainerPresenter presenter;

    private View loader;

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private int currentPageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackPressed());

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (currentPageId == item.getItemId()) {
                return false;
            }
            switch (item.getItemId()) {
                case R.id.item_main:
//                    presenter.navigateToMain();
                    selectTab("MAIN");
                    currentPageId = item.getItemId();
                    break;
                case R.id.item_menu:
                    selectTab("MENU");
//                    presenter.navigateToMenu();
                    currentPageId = item.getItemId();
                    break;
                case R.id.item_loot:
                    selectTab("LOOT");
//                    presenter.navigateToLoot();
                    currentPageId = item.getItemId();
                    break;
            }
            return true;
        });

        loader = findViewById(R.id.pre_loader);

        App.getGlobalNavigatorHolder().setNavigator(navigator);
        presenter.setRootScreen();
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

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return;

        FragmentTransaction transaction = fm.beginTransaction();
        if (newFragment == null) {
            switch (tab){
                case "MAIN":
                    transaction.add(R.id.fragment_container, new MainFragment(), tab);
                    break;
                case "MENU":
                    transaction.add(R.id.fragment_container, new CategoriesFragment(), tab);
                    break;
                case "LOOT":
                    transaction.add(R.id.fragment_container, new LootFragment(), tab);
                    break;
            }
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
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
//        super.onBackPressed();
        // TODO отладить навигацию с помощью cicerone (см. 'sample app')
        // TODO откорректировать работу onBackPressed()

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

        if (fragment != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1 && fragment.getChildFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            }
        }

        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            presenter.onBackPressed();
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getGlobalNavigatorHolder().removeNavigator();
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
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


}
