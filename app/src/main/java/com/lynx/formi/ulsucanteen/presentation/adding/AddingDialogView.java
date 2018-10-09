package com.lynx.formi.ulsucanteen.presentation.adding;

import com.arellomobile.mvp.MvpView;

public interface AddingDialogView extends MvpView {
    void notifyEatAdded(String title, boolean isMax);
}
