package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.replete.komalapp.R;
import com.replete.komalapp.activity.MainProductListActivity;
import com.replete.komalapp.activity.ProductDetailNewActivity;
import com.replete.komalapp.rowitem.Category;
import com.replete.komalapp.rowitem.SubCategory;

import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerAdapter<CategoryViewHolder, SubCategoryViewHolder> {
    Context context;
    private LayoutInflater mInflator;
    private int selectedPos=0;

    public CategoryAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View categoryView = mInflator.inflate(R.layout.row_item_category, parentViewGroup, false);
        return new CategoryViewHolder(categoryView);
    }

    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View subcategoryView = mInflator.inflate(R.layout.row_item_subcategory, childViewGroup, false);
        return new SubCategoryViewHolder(subcategoryView);
    }

    @Override
    public void onBindParentViewHolder(CategoryViewHolder categoryViewHolder, int position, ParentListItem parentListItem) {
        Category category = (Category) parentListItem;
        categoryViewHolder.bind(category);
    }

    @Override
    public void onBindChildViewHolder(final SubCategoryViewHolder subCategoryViewHolder, final int position, final Object childListItem) {
        SubCategory subCategory = (SubCategory) childListItem;
        subCategoryViewHolder.bind(subCategory);
//        subCategoryViewHolder.itemView.setBackgroundColor(Color.WHITE);
        subCategoryViewHolder.textViewSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                subCategoryViewHolder.itemView.setBackgroundColor(Color.parseColor("#f6f5f4"));
                Log.d("ChildView", "position=" + position + " ChildlistItem= " + ((SubCategory) childListItem).getSubCategoryName());
                Intent intent = new Intent(context, ProductDetailNewActivity.class);
//                intent.putExtra("CategoryOfSubCategoryName", ((SubCategory) childListItem).getCategoryOfSubCategoryName());
                intent.putExtra("SubCategoryName", ((SubCategory) childListItem).getSubCategoryName());
                context.startActivity(intent);
//                subCategoryViewHolder.itemView.setBackgroundColor(Color.WHITE);
                if (context instanceof MainProductListActivity) {
                    ((MainProductListActivity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

    }
}
