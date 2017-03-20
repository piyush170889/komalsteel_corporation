package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.OrderDetailsActivity;
import com.replete.komalapp.activity.TrackOrderActivity;
import com.replete.komalapp.rowitem.Orders;

import java.util.List;

/**
 * Created by MR JOSHI on 23-Mar-16.
 */
public class TrackOrderAdapter extends RecyclerView.Adapter<TrackOrderAdapter.CustomViewHolder> {
    private Context mContext;
    private List<Orders> ordersList;
    private Orders orderRowItem;
    private OnItemClickListener mItemClickListener;
    private String TAG = "TrackOrderAdapter";

    private View.OnClickListener linkToOrderDetailsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getAdapterPosition();
            Orders orders = ordersList.get(position);
            Intent intent = new Intent(mContext, OrderDetailsActivity.class);
            intent.putExtra("orderId", orders.getOrderId());
//            intent.putExtra("productTitle", orders.getProductTitle());
//            intent.putExtra("packageQuantity", orders.getPackageQuantity());
//            intent.putExtra("packagePrice", orders.getPackagePrice());
            intent.putExtra("orderDeliveredOn", orders.getOrderDeliveredOn());
            intent.putExtra("orderPlacedDateNTime", orders.getOrderPlacedDateNTime());
            intent.putExtra("orderStatus", orders.getOrderStatus());



            Log.d(TAG, "orderId" + orders.getOrderId());
            ((TrackOrderActivity) mContext).startActivity(intent);
        }
    };

    public TrackOrderAdapter(Context mContext, List<Orders> ordersList) {
        this.mContext = mContext;
        this.ordersList = ordersList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_order, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        orderRowItem = ordersList.get(position);
//        customViewHolder.textViewProductTitle.setText(Html.fromHtml(orderRowItem.getProductTitle()));
        customViewHolder.textViewOrderId.setText(Html.fromHtml(orderRowItem.getOrderId()));
        customViewHolder.textViewOrderStatus.setText(Html.fromHtml(orderRowItem.getOrderStatus()));
        /*if (mContext instanceof TrackOrderActivity) {
            if (orderRowItem.getOrderStatus().equals("Shipped")) {
                customViewHolder.imageViewOrderShipped.setBackground(((TrackOrderActivity) mContext).getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
                customViewHolder.buttonCancelOrder.setVisibility(View.GONE);
            } else if (orderRowItem.getOrderStatus().equals("Delivered")) {
                customViewHolder.imageViewOrderShipped.setBackground(((TrackOrderActivity) mContext).getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
                customViewHolder.imageViewOrderDelivered.setBackground(((TrackOrderActivity) mContext).getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
                customViewHolder.buttonCancelOrder.setVisibility(View.GONE);
            } else if (orderRowItem.getOrderStatus().equals("Ordered")) {
                customViewHolder.buttonCancelOrder.setVisibility(View.VISIBLE);
            }
        }*/
        customViewHolder.textViewLinkToOrderDetails.setOnClickListener(linkToOrderDetailsClickListener);
        customViewHolder.textViewLinkToOrderDetails.setTag(customViewHolder);

    }

    @Override
    public int getItemCount() {
        return (null != ordersList ? ordersList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewLinkToOrderDetails;
        private TextView textViewOrderId;
        private TextView textViewOrderStatus;
        private TextView textViewProductTitle;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textViewOrderId = (TextView) itemView.findViewById(R.id.textView_order_id);
            this.textViewOrderStatus = (TextView) itemView.findViewById(R.id.textView_order_status);

            this.textViewLinkToOrderDetails = (TextView) itemView.findViewById(R.id.textView_link_to_order_details);
            this.textViewProductTitle = (TextView) itemView.findViewById(R.id.textView_product_title);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
