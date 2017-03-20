package com.replete.komalapp.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.OrderProducts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MR JOSHI on 25-Mar-16.
 */
public class OrderDetailsActivity extends AppCompatActivity {
    private String TAG = "orderDetailsActivity";
    List<OrderProducts> orderProductsList = new ArrayList<>();
    private TableLayout tableOrderedProducts;
    private int tableIdCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");

        Bundle b = getIntent().getExtras();
        Log.d(TAG, "orderId= " + b.getString("orderId"));
        TextView textViewOrderId = (TextView) findViewById(R.id.textView_order_id);
        TextView textViewOrderStatus = (TextView) findViewById(R.id.textView_order_status);
        TextView textViewOrderPlacedDateTime = (TextView) findViewById(R.id.textView_order_placed_date_time);
        TextView textViewOrderDeliveredDateTime = (TextView) findViewById(R.id.textView_order_delivered_date_time);
        TextView textViewAddress1 = (TextView) findViewById(R.id.textView_order_address);
        TextView textViewAddress2 = (TextView) findViewById(R.id.textView_order_address_2);
        TextView textViewAddress3 = (TextView) findViewById(R.id.textView_order_address_3);
        TextView textViewOrderLrNo = (TextView) findViewById(R.id.textView_order_Lr_No);
        TextView textViewOrderLrDate = (TextView) findViewById(R.id.textView_order_Lr_Date);
        TextView textViewNumberOfCartonLoaded = (TextView) findViewById(R.id.textView_number_of_carton_loaded);
        ImageView imageViewOrderd = (ImageView) findViewById(R.id.imageView_order_accepted);
        ImageView imageViewShipped = (ImageView) findViewById(R.id.imageView_order_shipped);
        ImageView imageViewDelivered = (ImageView) findViewById(R.id.imageView_order_delivered);
        tableOrderedProducts = (TableLayout) findViewById(R.id.tableOrderedProducts);

        //initialize table
//        initTable();

        //set values to textViews
        textViewOrderId.setText(b.getString("orderId"));
//        textViewProductPrice.setText("Rs. " + b.getString("packagePrice"));
        textViewOrderPlacedDateTime.setText(b.getString("orderPlacedDateNTime"));
        textViewAddress1.setText(b.getString("orderAddress"));
        textViewAddress2.setText(b.getString("orderCity") + "-" + b.getString("orderPinCode"));
        textViewAddress3.setText(b.getString("orderState") + "," + b.getString("orderCountry"));

        // TODO: 9/4/2016 add Lr No from previous page
        textViewOrderLrNo.setText(b.getString("LrNo"));
        textViewOrderLrDate.setText(b.getString("LrDate"));
        textViewNumberOfCartonLoaded.setText(b.getString("NumberOfCartonLoaded"));


        if ((b.getString("orderDeliveredOn").equals("null")))
            textViewOrderDeliveredDateTime.setText("NA");
        else
            textViewOrderDeliveredDateTime.setText(b.getString("orderDeliveredOn"));

        String orderStatus = b.getString("orderStatus");
        textViewOrderStatus.setText(orderStatus);

        //Get orderProductList from bundle
        String arrayListString = b.getString("orderProductsList");
        //convert string to arraylist using GSON
        Gson gson = new Gson();
        OrderProducts[] orderProductsItems = gson.fromJson(arrayListString,
                OrderProducts[].class);
        orderProductsList = Arrays.asList(orderProductsItems);

        for (int i = 0; i < orderProductsList.size(); i++) {
            OrderProducts orderproducts = orderProductsList.get(i);
            addToTable(orderproducts.getProductTitle(), orderproducts.getProductType(), orderproducts.getPackageQuantity());
        }

        if (orderStatus.equals("Dispatched")) {
            if (Build.VERSION.SDK_INT >= 22)
                imageViewShipped.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary, null));
            else if (Build.VERSION.SDK_INT >= 16)
                imageViewShipped.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
            else
                imageViewShipped.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
//            buttonCancelOrder.setVisibility(View.GONE);
        } else if (orderStatus.equals("Completed")) {
            if (Build.VERSION.SDK_INT >= 22) {
                imageViewShipped.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary, null));
                imageViewDelivered.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary, null));
            } else if (Build.VERSION.SDK_INT >= 16) {
                imageViewShipped.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
                imageViewDelivered.setBackground(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
            } else {
                imageViewShipped.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
                imageViewDelivered.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.order_status_img_bkg_primary));
            }
//            buttonCancelOrder.setVisibility(View.GONE);
        } else if (orderStatus.equals("Booked")) {
//            buttonCancelOrder.setVisibility(View.VISIBLE);
        }
    }

    private void addToTable(String productTitle, String type, String packageQuantity) {
        TableRow tr = new TableRow(OrderDetailsActivity.this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));


     /*   TextView tvId = new TextView(OrderDetailsActivity.this);
        tvId.setId(0 + 0);
        tvId.setText(tableIdCount + "");
        tvId.setTextColor(Color.BLACK);
        tvId.setPadding(2, 5, 2, 0);
        tvId.setGravity(Gravity.CENTER);
        tvId.setBackgroundResource(R.drawable.cell_shape);
        tr.addView(tvId);*/

        TextView tvService = new TextView(OrderDetailsActivity.this);
        tvService.setId(0 + 0);
        tvService.setText(productTitle);
        tvService.setPadding(2, 5, 2, 0);
        tvService.setGravity(Gravity.CENTER);
        tvService.setBackgroundResource(R.drawable.cell_shape);
        tr.addView(tvService);


        TextView tvType = new TextView(OrderDetailsActivity.this);
        tvType.setId(1 + 0);
        tvType.setText(type);
        tvType.setPadding(2, 5, 2, 0);
        tvType.setPadding(2, 5, 2, 0);
        tvType.setGravity(Gravity.CENTER);
        tvType.setBackgroundResource(R.drawable.cell_shape);
        tr.addView(tvType);


        TextView tvQuantity = new TextView(OrderDetailsActivity.this);
        tvQuantity.setId(2 + 0);
        tvQuantity.setText(packageQuantity);
        tvQuantity.setPadding(2, 5, 2, 0);
        tvQuantity.setGravity(Gravity.CENTER);
        tvQuantity.setBackgroundResource(R.drawable.cell_shape);
        tr.addView(tvQuantity);

      /*  TextView perPrice = new TextView(OrderDetailsActivity.this);
        perPrice.setId(2 + 0);
        perPrice.setText(productPrice);
        perPrice.setPadding(2, 5, 2, 0);
        perPrice.setGravity(Gravity.CENTER);
        perPrice.setBackgroundResource(R.drawable.cell_shape);
        tr.addView(perPrice);*/

        // finally add this to the table row
        tableOrderedProducts.addView(tr, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tableIdCount++;
    }

   /* private void initTable() {

        TableRow tr_head = new TableRow(this);

        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f));

       *//* TextView labelInvoiceNo = new TextView(this);
        labelInvoiceNo.setText("Sr. No.");
        labelInvoiceNo.setTextColor(Color.BLACK);
        labelInvoiceNo.setPadding(2, 5, 2, 5);
        labelInvoiceNo.setGravity(Gravity.CENTER);
        labelInvoiceNo.setBackgroundResource(R.drawable.cell_shape);
        tr_head.addView(labelInvoiceNo);// add the column to the table row here
*//*


        TextView labelInfo = new TextView(this);
        labelInfo.setText(" Product"); // set the text for the header
        labelInfo.setTypeface(null, Typeface.BOLD);
        labelInfo.setGravity(Gravity.CENTER);
        labelInfo.setPadding(0, 5, 0, 5); // set the padding (if required)
        labelInfo.setHeight(150);
        labelInfo.setBackgroundResource(R.drawable.table_headers_bkg);
        tr_head.addView(labelInfo); //add the column to the table row here



        TextView labelType= new TextView(this);
        labelType.setText(" Type "); // set the text for the header
        labelType.setTypeface(null, Typeface.BOLD);
        labelType.setGravity(Gravity.CENTER);
        labelType.setPadding(0, 5, 0, 5); // set the padding (if required)
        labelType.setHeight(150);
        labelType.setBackgroundResource(R.drawable.table_headers_bkg);
        tr_head.addView(labelType); //add the column to the table row here


        TextView labelQuantity = new TextView(this);
        labelQuantity.setText(" No. Of \n Items \n\n"); // set the text for the header
        labelQuantity.setTypeface(null, Typeface.BOLD);
        labelQuantity.setGravity(Gravity.CENTER);
        labelQuantity.setPadding(0, 0, 0, 10); // set the padding (if required)
        labelQuantity.setHeight(150);
        labelQuantity.setBackgroundResource(R.drawable.table_headers_bkg);
        tr_head.addView(labelQuantity); //add the column to the table row here

       *//* TextView labelPerPrice = new TextView(this);
        labelPerPrice.setText(" Type ");
        labelPerPrice.setTypeface(null, Typeface.BOLD);
        labelPerPrice.setGravity(Gravity.CENTER);
        labelPerPrice.setPadding(0, 5, 0, 5); // set the padding (if required)
        labelPerPrice.setHeight(150);
        labelPerPrice.setBackgroundResource(R.drawable.table_headers_bkg);
        tr_head.addView(labelPerPrice);// add the column to the table row here*//*

        tableOrderedProducts.addView(tr_head, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
