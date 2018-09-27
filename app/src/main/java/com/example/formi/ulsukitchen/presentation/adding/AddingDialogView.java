package com.example.formi.ulsukitchen.presentation.adding;

import com.arellomobile.mvp.MvpView;

public interface AddingDialogView extends MvpView {
    void notifyEatAdded(String title, String count);
}
