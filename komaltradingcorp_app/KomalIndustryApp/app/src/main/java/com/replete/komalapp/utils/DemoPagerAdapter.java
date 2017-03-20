package com.replete.komalapp.utils;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.replete.komalapp.R;

import java.util.Random;

/**
 * Created by MR JOSHI on 16-Mar-16.
 */
public class DemoPagerAdapter extends PagerAdapter {
    private final Random random = new Random();
    private final SparseArray<TextView> mHolderArray = new SparseArray<>();
    private int mSize;
    private final int[] GalImages = new int[]{
            R.drawable.kitchen_tool_1,
            R.drawable.kitchenware_1,
            R.drawable.kitchen_tool_2,
    };

    public DemoPagerAdapter() {
        mSize = GalImages.length;
    }

    public DemoPagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mHolderArray.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        //set images as viewpager bkg
       /* view.setBackgroundResource(GalImages[position]);
        View newView = LayoutInflater.from(view.getContext()).inflate(R.layout.empty_view, null);
        view.addView(newView);*/

        //set images as imageview
        ImageView imageView = new ImageView(view.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(GalImages[position]);

        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;
        notifyDataSetChanged();
    }

/*
    private void updateUI(final int fi) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int i = fi;
                int j = i;
                viewpager.setCurrentItem(j++);
            }
        }, 1000);
    }*/

}
