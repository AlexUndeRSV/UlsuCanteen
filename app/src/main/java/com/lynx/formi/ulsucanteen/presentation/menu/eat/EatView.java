package com.lynx.formi.ulsucanteen.presentation.menu.eat;

import com.arellomobile.mvp.MvpView;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;

import java.util.List;

public interface EatView extends MvpView {
    void setEatList(List<Eat> eatList);
}
