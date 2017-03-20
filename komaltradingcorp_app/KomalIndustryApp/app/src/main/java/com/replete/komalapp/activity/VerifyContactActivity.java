package com.replete.komalapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.BaseInterface;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.service.HttpService;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.logging.StreamHandler;

/**
 * Created by MR JOSHI on 07-Apr-16.
 */
public class VerifyContactActivity extends AppCompatActivity implements BaseInterface, View.OnClickListener {

    private Button buttonVerifyContact;
    private ProgressBar progressBar;
    private EditText editTextContactNoToVerify;
    private String TAG = "VerifyContactActivity ";
    private SingletonUtil singletonUtil;
    String contact;
    String android_id;
    private EditText editTextOtp;
    private TextView textViewResendOtp;

    private Snackbar snackbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_contact);
        buttonVerifyContact = (Button) findViewById(R.id.btn_verify_contact);
        buttonVerifyContact.setOnClickListener(this);
        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        editTextContactNoToVerify = (EditText) findViewById(R.id.editText_contact_no_to_verify);
        textViewResendOtp = (TextView) findViewById(R.id.tvResendOtp);
        textViewResendOtp.setOnClickListener(this);
        singletonUtil = SingletonUtil.getSingletonConfigInstance();
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_verify_contact:
                showHideKeyboard();
                if (buttonVerifyContact.getText().toString().equals("submit")) {
                    String otp = editTextOtp.getText().toString();
                    if (!otp.isEmpty())
                        confirmOtp(editTextOtp.getText().toString());
                    else
                        singletonUtil.showSnackBar("Please enter the OTP", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                } else
                    sendOtp();
                break;
            case R.id.tvResendOtp:
                sendOtp();
                break;
        }
    }

    private void sendOtp() {
        if (!validate()) {
            singletonUtil.showSnackBar("Please provide valid details", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
        } else if (!SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this)) {
            singletonUtil.showSnackBar(getString(R.string.check_net_connection), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
        } else {
//            buttonVerifyContact.setEnabled(false);
            generateOTP(editTextContactNoToVerify.getText().toString());
        }
    }

    private boolean validate() {
        boolean valid = true;
        String contact = editTextContactNoToVerify.getText().toString();
        if (contact.isEmpty() || contact.length() != 10) {
            editTextContactNoToVerify.setError("provide valid contact No.");
            valid = false;
        } else {
            editTextContactNoToVerify.setError(null);
        }
        return valid;
    }

    private void generateOTP(String contactNo) {

        contact = editTextContactNoToVerify.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        JSONObject finalJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellnumber", contact);
            jsonObject.put("deviceInfo", android_id);
            finalJsonObject.put("request", jsonObject);
            Log.d(TAG, finalJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "finalJsonObject=" + finalJsonObject);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_SEND_OTP, finalJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
//                        buttonVerifyContact.setEnabled(true);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
//                                snackbar1 = singletonUtil.showSnackBar("OTP sent in SMS", (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                                Snackbar snackbar = Snackbar
                                        .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), "OTP sent in SMS", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                onOtpSentSuccess();
                                            }
                                        });
                                // Changing message text color
                                snackbar.setActionTextColor(Color.WHITE);
                                // Changing action button text color
                                View sbView = snackbar.getView();
                                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setTextColor(Color.WHITE);
                                snackbar.show();
                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                buttonVerifyContact.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void onOtpSentSuccess() {

        ((TextView) findViewById(R.id.verify_contact_msg)).setText("You have done!!Enter OTP sent in SMS");
        editTextContactNoToVerify.setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tvResendOtp)).setVisibility(View.GONE);
        buttonVerifyContact.setText("submit");
        editTextOtp = (EditText) findViewById(R.id.editText_otp);
        editTextOtp.setVisibility(View.VISIBLE);
        textViewResendOtp.setVisibility(View.VISIBLE);

//        confirmOtp(editTextOtp.getText().toString());

    }

    private void confirmOtp(String otp) {
/*
        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("otp", otp);
            grapprIntent.putExtra("contact", contact);
            grapprIntent.putExtra("android_id", android_id);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }*/

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

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
        Log.d(TAG, "finalJsonObject=" + finalJsonObject);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_CONFIRM_OTP, finalJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
//                        buttonVerifyContact.setEnabled(false);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
//                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                snackbar2 = singletonUtil.showSnackBar("Verified successfully", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intentToSignUp = new Intent(VerifyContactActivity.this, RegisterActivity.class);
                                        intentToSignUp.putExtra("contactNo", contact);
                                        startActivity(intentToSignUp);
                                        snackbar2.dismiss();
                                        finish();
                                    }
                                }, 1000);
                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);
//                buttonVerifyContact.setEnabled(false);
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void callToVerify() {
         /* progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_CONFIRM_OTP +otp,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        buttonVerifyContact.setEnabled(false);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intentToSignUp = new Intent(VerifyContactActivity.this, RegisterActivity.class);
                                        Bundle mBundle = new Bundle();
                                        mBundle.putString("contactNo", editTextContactNoToVerify.getText().toString());
                                        intentToSignUp.putExtras(mBundle);
                                        startActivity(intentToSignUp);
                                        finish();
                                    }
                                }, 2000);
                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                buttonVerifyContact.setEnabled(false);
                progressBar.setVisibility(View.GONE);
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);*/
    }

}


