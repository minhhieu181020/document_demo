package com.lpb.lifecardsdk.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int top,bottom,right,left;

    public SpaceItemDecoration(int top, int bottom, int right, int left) {
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;
    }
}
