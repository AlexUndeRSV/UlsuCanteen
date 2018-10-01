package com.lynx.formi.ulsucanteen.other;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.lynx.formi.ulsucanteen.presentation.detail.DetailEatFragment;
import com.lynx.formi.ulsucanteen.presentation.loot.LootFragment;
import com.lynx.formi.ulsucanteen.presentation.main.MainFragment;
import com.lynx.formi.ulsucanteen.presentation.menu.categories.CategoriesFragment;
import com.lynx.formi.ulsucanteen.presentation.menu.eat.EatFragment;
import com.lynx.formi.ulsucanteen.presentation.pay.PayFragment;

public enum Screen {
    MAIN,
    CATEGORIES,
    MENU,
    PAY,
    EAT,
    DETAIL,
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
            case DETAIL:
                return DetailEatFragment.newInstance(data);
            default:
                return null;
        }
    }
}
