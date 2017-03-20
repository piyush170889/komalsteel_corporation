package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.CartActivity;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;

import java.util.List;

/**
 * Created by MR JOSHI on 28-Mar-16.
 */
public class ReviewProductsNSubcategoriesAdapter extends RecyclerView.Adapter<ReviewProductsNSubcategoriesAdapter.CustomViewHolder> {

    private Context mContext;
    private List<CartProductNSubCategories> cartProductNSubCategoriesList;
    private CartProductNSubCategories cartProductNSubCategories;
    private ReviewProductAdapter adapter;

    public ReviewProductsNSubcategoriesAdapter(Context mContext, List<CartProductNSubCategories> cartProductNSubCategoriesList) {
        this.mContext = mContext;
        this.cartProductNSubCategoriesList = cartProductNSubCategoriesList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_ro, null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_cart_review_product, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        cartProductNSubCategories = cartProductNSubCategoriesList.get(position);
        customViewHolder.textViewProductName.setText(Html.fromHtml(cartProductNSubCategories.getSubcategoryName()));
//        customViewHolder.textViewQuantity.setText(Html.fromHtml(cartInfoRowItem.getProductQuantity()));
//        customViewHolder.textViewProductType.setText(Html.fromHtml(cartInfoRowItem.getProductType()));
//        customViewHolder.textViewTotalPrice.setText(Html.fromHtml("Rs. " + cartInfoRowItem.getProductTotal()));
//        customViewHolder.imageViewCartProduct.setImageBitmap(cartInfoRowItem.getProductImageBitmap());
//        customViewHolder.setIsRecyclable(true);

        List<CartProducts> cartProductsArrayList = cartProductNSubCategories.getCartProductsList();

        for (int i = 0; i < cartProductsArrayList.size(); i++) {

            CartProducts cartProducts = cartProductsArrayList.get(i);

            adapter = new ReviewProductAdapter(mContext, cartProductsArrayList);
            adapter.notifyDataSetChanged();
            customViewHolder.recyclerViewCartSubcatProducts.setAdapter(adapter);

//            addProductTypesToTable(cartProducts.getProductType(), cartProducts.getProductQuantity(), customViewHolder);

            if (mContext instanceof CartActivity) {
                ((CartActivity) mContext).calculateTotal(Integer.parseInt(cartProducts.getProductQuantity()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != cartProductNSubCategoriesList ? cartProductNSubCategoriesList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductName;
//        private TextView textViewQuantity;
//        private TextView textViewProductType;
        //        private ImageView imageViewCartProduct;
        public RecyclerView recyclerViewCartSubcatProducts;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textViewProductName = (TextView) itemView.findViewById(R.id.textView_product_title);
//            this.textViewQuantity = (TextView) itemView.findViewById(R.id.textView_qty);
//            this.textViewProductType = (TextView) itemView.findViewById(R.id.textView_product_type);
            this.recyclerViewCartSubcatProducts = (RecyclerView) itemView.findViewById(R.id.recycler_view_cart_subcat_products);
            recyclerViewCartSubcatProducts.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerViewCartSubcatProducts.setNestedScrollingEnabled(false);


//            this.textViewTotalPrice = (TextView) itemView.findViewById(R.id.textView_product_price);
//            this.imageViewCartProduct = (ImageView) itemView.findViewById(R.id.imageView_cart_product);
        }
    }
}
