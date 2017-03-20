package com.replete.komalapp.utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by MR JOSHI on 27-Apr-16.
 */

public class FixedSpeedScroller extends Scroller {

    private int mDuration = 2000; // set the timein millisecond what you want for slideshow effect



    public FixedSpeedScroller(Context context) {

        super(context);

    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {

        super(context, interpolator);

    }



    public FixedSpeedScroller(Context context, Interpolator interpolator,

                              boolean flywheel) {

        super(context, interpolator, flywheel);

    }



    @Override

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        super.startScroll(startX, startY, dx, dy, mDuration);

    }



    @Override

    public void startScroll(int startX, int startY, int dx, int dy) {

        super.startScroll(startX, startY, dx, dy, mDuration);

    }

}


