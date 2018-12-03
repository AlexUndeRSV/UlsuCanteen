package com.lynx.formi.ulsucanteen.other.utils.diffutils;

import com.lynx.formi.ulsucanteen.domain.dataclass.Category;

import java.util.List;

public class CategoriesDiffUtilCallback<T> extends BaseDiffUtilCallback<T> {

    public CategoriesDiffUtilCallback(List<T> oldList, List<T> newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Category categoryOld = (Category) oldList.get(oldItemPosition);
        Category categoryNew = (Category) newList.get(newItemPosition);

        return categoryOld.getId().equals(categoryNew.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Category categoryOld = (Category) oldList.get(oldItemPosition);
        Category categoryNew = (Category) newList.get(newItemPosition);

        if (categoryOld.getFood() != null && categoryNew.getFood() != null)
            return categoryOld.getFood().equals(categoryNew.getFood())
                    && categoryOld.getImgUrl().equals(categoryNew.getImgUrl())
                    && categoryOld.getTitle().equals(categoryNew.getTitle());
        else
            return categoryOld.getImgUrl().equals(categoryNew.getImgUrl())
                    && categoryOld.getTitle().equals(categoryNew.getTitle());
    }
}
