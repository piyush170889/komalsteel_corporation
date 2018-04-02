package com.replete.komalapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.activity.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ravi on 04/04/15.
 */
public class HttpService extends IntentService {

    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            String android_id = intent.getStringExtra("android_id");
            String contact = intent.getStringExtra("contact");

            verifyOtp(otp,android_id,contact);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp,String android_id,String contact) {
        JSONObject finalJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellnumber", contact);
            jsonObject.put("deviceInfo", android_id);
            jsonObject.put("otp", otp);
            finalJsonObject.put("request", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG+ " confirm otp", "finalJsonObject=" + finalJsonObject);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_CONFIRM_OTP,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + " confirm otp", response.toString());
//                        buttonVerifyContact.setEnabled(false);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
//                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Verified successfully", Toast.LENGTH_LONG).show();
                                        Intent intentToSignUp = new Intent(HttpService.this, RegisterActivity.class);

                                        startActivity(intentToSignUp);
//                                        finish();
                                    }
                                }, 2000);
                            } else
                                Toast.makeText(getApplicationContext(), responseObj.getString("message"), Toast.LENGTH_LONG).show();
//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Try Again!!", Toast.LENGTH_LONG).show();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                buttonVerifyContact.setEnabled(false);
//                progressBar.setVisibility(View.GONE);
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }
}
