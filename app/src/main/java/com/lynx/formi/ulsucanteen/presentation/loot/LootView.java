package com.lynx.formi.ulsucanteen.presentation.loot;

import com.arellomobile.mvp.MvpView;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;

import java.util.List;

public interface LootView extends MvpView {
    void setLootList(List<Eat> eatList);
    void notifyItemDeleted(String title, int position);
}
