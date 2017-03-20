package com.replete.komalapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.StaggeredGridView.DataSet;
import com.replete.komalapp.StaggeredGridView.Item;
import com.replete.komalapp.StaggeredGridView.STGVAdapter;
import com.replete.komalapp.StaggeredGridView.StaggeredGridView;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class STGVActivity extends Activity {
    private String TAG = "STGVActivity";
    StaggeredGridView stgv;
    STGVAdapter mAdapter;
    private DataSet mData = new DataSet();
    private ArrayList<Item> mItems = new ArrayList<Item>();
    private SingletonUtil singletonUtil;
    private View footerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_stgv);

        stgv = (StaggeredGridView) findViewById(R.id.stgv);
        singletonUtil = SingletonUtil.getSingletonConfigInstance();
        int margin = getResources().getDimensionPixelSize(R.dimen.stgv_margin);

        stgv.setItemMargin(margin);
        stgv.setPadding(margin, 0, margin, 0);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.layout_loading_footer, null);
        stgv.setFooterView(footerView);

//        addCategoriesToList();
        callToCategories();
        //set category List first time and set in adapter

      /*  mAdapter = new STGVAdapter(this, getApplication(), addCategoriesToList());
        stgv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*/

        stgv.setOnLoadmoreListener(new StaggeredGridView.OnLoadmoreListener() {
            @Override
            public void onLoadmore() {
//                new LoadMoreTask().execute();
//                addCategoriesToList();
//                callToCategories();
            }
        });
    }

/*

    public class LoadMoreTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            addCategoriesToList();
            mAdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }
    }

*/

    private void callToCategories() {
//        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_CATEGORY + 1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            JSONObject paginationObj = response.getJSONObject("pagination and no.of.category");
                            int numOfOrders = paginationObj.getInt("numOfCategory");
                            int recordPerPage = paginationObj.getInt("recordPerPage");
                            int current_page = paginationObj.getInt("pageNumber");
//                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                addCategoriesToList(response);
                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                            if ((numOfOrders - (recordPerPage * (current_page))) <= 0) {
//                                stgv.removeView(footerView);
                                footerView.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    private void addCategoriesToList(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newJsonObj = jsonArray.getJSONObject(i);

                Item item = new Item(newJsonObj.getString("url"), newJsonObj.getString("categoryId"), newJsonObj.getString("categoryName"));

           /* item.width= mData.width[i];
            item.height= mData.height[i];*/
                mItems.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



       /* for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.url = mData.url[i];
           *//* item.width= mData.width[i];
            item.height= mData.height[i];*//*
            mItems.add(item);
        }*/

        mAdapter = new STGVAdapter(this, mItems);
        stgv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}