package com.example.formi.ulsukitchen.presentation.menu.categories;


import android.os.Bundle;

import com.example.formi.ulsukitchen.App;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.formi.ulsukitchen.other.Constants;
import com.example.formi.ulsukitchen.other.Screen;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class CategoriesPresenter extends MvpPresenter<CategoriesView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCategories(App.getDBRepository().getCategoryList());
    }

    public void navigateToEatFragment(String id) {
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.ID_KEY, id);
        App.getLocalRouter().navigateTo(Screen.EAT.name(), args);
    }

    public void onBackButtonPressed() {
        App.getLocalRouter().exit();
    }
}
