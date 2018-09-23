package com.example.formi.ulsukitchen.presentation.loot;

import com.arellomobile.mvp.MvpView;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;

import java.util.List;

public interface LootView extends MvpView {
    void setLootList(List<Eat> eatList);
    void notifyItemDeleted(String title, int position);
}
