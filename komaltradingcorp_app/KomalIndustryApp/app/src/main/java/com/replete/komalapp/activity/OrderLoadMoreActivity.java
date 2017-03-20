package com.replete.komalapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.recyclerutils.CustomAdapter;
import com.replete.komalapp.rowitem.OrderProducts;
import com.replete.komalapp.rowitem.Orders;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderLoadMoreActivity extends AppCompatActivity {

    private List<Orders> ordersArrayList = new ArrayList<>();
    int current_page = 1;
    private ListView lv;
    private CustomAdapter trackOrderAdapter;
    private String TAG = "OrderLoadMoreActivity";
    private AppCompatButton btnLoadMore;
    private int numOfOrders;
    private int recordPerPage;
    private int pagenumber;
    private ProgressBar progressBar;
    private boolean isLoadMore = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        //register broadcast receiver

        lv = (ListView) findViewById(R.id.list);
//        lv.setSmoothScrollbarEnabled(true);
//        lv.setScrollContainer(false);

/*

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCoun) {
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState != 0)
                    lv.getAdapter().isScrolling = true;
                else {
                    adapter.isScrolling = false;
                    adapter.notifyDataSetChanged();
                }
            }
        });
*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");

        //call webservice to get orders
        getOrders();

        // LoadMore button
        btnLoadMore = new AppCompatButton(OrderLoadMoreActivity.this);
        btnLoadMore.setBackgroundColor(Color.parseColor("#009788"));
        btnLoadMore.setTextColor(Color.parseColor("#ffffff"));
        btnLoadMore.setText("Load More");
        // Adding Load More button to lisview at bottom

        /**
         * Listening to Load More button click event
         * */
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //call webservice to get orders
                getOrders();
            }
        });


		/*
        *//**
         * Listening to listview single row selected
         * **/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Orders orders = ordersArrayList.get(position);
                Intent intent = new Intent(OrderLoadMoreActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orders.getOrderId());

//                intent.putExtra("packagePrice", orders.getPackagePrice());
                intent.putExtra("orderDeliveredOn", orders.getOrderDeliveredOn());
                intent.putExtra("orderStatus", orders.getOrderStatus());
                intent.putExtra("orderPlacedDateNTime", orders.getOrderPlacedDateNTime());
                intent.putExtra("orderAddress", orders.getOrderAddress());

                intent.putExtra("orderCity", orders.getOrderCity());
                intent.putExtra("orderState", orders.getOrderState());
                intent.putExtra("orderCountry", orders.getOrderCountry());
                intent.putExtra("orderPinCode", orders.getOrderPinCode());

                intent.putExtra("LrNo", orders.getOrderLrNo());
                intent.putExtra("LrDate", orders.getOrderLrDate());
                intent.putExtra("NumberOfCartonLoaded", orders.getOrderNoOfCartonLoaded());

                List<OrderProducts> orderProductsList = orders.getOrderProductsList();
                Gson gson = new Gson();
                String orderProductsListString = gson.toJson(orderProductsList);

                intent.putExtra("orderProductsList", orderProductsListString);
                startActivity(intent);
            }
        });
    }

    private void getOrders() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final HashMap<String, String> userDetails = databaseHandler.getUserDetails();
        if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(OrderLoadMoreActivity.this))
            callToGetOrdersStatus(current_page, userDetails.get("userTrackId"));
        else
            Toast.makeText(OrderLoadMoreActivity.this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    private void callToGetOrdersStatus(final int page_No, final String userTrackId) {
        final ProgressBar progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_ORDER_STATUS + userTrackId + "/" + page_No,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        Log.d(TAG, "url=" + ConfigUrls.URL_GET_ORDER_STATUS + userTrackId + "/" + page_No);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            if (responseObj.getString("status").equals("200")) {

                                JSONArray cartDetailsArrayObj = response.getJSONArray("request");
                                if (cartDetailsArrayObj.length() > 0) {
                                    for (int i = 0; i < cartDetailsArrayObj.length(); i++) {
                                        JSONObject cartDetailsObject = cartDetailsArrayObj.getJSONObject(i);
                                        Orders orders = new Orders();
                                        orders.setOrderId(cartDetailsObject.getString("cartDtlsId"));
                                        orders.setOrderStatus(cartDetailsObject.getString("cartStatus"));
//                                        orders.setPackagePrice(cartDetailsObject.getString("cartPrice"));
                                        orders.setOrderDeliveredOn(cartDetailsObject.getString("expDlvryDate"));
                                        orders.setOrderPlacedDateNTime(cartDetailsObject.getString("orderDate"));
                                        orders.setOrderAddress(cartDetailsObject.getString("stAddress1"));

                                        orders.setOrderCity(cartDetailsObject.getString("city"));
                                        orders.setOrderState(cartDetailsObject.getString("state"));
                                        orders.setOrderPinCode(cartDetailsObject.getString("postalCode"));
                                        orders.setOrderCountry(cartDetailsObject.getString("country"));

                                        orders.setOrderLrNo(cartDetailsObject.getString("lrNo"));
                                        orders.setOrderLrDate(cartDetailsObject.getString("lrNoDate"));
                                        orders.setOrderNoOfCartonLoaded(cartDetailsObject.getString("noOfCartonLoaded"));

                                        List<OrderProducts> orderProductsList = new ArrayList<>();
                                        JSONArray itemOrderDetailsListArrayObj = cartDetailsObject.getJSONArray("itemOrderDetailsList");
                                        for (int j = 0; j < itemOrderDetailsListArrayObj.length(); j++) {
                                            JSONObject itemOrderDetailsListObject = itemOrderDetailsListArrayObj.getJSONObject(j);
                                            OrderProducts orderProducts = new OrderProducts(itemOrderDetailsListObject.getString("itemName"),
                                                    itemOrderDetailsListObject.getString("itemQty"),
                                                    itemOrderDetailsListObject.getString("itemPrice"), itemOrderDetailsListObject.getString("uom"));


                                            orderProductsList.add(orderProducts);
                                        }

                                        orders.setOrderProductsList(orderProductsList);
                                        ordersArrayList.add(orders);
                                    }
                                    JSONObject paginationDetailsObject = response.getJSONObject("paginationDetails");
                                    numOfOrders = paginationDetailsObject.getInt("numOfOrders");
                                    recordPerPage = paginationDetailsObject.getInt("recordPerPage");
                                    pagenumber = paginationDetailsObject.getInt("pageNumber");
                                    current_page++;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            trackOrderAdapter = new CustomAdapter(OrderLoadMoreActivity.this, ordersArrayList);
                                            lv.setAdapter(trackOrderAdapter);

                                            updateUI();
                                        }
                                    }, 1000);
                                } else {
                                    TextView textViewMesg = ((TextView) findViewById(R.id.textView_show_message));
                                    textViewMesg.setVisibility(View.VISIBLE);
                                    ImageView imageView_no_order = ((ImageView) findViewById(R.id.imageView_no_order));
                                    imageView_no_order.setVisibility(View.VISIBLE);
                                    textViewMesg.setText("No Order Placed Yet");
//                                    Toast.makeText(OrderLoadMoreActivity.this, "No Order yet", Toast.LENGTH_SHORT).show();
                                }

                            } else
                                Toast.makeText(OrderLoadMoreActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);
                onError();
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    private void onError() {
        Snackbar snackbar = Snackbar
                .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), "Unable to connect server!! Press OK to try again..", Snackbar.LENGTH_INDEFINITE)

                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getOrders();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    private void updateUI() {
        Log.d(TAG, "numOfOrders =" + numOfOrders + " recordPerPage=" + recordPerPage + "pagenumber " + current_page);
        Log.d(TAG, "remaining order=" + (numOfOrders - (recordPerPage * (current_page - 1))));
        if ((numOfOrders - (recordPerPage * (current_page - 1))) > 0) {
            btnLoadMore.setVisibility(View.VISIBLE);
            lv.addFooterView(btnLoadMore);
            lv.setSelection(lv.getAdapter().getCount() - 1);
        } else {
            btnLoadMore.setVisibility(View.GONE);
        }
    }

/*

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
*/
/*
//        if (isLoadMore) {
        if ((numOfOrders - (recordPerPage * (current_page - 1))) <= 0) {
//                                stgv.removeView(footerView);
//                                gridView.removeView(footerView);
            if (btnLoadMore.getVisibility() == View.VISIBLE)
                btnLoadMore.setVisibility(View.GONE);
            isLoadMore = false;
        } else {

            isLoadMore = true;
            btnLoadMore.setVisibility(View.VISIBLE);
            lv.addFooterView(btnLoadMore);

        }
        lv.setSelection(lv.getAdapter().getCount() - 1);

//        }*//*

    }
*/


}