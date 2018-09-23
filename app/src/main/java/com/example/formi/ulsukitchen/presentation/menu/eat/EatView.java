package com.example.formi.ulsukitchen.presentation.menu.eat;

import com.arellomobile.mvp.MvpView;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;

import java.util.List;

public interface EatView extends MvpView {
    void setEatList(List<Eat> eatList);
}
