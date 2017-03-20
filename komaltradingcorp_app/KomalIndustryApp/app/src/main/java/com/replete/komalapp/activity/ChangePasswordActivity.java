package com.replete.komalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by MR JOSHI on 09-Apr-16.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChangePasswordActivity";
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextOldPassword;
    private TextView textViewShowHideOldPassword, textViewShowHideNewPassword, textViewShowHideConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");


        //init components
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        textViewShowHideOldPassword = (TextView) findViewById(R.id.textView_show_hide_password_old);
        textViewShowHideNewPassword = (TextView) findViewById(R.id.textView_show_hide_password_new);
        textViewShowHideConfirmPassword = (TextView) findViewById(R.id.textView_show_hide_password_confirm);
        Button buttonChangePassword = (Button) findViewById(R.id.button_change_password);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);

        //set ClickListeners
        textViewShowHideOldPassword.setOnClickListener(this);
        textViewShowHideNewPassword.setOnClickListener(this);
        textViewShowHideConfirmPassword.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_change_password:
                if (validate()) {

                    //hide keypad , if open
                    showHideKeyboard();

                    if (editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {

                        //if new and confirm password are same
                        if (!editTextOldPassword.getText().toString().equals(editTextPassword.getText().toString())) {

                            //if old password and new pasword are different
                            if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this))

                                //if net is ON
                                //server call to change password
                                callToChangePassword();
                            else
                                Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(this, "New password should be different", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "new password & confirm password should same", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_cancel:
                //back to profile activity
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.textView_show_hide_password_old:
                //handle show or hide password link
                setHideShowPassword(editTextOldPassword, textViewShowHideOldPassword);
                break;
            case R.id.textView_show_hide_password_new:
                //handle show or hide password link
                setHideShowPassword(editTextPassword, textViewShowHideNewPassword);
                break;
            case R.id.textView_show_hide_password_confirm:
                //handle show or hide password link
                setHideShowPassword(editTextConfirmPassword, textViewShowHideConfirmPassword);
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

    /**
     * server call to Change Password
     */
    private void callToChangePassword() {

//        progressBar.setVisibility(View.VISIBLE);
        final DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final HashMap<String, String> userDetails = databaseHandler.getUserDetails();


        JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            requestJson.put("oldPassWord", editTextOldPassword.getText().toString());
            requestJson.put("newPassword", editTextPassword.getText().toString());
            finalObject.put("request", requestJson);
            Log.d(TAG, "finalObject" + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_CHANGE_PASSWORD + userDetails.get("userTrackId"), finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
//                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {

                                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        //logout the user
                                        SessionManager session = new SessionManager(getApplicationContext());

                                        //clear session
                                        session.setLogin(false);
                                        session.clear();

                                        //clear all DB tables
                                        databaseHandler.clearCartTable();
                                        databaseHandler.clearCartSubcategoryTable();
                                        databaseHandler.clearShippingAddressTable();
                                        databaseHandler.clearAssociateDistributorTable();

                                        //redirect to Login screen
                                        Intent loginIntent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                        //clear all back activities
                                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                }, 1000);

                            } else
                                Toast.makeText(ChangePasswordActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChangePasswordActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();

            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    /**
     * check validation
     *
     * @return valid : true ->if valid details
     * valid : false -> if not valid details
     */
    private boolean validate() {

        boolean valid = true;

        String oldPassword = editTextOldPassword.getText().toString();
        String newPassword = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();


        if (oldPassword.isEmpty() || oldPassword.length() < 4 || oldPassword.length() > 30) {
            editTextOldPassword.setError(Html.fromHtml("<font color='red'>Enter between 4 and 30 alphanumeric characters</font>"));
            requestFocus(editTextOldPassword);
            valid = false;
        } else editTextOldPassword.setError(null);

        if (newPassword.isEmpty() || newPassword.length() < 4 || newPassword.length() > 30) {
            editTextPassword.setError(Html.fromHtml("<font color='red'>Enter between 4 and 30 alphanumeric characters</font>"));
            requestFocus(editTextPassword);
            valid = false;
        } else editTextPassword.setError(null);

        if (confirmPassword.isEmpty() || confirmPassword.length() < 4 || confirmPassword.length() > 30) {
            editTextConfirmPassword.setError(Html.fromHtml("<font color='red'>Enter between 4 and 30 alphanumeric characters</font>"));
            requestFocus(editTextConfirmPassword);
            valid = false;
        } else editTextConfirmPassword.setError(null);


       /* if (oldPassword.isEmpty() || oldPassword.length() < 4 || oldPassword.length() > 30) {
            ((TextInputLayout) findViewById(R.id.input_layout_old_password)).setError(Html.fromHtml("<font color='red'>Enter 4 to 30 alphanumeric characters</font>"));
            requestFocus(editTextOldPassword);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_old_password)).setErrorEnabled(false);
            requestFocus(editTextOldPassword);
            editTextOldPassword.setHintTextColor(this.getResources().getColor(R.color.primary));
        }


        if (newPassword.isEmpty() || newPassword.length() < 4 || newPassword.length() > 30) {
            ((TextInputLayout) findViewById(R.id.input_layout_password)).setError(Html.fromHtml("<font color='red'>Enter 4 to 30 alphanumeric characters</font>"));
            requestFocus(editTextPassword);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_password)).setErrorEnabled(false);
            requestFocus(editTextPassword);
            editTextPassword.setHintTextColor(this.getResources().getColor(R.color.primary));
        }

        if (confirmPassword.isEmpty() || confirmPassword.length() < 4 || confirmPassword.length() > 30) {
            ((TextInputLayout) findViewById(R.id.input_layout_confirm_password)).setError(Html.fromHtml("<font color='red'>Enter 4 to 30 alphanumeric characters</font>"));
            requestFocus(editTextConfirmPassword);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_confirm_password)).setErrorEnabled(false);
            requestFocus(editTextConfirmPassword);
            editTextConfirmPassword.setHintTextColor(this.getResources().getColor(R.color.primary));
        }*/
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


}