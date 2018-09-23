package com.example.formi.ulsukitchen.other.itemdecorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearItemDecorator extends RecyclerView.ItemDecoration {

    private int space;

    public LinearItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
        } else if (parent.getChildLayoutPosition(view) == state.getItemCount() - 1) {
            outRect.left = space;
            outRect.top = space / 2;
            outRect.right = space;
            outRect.bottom = space;
        } else {
            outRect.left = space;
            outRect.top = space / 2;
            outRect.right = space;
        }
    }
}
