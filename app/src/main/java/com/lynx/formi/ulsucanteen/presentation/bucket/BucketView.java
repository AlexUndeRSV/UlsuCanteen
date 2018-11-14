package com.lynx.formi.ulsucanteen.presentation.bucket;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.List;

public interface BucketView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setLootList(List<Food> foodList);
    @StateStrategyType(SkipStrategy.class)
    void notifyItemDeleted(String title, int position);
    @StateStrategyType(SkipStrategy.class)
    void notifyLootCleared();
    void requestForClear();
}
