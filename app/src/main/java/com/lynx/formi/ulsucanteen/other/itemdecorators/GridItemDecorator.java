package com.lynx.formi.ulsucanteen.other.itemdecorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecorator extends RecyclerView.ItemDecoration {

    private int space;

    public GridItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = space + (space / 2);
            outRect.bottom = space;
        } else {
            outRect.left = space / 2;
            outRect.right = space;
            outRect.bottom = space;
        }

        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
