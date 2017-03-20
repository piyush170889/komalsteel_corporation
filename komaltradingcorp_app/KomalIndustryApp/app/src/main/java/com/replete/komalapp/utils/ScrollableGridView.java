package com.replete.komalapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by MR JOSHI on 11-Jul-16.
 */
public class ScrollableGridView extends GridView {

    public ScrollableGridView(Context context) {
        super(context);
    }

    public ScrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        // Called when a child does not want this parent and its ancestors to intercept touch events.
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }
}
