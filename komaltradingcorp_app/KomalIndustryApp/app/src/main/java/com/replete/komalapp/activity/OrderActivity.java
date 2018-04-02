package com.replete.komalapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.OnLoadMoreListener;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.Orders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private List<Orders> ordersArrayList = new ArrayList<>();
    private UserAdapter mUserAdapter;
    private String TAG = "OrderActivity";
    private int Page_No = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Order");

       /* for (int i = 0; i < 30; i++) {
            Orders orders= new Orders();
            orders.set("alibaba" + i + "@gmail.com");
            orders.setName("Name " + i);
            ordersArrayList.add(orders);
        }*/
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final HashMap<String, String> userDetails = databaseHandler.getUserDetails();


//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_track_order);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        callToGetOrdersStatus(Page_No, userDetails.get("userTrackId"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mUserAdapter = new UserAdapter();
                mRecyclerView.setAdapter(mUserAdapter);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUserAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore() {
                                Log.e("haint", "Load More");
                                ordersArrayList.add(null);
                                mUserAdapter.notifyItemInserted(ordersArrayList.size() - 1);

                                //Load more data for reyclerview

                                Log.e("haint", "Load More 2");

                                //Remove loading item
                                ordersArrayList.remove(ordersArrayList.size() - 1);
                                mUserAdapter.notifyItemRemoved(ordersArrayList.size());

                                //Load data
                                int index = ordersArrayList.size();
                                int end = index + 20;
                                for (int i = index; i < end; i++) {
                                    callToGetOrdersStatus(Page_No, userDetails.get("userTrackId"));

                                    /*Orders orders = new Orders();
                                    orders.setName("Name " + i);
                                    orders.setEmail("alibaba" + i + "@gmail.com");
                                    ordersArrayList.add(orders);*/
                                }
                                mUserAdapter.notifyDataSetChanged();
                                mUserAdapter.setLoaded();

                            }
                        });
                    }
                }, 10000);
            }
        }, 5000);


    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.textView_order_id);
            tvEmailId = (TextView) itemView.findViewById(R.id.textView_order_status);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        private OnLoadMoreListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 4;
        private int lastVisibleItem, totalItemCount;

        public UserAdapter() {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        @Override
        public int getItemViewType(int position) {
            return ordersArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(OrderActivity.this).inflate(R.layout.row_item_order, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(OrderActivity.this).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UserViewHolder) {
                Orders orders = ordersArrayList.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvName.setText(orders.getOrderId());
                userViewHolder.tvEmailId.setText(orders.getOrderStatus());
            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }

        @Override
        public int getItemCount() {
            return ordersArrayList == null ? 0 : ordersArrayList.size();
        }

        public void setLoaded() {
            isLoading = false;
        }
    }

    private void callToGetOrdersStatus(final int page_No, final String userTrackId) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_ORDER_STATUS + userTrackId + "/" + page_No,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + "  get order status", response.toString());
                        Log.d(TAG+ "  get order status", "url=" + ConfigUrls.URL_GET_ORDER_STATUS + userTrackId + "/" + page_No);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            if (responseObj.getString("status").equals("200")) {
                                JSONArray cartDetailsArrayObj = response.getJSONArray("cartDetails");
                                for (int i = 0; i < cartDetailsArrayObj.length(); i++) {
                                    JSONObject cartDetailsObject = cartDetailsArrayObj.getJSONObject(i);
                                    Orders orders = new Orders();
                                    orders.setOrderId(cartDetailsObject.getString("cartDtlsId"));
                                    orders.setOrderStatus(cartDetailsObject.getString("cartStatus"));
                                    ordersArrayList.add(orders);
                                }

                                JSONObject paginationDetailsObject = response.getJSONObject("paginationDetails");
                                paginationDetailsObject.getString("numOfOrders");
                                paginationDetailsObject.getString("recordPerPage");
                                paginationDetailsObject.getString("pageNumber");

                                Page_No++;


                            } else
                                Toast.makeText(OrderActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }
}
