package com.replete.komalapp.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.replete.komalapp.R;

/**
 * Created by MR JOSHI on 29-Apr-16.
 */
public class VerticalDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public VerticalDividerItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop() + 10;

        int bottom = parent.getHeight() - parent.getPaddingBottom() - 10;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
