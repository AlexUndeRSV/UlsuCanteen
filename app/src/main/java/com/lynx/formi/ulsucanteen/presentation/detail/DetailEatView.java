package com.lynx.formi.ulsucanteen.presentation.detail;

import com.arellomobile.mvp.MvpView;

public interface DetailEatView extends MvpView {
    void setTitle(String title);
    void setImage(String image);
    void setDescription(String description);
}
