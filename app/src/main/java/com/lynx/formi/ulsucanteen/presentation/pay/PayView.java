package com.lynx.formi.ulsucanteen.presentation.pay;

import com.arellomobile.mvp.MvpView;

public interface PayView extends MvpView {

    void notifyOrderAdded(String key);
}
