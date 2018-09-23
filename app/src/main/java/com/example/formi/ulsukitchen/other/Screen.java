package com.example.formi.ulsukitchen.other;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.formi.ulsukitchen.presentation.loot.LootFragment;
import com.example.formi.ulsukitchen.presentation.main.MainFragment;
import com.example.formi.ulsukitchen.presentation.menu.categories.CategoriesFragment;
import com.example.formi.ulsukitchen.presentation.menu.eat.EatFragment;
import com.example.formi.ulsukitchen.presentation.menu.lcontainer.MenuContainerFragment;
import com.example.formi.ulsukitchen.presentation.pay.PayFragment;

public enum Screen {
    MAIN,
    CATEGORIES,
    MENU,
    PAY,
    EAT,
    LOOT;

    public MvpAppCompatFragment create(Bundle data) {
        switch (this) {
            case CATEGORIES:
                return CategoriesFragment.newInstance(data);
            case MAIN:
                return MainFragment.newInstance(data);
            case LOOT:
                return LootFragment.newInstance(data);
            case PAY:
                return PayFragment.newInstance(data);
            case EAT:
                return EatFragment.newInstance(data);
            case MENU:
                return MenuContainerFragment.newInstance(data);
            default:
                return null;
        }
    }
}
