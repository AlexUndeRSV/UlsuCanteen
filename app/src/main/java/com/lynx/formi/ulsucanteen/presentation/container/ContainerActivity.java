package com.lynx.formi.ulsucanteen.presentation.container;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.ClearLootEvent;
import com.lynx.formi.ulsucanteen.other.router.CustomNavigator;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;
import com.lynx.formi.ulsucanteen.other.utils.TitleProvider;
import com.lynx.formi.ulsucanteen.presentation.tabcontainer.TabContainerFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ru.terrakok.cicerone.Router;

public class ContainerActivity extends MvpAppCompatActivity implements ContainerView, RouterProvider {

    @InjectPresenter
    ContainerPresenter presenter;

    private View loader;

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private MenuItem clearLootItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_main:
                    selectTab("MAIN");
                    hideClearLootItem();
                    break;
                case R.id.item_menu:
                    selectTab("MENU");
                    hideClearLootItem();
                    break;
                case R.id.item_loot:
                    selectTab("BUCKET");
                    showClearLootItem();
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
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            transaction.add(R.id.fragment_container, TabContainerFragment.newInstance(tab), tab);
        }

        if (currentFragment != null) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            if (newFragment.getChildFragmentManager().getBackStackEntryCount() == 0) {
                hideNavigationIcon();
            } else {
                showNavigationIcon();
            }

            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            toolbar.setTitle(((TitleProvider) newFragment).getTitle());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        clearLootItem = menu.findItem(R.id.clear_loot_item);
        hideClearLootItem();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_loot_item:
                EventBus.getDefault().post(new ClearLootEvent());
        }
        return true;
    }

    @Override
    public void onBackPressed() {

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
    protected void onResume() {
        super.onResume();
//        hideClearLootItem();
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
    public void selectItem(int itemId) {
        bottomNavigationView.setSelectedItemId(itemId);
    }

    @Override
    public void hideBaseToolbar() {
        this.toolbar.setVisibility(View.GONE);
    }

    @Override
    public void showBaseToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNavigationIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public void hideNavigationIcon() {
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void showClearLootItem() {
        if(clearLootItem != null) clearLootItem.setVisible(true);
    }

    @Override
    public void hideClearLootItem() {
        if(clearLootItem!= null)clearLootItem.setVisible(false);
    }


    @Override
    public Router getRouter() {
        return App.getGlobalRouter();
    }
}
