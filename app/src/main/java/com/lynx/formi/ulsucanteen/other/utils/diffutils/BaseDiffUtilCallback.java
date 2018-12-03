package com.lynx.formi.ulsucanteen.other.utils.diffutils;

import android.support.v7.util.DiffUtil;

import java.util.List;

public abstract class BaseDiffUtilCallback<T> extends DiffUtil.Callback {

    protected final List<T> oldList;
    protected final List<T> newList;

    public BaseDiffUtilCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

    @Override
    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);
}
