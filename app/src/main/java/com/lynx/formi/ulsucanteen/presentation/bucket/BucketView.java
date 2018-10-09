package com.lynx.formi.ulsucanteen.presentation.bucket;

import com.arellomobile.mvp.MvpView;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.List;

public interface BucketView extends MvpView {
    void setLootList(List<Food> foodList);
    void notifyItemDeleted(String title, int position);

    void notifyLootCleared();

    void requestForClear();
}
