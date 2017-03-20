package com.replete.komalapp.recyclerutils;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.SubCategory;

public class SubCategoryViewHolder extends ChildViewHolder {

    protected TextView textViewSubCategory;

    public SubCategoryViewHolder(View itemView) {
        super(itemView);
        textViewSubCategory = (TextView) itemView.findViewById(R.id.textView_subcategory);
    }

    public void bind(SubCategory subCategory) {
        textViewSubCategory.setText(subCategory.getSubCategoryName());
    }
}
