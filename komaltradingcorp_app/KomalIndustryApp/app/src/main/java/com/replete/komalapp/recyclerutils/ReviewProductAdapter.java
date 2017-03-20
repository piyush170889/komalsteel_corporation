package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.CartActivity;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Replete Android on 9/13/2016.
 */
public class ReviewProductAdapter extends RecyclerView.Adapter<ReviewProductAdapter.CustomViewHolder> {

    CartProducts cartInfoRowItem = new CartProducts();

    private Context mContext;

    private List<CartProducts> cartProductsList;

    DatabaseHandler databaseHandler;

    public ReviewProductAdapter(Context mContext,
                                List<CartProducts> cartProductsArrayList) {

        this.mContext = mContext;

        this.cartProductsList = cartProductsArrayList;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_review_product, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        databaseHandler = new DatabaseHandler(mContext);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {

        cartInfoRowItem = cartProductsList.get(position);

        customViewHolder.textViewProductType.setText(Html.fromHtml(cartInfoRowItem.getProductType()));
        customViewHolder.textViewProductQuantity.setText(Html.fromHtml(cartInfoRowItem.getProductQuantity()));


    }

    @Override
    public int getItemCount() {
        return (null != cartProductsList ? cartProductsList.size() : 0);
    }

    /**
     * CustomHolder
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewProductQuantity;
        private TextView textViewProductType;

        public CustomViewHolder(View itemView) {

            super(itemView);

            textViewProductType = (TextView) itemView.findViewById(R.id.textViewProductType);
            textViewProductQuantity = (TextView) itemView.findViewById(R.id.textViewProductQuantity);
        }
    }


}
