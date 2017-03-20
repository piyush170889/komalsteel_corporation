package com.replete.komalapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.replete.komalapp.R;

import java.util.List;
import java.util.Random;


/**
 * Created by MR JOSHI on 16-Mar-16.
 */
public class DemoPagerProductViewAdapter extends PagerAdapter {
    private final Random random = new Random();
    private final SparseArray<TextView> mHolderArray = new SparseArray<>();
    private int mSize;
    private List<String> imageStringList;

    public DemoPagerProductViewAdapter(List<String> imagesList) {
        mSize = imagesList.size();
        this.imageStringList=imagesList;
    }

    public DemoPagerProductViewAdapter(int count) {
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
        ImageView imageView = new ImageView(view.getContext());
        int padding = 10;
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(getBitmapFromString(imageStringList.get(position)));
//        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageStringList.get(position).getBytes(), 0, imageStringList.get(position).length()));
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    /**
     * Convert image to bitmap
     *
     * @param galImage
     * @return
     */
    private Bitmap getBitmapFromString(String galImage) {
        byte[] imageAsBytes = Base64.decode(galImage.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return bitmap;
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

}
