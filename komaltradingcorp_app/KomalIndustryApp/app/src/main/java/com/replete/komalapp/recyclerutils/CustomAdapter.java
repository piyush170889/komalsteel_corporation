package com.replete.komalapp.recyclerutils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.Orders;

import java.util.List;

/**
 * Created by MR JOSHI on 09-Apr-16.
 */
public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Orders> ordersItemList;
    private String TAG="CustomAdapter";

    public CustomAdapter(Activity activity, List<Orders> ordersItem) {
        this.activity = activity;
        this.ordersItemList = ordersItem;
    }

    @Override
    public int getCount() {
        return ordersItemList.size();
    }

    @Override
    public Object getItem(int location) {
        return ordersItemList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_item_order, null);

        String[] title=null;
//        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textView_product_title);
        TextView textViewId = (TextView) convertView.findViewById(R.id.textView_order_id);
        TextView textViewStatus = (TextView) convertView.findViewById(R.id.textView_order_status);
//        TextView textViewPrice = (TextView) convertView.findViewById(R.id.textView_order_total_price);

        TextView textViewOrderPlacedDate = (TextView) convertView.findViewById(R.id.textView_order_placed_date);
        TextView textViewOrderDeliveredDate= (TextView) convertView.findViewById(R.id.textView_order_delivered_date);
        TextView textViewOrderAddress1= (TextView) convertView.findViewById(R.id.textView_order_address);
        TextView textViewOrderAddress2= (TextView) convertView.findViewById(R.id.textView_order_address_2);
        TextView textViewOrderAddress3= (TextView) convertView.findViewById(R.id.textView_order_address_3);
        Orders orders = ordersItemList.get(position);

        textViewId.setText(orders.getOrderId());
        textViewStatus.setText(orders.getOrderStatus());
//        textViewPrice.setText(orders.getPackagePrice());
        textViewOrderPlacedDate.setText(orders.getOrderPlacedDateNTime());

        if(orders.getOrderDeliveredOn().equals("null"))
            textViewOrderDeliveredDate.setText("NA");
        else
            textViewOrderDeliveredDate.setText(orders.getOrderDeliveredOn());

        textViewOrderAddress1.setText(orders.getOrderAddress());
        textViewOrderAddress2.setText(orders.getOrderCity()+"-"+orders.getOrderPinCode());
        textViewOrderAddress3.setText(orders.getOrderState() +","+orders.getOrderCountry());

        /*String productName="";
        List<OrderProducts> orderProductsList = orders.getOrderProductsList();

        Log.d(TAG,"size="+orderProductsList.size());
        for (int i = 0; i < orderProductsList.size(); i++) {
            title = new String[orderProductsList.size()];
            OrderProducts orderproducts = orderProductsList.get(i);
            title[i]=orderproducts.getProductTitle();
            productName= productName +title[i]+"\n";
        }
        textViewTitle.setText(productName);*/
        return convertView;
    }

}