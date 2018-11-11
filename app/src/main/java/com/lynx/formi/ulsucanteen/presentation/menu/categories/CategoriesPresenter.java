package com.lynx.formi.ulsucanteen.presentation.menu.categories;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.lynx.formi.ulsucanteen.App;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Constants;
import com.lynx.formi.ulsucanteen.other.Screen;
import com.lynx.formi.ulsucanteen.other.events.HideBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.HideToolbarIcon;
import com.lynx.formi.ulsucanteen.other.events.ShowBottomNavigationEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowMessageEvent;
import com.lynx.formi.ulsucanteen.other.events.TitleEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CategoriesPresenter extends MvpPresenter<CategoriesView> {

    private final Router router;

    private final List<Food> foodList;

    public CategoriesPresenter(final Router router) {
        this.router = router;
        foodList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCategories(App.getDBRepository().getCategoryList());
    }

    public void navigateToEatFragment(final String id, final String title) {
        final Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.ID_KEY, id);
        args.putString(Constants.BundleKeys.TITLE_KEY, title);
        router.navigateTo(Screen.EAT.name(), args);
    }

    public void setTitle(final String title) {
        EventBus.getDefault().post(new TitleEvent(title));
    }

    public ValueEventListener getValueEventListener(final boolean isNeedLoad) {
        if (isNeedLoad) {
            EventBus.getDefault().post(new ShowLoaderEvent());
            EventBus.getDefault().post(new HideBottomNavigationEvent());
        }
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodList.clear();
                final GenericTypeIndicator<List<Category>> t = new GenericTypeIndicator<List<Category>>() {
                };
                final List<Category> categoryList = dataSnapshot.getValue(t);
                for (Category category : categoryList) {
                    if (category.getFood() != null) {
                        foodList.addAll(category.getFood());
                    }
                }
                App.getDBRepository().saveFoodList(foodList);
                getViewState().setCategories(categoryList);
                EventBus.getDefault().post(new HideLoaderEvent());
                EventBus.getDefault().post(new ShowBottomNavigationEvent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                EventBus.getDefault().post(new ShowMessageEvent(databaseError.getMessage()));
                EventBus.getDefault().post(new HideLoaderEvent());
            }
        };
    }

    public void hideToolbarIcon() {
        EventBus.getDefault().post(new HideToolbarIcon());
    }
}
