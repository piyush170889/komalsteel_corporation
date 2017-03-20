package com.replete.komalapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.Orders;
import com.replete.komalapp.recyclerutils.TrackOrderAdapter;

import java.util.ArrayList;

/**
 * Created by MR JOSHI on 23-Mar-16.
 */
public class TrackOrderActivity extends AppCompatActivity {
    private ArrayList<Orders> ordersArrayList;
    private TrackOrderAdapter trackOrderAdapter;
    private ProgressBar progressBar;
    private String TAG = "OrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");

//        RecyclerView recyclerViewTrackOrder = (RecyclerView) findViewById(R.id.recycler_view_track_order);
//        recyclerViewTrackOrder.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
        ordersArrayList = new ArrayList<>();
//        JSONObject response = SingletonUtil.getSingletonConfigInstance().getOrderList();

        ArrayList<Orders> newList = new ArrayList<>();
//        ordersArrayList = parseJSONObj(response);
        Log.d(TAG, "Listsize" + ordersArrayList.size());
        progressBar.setVisibility(View.GONE);
        trackOrderAdapter = new TrackOrderAdapter(this, ordersArrayList);
//        recyclerViewTrackOrder.setAdapter(trackOrderAdapter);

        trackOrderAdapter.SetOnItemClickListener(new TrackOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Orders orders = ordersArrayList.get(position);
                Intent intent = new Intent(TrackOrderActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orders.getOrderId());
                startActivity(intent);
            }
        });
    }

   /* private ArrayList<Orders> parseJSONObj(JSONObject response) {
        Log.d(TAG, "parseJSON Response=" + response.toString());
        try {
            JSONArray jsonArray = response.getJSONArray("orders");
            for (int i = 0; i < jsonArray.length(); i++) {
                Orders orders = new Orders();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d(TAG, " Response of one product=" + jsonObject.toString());
                orders.setProductTitle(jsonObject.getString("productName"));
                orders.setOrderId(jsonObject.getString("orderId"));
                orders.setOrderPlacedDateNTime(jsonObject.getString("orderPlacedDateTime"));
                orders.setOrderStatus(jsonObject.getString("orderStatus"));
                orders.setOrderDeliveredOn(jsonObject.getString("orderDeliveredOn"));
                orders.setPackageQuantity(jsonObject.getString("packageQuantity"));
                orders.setPackagePrice(jsonObject.getString("packagePrice"));
                ordersArrayList.add(orders);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ordersArrayList;
    }*/
}
