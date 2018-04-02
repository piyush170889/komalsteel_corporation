package com.replete.komalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.utils.SessionManager;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by MR JOSHI on 25-Mar-16.
 */
public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etOTP, etNewPassword, etReTypeNewPassword;
    private String TAG = "ForgetPasswordActivity";
    private HashMap<String, String> userDetails;
    private String contactNo;
    private ProgressBar progressBar;
    private TextView textViewShowHidePasswordConfirm;
    private TextView textViewShowHidePasswordNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        etOTP = (EditText) findViewById(R.id.editText_otp);
        etNewPassword = (EditText) findViewById(R.id.editTextPassword);
        etReTypeNewPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textViewShowHidePasswordNew = (TextView) findViewById(R.id.textView_show_hide_password_new);
        textViewShowHidePasswordConfirm = (TextView) findViewById(R.id.textView_show_hide_password_confirm);
        textViewShowHidePasswordNew.setOnClickListener(this);
        textViewShowHidePasswordConfirm.setOnClickListener(this);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        contactNo = getIntent().getExtras().getString("contactNo");

        Log.d(TAG, "contactNo" + contactNo);


        Button buttonChangePassword = (Button) findViewById(R.id.button_change_password);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    if (etNewPassword.getText().toString().equals(etReTypeNewPassword.getText().toString())) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                        //if old password and new pasword are different
                        if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(ForgetPasswordActivity.this))
                            //if net is ON
                            //server call to change password
                            changePassword(contactNo, etOTP.getText().toString(), etNewPassword.getText().toString());
                        else
                            Toast.makeText(ForgetPasswordActivity.this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();
//                        onChangePasswordSuccessfully();

                    } else {
                        etReTypeNewPassword.setError("Both Passwords should be matched");
                        etNewPassword.setError("Both Passwords should be matched");
                    }
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // _textDialog.setText(editText.getText().toString());
//                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void onChangePasswordSuccessfully() {
        Toast.makeText(ForgetPasswordActivity.this, "Password reseted succeessfully", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }, 1000);
    }

    public boolean isValid() {
        boolean valid = true;

        if (etOTP.getText().toString().isEmpty() || etOTP.getText().toString().length() < 3 || etOTP.getText().toString().length() > 10) {
            etOTP.setError("enter 3 to 10 digit number");
            valid = false;
        } else {
            etOTP.setError(null);
        }

        if (etNewPassword.getText().toString().isEmpty() || etNewPassword.getText().toString().length() < 4 || etNewPassword.getText().toString().length() > 30) {
            etNewPassword.setError("between 4 and 30 alphanumeric characters");
            valid = false;
        } else {
            etNewPassword.setError(null);
        }

        if (etReTypeNewPassword.getText().toString().isEmpty() || etReTypeNewPassword.getText().toString().length() < 4 || etReTypeNewPassword.getText().toString().length() > 30) {
            etReTypeNewPassword.setError("between 4 and 30 alphanumeric characters");
            valid = false;
        } else {
            etReTypeNewPassword.setError(null);
        }
        return valid;
    }


    /**
     * server call to Change Password
     *
     * @param contactNo
     * @param otp
     * @param newPassword
     */
    private void changePassword(String contactNo, String otp, String newPassword) {

        progressBar.setVisibility(View.VISIBLE);

        JSONObject requestJson = new JSONObject();
//        JSONObject finalObject = new JSONObject();
        try {
            requestJson.put("cellNumber", contactNo);
            requestJson.put("otp", otp);
            requestJson.put("newPassword", newPassword);
            Log.d(TAG, "finalObject reset password" + requestJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_RESET_PASSWORD, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response reset password  " + response.toString());
                        Log.d(TAG, "URL  reset password  " + ConfigUrls.URL_RESET_PASSWORD);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {

                                onChangePasswordSuccessfully();

                            } else
                                Toast.makeText(ForgetPasswordActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ForgetPasswordActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgetPasswordActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();

            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_show_hide_password_new:
                setHideShowPassword(etNewPassword, textViewShowHidePasswordNew);
                break;

            case R.id.textView_show_hide_password_confirm:
                setHideShowPassword(etReTypeNewPassword, textViewShowHidePasswordConfirm);
                break;
        }
    }

    /**
     * handle show or hide password link
     *
     * @param editText: which contains password
     * @param textView  : show/hide textview
     */
    private void setHideShowPassword(EditText editText, TextView textView) {
        if (textView.getText().toString().equals(getString(R.string.show_password))) {
            // hide password
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            textView.setText(getString(R.string.hide_password));
            editText.setSelection(editText.getText().length());
        } else if (textView.getText().toString().equals(getString(R.string.hide_password))) {
            // show password
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            textView.setText(getString(R.string.show_password));
            editText.setSelection(editText.getText().length());
        }
    }
}
