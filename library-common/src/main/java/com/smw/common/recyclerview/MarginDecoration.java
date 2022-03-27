package com.smw.common.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.smw.common.utils.DensityUtils;

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        margin = DensityUtils.dip2px(context,6);
    }

    @Override

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0

        int position = parent.getChildLayoutPosition(view);
        int size = parent.getChildCount();

        outRect.set(0, margin, 0, 0);

    }
}