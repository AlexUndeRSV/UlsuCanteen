package com.lynx.formi.ulsucanteen.other.utils.diffutils;

import com.lynx.formi.ulsucanteen.domain.dataclass.Food;

import java.util.List;

public class FoodDiffUtilCallback<T> extends BaseDiffUtilCallback<T> {


    public FoodDiffUtilCallback(List<T> oldList, List<T> newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        Food foodOld = (Food) oldList.get(oldItemPosition);
        Food foodNew = (Food) newList.get(newItemPosition);

        return foodOld.id.equals(foodNew.id);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Food foodOld = (Food) oldList.get(oldItemPosition);
        Food foodNew = (Food) newList.get(newItemPosition);

        return foodOld.categoryId.equals(foodNew.categoryId)
                && foodOld.count.equals(foodNew.count)
                && foodOld.description.equals(foodNew.description)
                && foodOld.imgUrl.equals(foodNew.imgUrl)
                && foodOld.price.equals(foodNew.price)
                && foodOld.title.equals(foodNew.title);
    }
}
