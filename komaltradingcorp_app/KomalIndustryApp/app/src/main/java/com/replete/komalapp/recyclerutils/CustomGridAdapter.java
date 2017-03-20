package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.MainProductListActivity;
import com.replete.komalapp.activity.ProductDetailNewActivity;
import com.replete.komalapp.rowitem.SubCategory;
import com.replete.komalapp.utils.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 09-Jul-16.
 */
public class CustomGridAdapter extends BaseAdapter {
    private Context mContext;
    //    private Application mAppContext;
//    private DataSet mData = new DataSet();
    private List<SubCategory> subCategoryArrayList = new ArrayList<>();
    //
//    private final Random mRandom;
//    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
//
//    private int newPos = 19;
    private String TAG = "STGVAdapter";

    public CustomGridAdapter(Context mContext, List<SubCategory> subCategoryArrayList) {
        this.mContext = mContext;
//        this.mAppContext = mAppContext;
        this.subCategoryArrayList = subCategoryArrayList;
//        this.mRandom = new Random();
    }


  /*  public void getMoreItem() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.url = mData.url[i];
            item.width = mData.width[i];
            item.height = mData.height[i];
            mItems.add(item);
        }
    }*/

   /* public void getNewItem() {
        Item item = new Item();
        item.url = mData.url[newPos];
        item.width = mData.width[newPos];
        item.height = mData.height[newPos];
        mItems.add(0, item);
        newPos = (newPos - 1) % 19;
    }*/

    @Override
    public int getCount() {
        return subCategoryArrayList == null ? 0 : subCategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        final SubCategory subCategory = subCategoryArrayList.get(position);

        String url = subCategory.getSubCategoryUrl();
//        Log.d(TAG, "url=" + url);
        if (convertView == null) {
            Holder holder = new Holder();
            view = View.inflate(mContext, R.layout.cell_stgv, null);
            holder.img_content = (SquareImageView) view.findViewById(R.id.img_content);
            holder.tv_info = (TextView) view.findViewById(R.id.tv_info);
            view.setTag(holder);
        } else {
            view = convertView;
        }

        Holder holder = (Holder) view.getTag();

        holder.tv_info.setText(subCategory.getSubCategoryName());
     /*   holder.img_content.mHeight = item.height;
        holder.img_content.mWidth = item.width;*/

    /*    double positionHeight = getPositionRatio(position);
        holder.img_content.setHeightRatio(positionHeight);*/

//        Picasso.with(mContext).load(url).into(holder.img_content);
        if (null == url || url.isEmpty()) {
            Picasso.with(mContext).load(R.drawable.circledgrey).into(holder.img_content);
        } else
            Picasso.with(mContext).load(url).into(holder.img_content);



        /**
         * StaggeredGridView has bugs dealing with child TouchEvent
         * You must deal TouchEvent in the child view itself
         **/

        holder.img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailNewActivity.class);
                intent.putExtra("SubCategoryId", subCategory.getSubCategoryId());
                intent.putExtra("CategoryId", subCategory.getCategoryId());
                intent.putExtra("SubCategoryName", subCategory.getSubCategoryName());
                mContext.startActivity(intent);
                if (mContext instanceof MainProductListActivity) {
                    ((MainProductListActivity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        holder.tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;
    }

    class Holder {
        SquareImageView img_content;
        TextView tv_info;
    }

    /*private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
//            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
        // the width
    }*/

}
