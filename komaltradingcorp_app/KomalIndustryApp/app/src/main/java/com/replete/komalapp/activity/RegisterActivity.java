package com.replete.komalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.utils.SingletonUtil;
import com.replete.komalapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MR JOSHI on 14-Mar-16.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private SingletonUtil singletonUtil;
    private EditText editTextPassword, editTextContactNo, editTextFirstName;
    private ProgressBar progressBar;
    private String TAG = "RegisterActivity";
    //    private RadioGroup radioGroupUserType;
    //    private AutoCompleteTextView autocompleteEmail;
    private TextView textViewShowHidePassword;
    private EditText editTextLastName;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        //create instance of Singleton class
        singletonUtil = SingletonUtil.getSingletonConfigInstance();
        //init components
//        autocompleteEmail = (AutoCompleteTextView) findViewById(R.id.editTextEmail);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
//        editTextPANNo = (EditText) findViewById(R.id.editTextPANNo);
//        editTextVatTinNo = (EditText) findViewById(R.id.editTextVatTinNo);
//        editTextDisplayName = (EditText) findViewById(R.id.editTextDisplayName);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContactNo = (EditText) findViewById(R.id.editTextContactNo);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        Button buttonRegister = (Button) findViewById(R.id.btn_register);
        TextView textViewLinkToSignIn = (TextView) findViewById(R.id.textViewLinkToSignIn);
//        radioGroupUserType = (RadioGroup) findViewById(R.id.radioGroupUserType);
        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));

        textViewShowHidePassword = (TextView) findViewById(R.id.textView_show_hide_password);
        textViewShowHidePassword.setOnClickListener(this);
//set clicklistener
        textViewLinkToSignIn.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
//get contactNo from previous screen
        Bundle b = getIntent().getExtras();
        editTextContactNo.setText(b.getString("contactNo"));

//set animation
        Animation slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        editTextFirstName.startAnimation(slideRight);
        editTextContactNo.startAnimation(slideRight);
        editTextPassword.startAnimation(slideRight);
        editTextLastName.startAnimation(slideRight);
        editTextEmail.startAnimation(slideRight);
//        editTextPANNo.startAnimation(slideRight);
//        editTextVatTinNo.startAnimation(slideRight);
//        editTextDisplayName.startAnimation(slideRight);
        //        autocompleteEmail.startAnimation(slideRight);
        ((TextView) findViewById(R.id.textView11)).startAnimation(fadeIn);
        textViewLinkToSignIn.startAnimation(fadeIn);
        textViewShowHidePassword.startAnimation(slideRight);
        buttonRegister.startAnimation(fadeIn);
//        radioGroupUserType.startAnimation(fadeIn);
//        ((TextView) findViewById(R.id.textView_usertype_message)).startAnimation(fadeIn);

/*
//set autocompletion to email, with currently logged in account
        final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Account[] accounts = AccountManager.get(this).getAccounts();
        Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (EMAIL_PATTERN.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        autocompleteEmail.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(emailSet)));
    */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (validate()) {
                    showHideKeyboard();
                    if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this))
                        callToRegister();
                    else
                        singletonUtil.showSnackBar(getString(R.string.check_net_connection), (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                } else {
                    showHideKeyboard();
                    singletonUtil.showSnackBar("Please provide valid details", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                }
                break;
            case R.id.textViewLinkToSignIn:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.textView_show_hide_password:
                if (textViewShowHidePassword.getText().toString().equals(getString(R.string.show_password))) {
                    // hide password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    textViewShowHidePassword.setText(getString(R.string.hide_password));
                    editTextPassword.setSelection(editTextPassword.getText().length());
                } else if (textViewShowHidePassword.getText().toString().equals(getString(R.string.hide_password))) {
                    // show password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    textViewShowHidePassword.setText(getString(R.string.show_password));
                    editTextPassword.setSelection(editTextPassword.getText().length());
                }
                break;
        }
    }

    private void callToRegister() {
        progressBar.setVisibility(View.VISIBLE);
//        RadioButton radiobtn = (RadioButton) findViewById(radioGroupUserType.getCheckedRadioButtonId());
        JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();

        try {
            requestJson.put("firstName", editTextFirstName.getText().toString());
            requestJson.put("lastName", editTextLastName.getText().toString() == null ? "" : editTextLastName.getText().toString());
            requestJson.put("cntc_num", editTextContactNo.getText().toString());
            requestJson.put("cmpnyInfoId", "56");
//            requestJson.put("", editTextEmail.getText().toString());

            requestJson.put("loginId", editTextEmail.getText().toString());
//            requestJson.put("userType", radiobtn.getText().toString());
            requestJson.put("password", editTextPassword.getText().toString());
//            requestJson.put("displayName", editTextDisplayName.getText().toString());
//            requestJson.put("vatTinNo", editTextVatTinNo.getText().toString());
//            requestJson.put("panNo", editTextPANNo.getText().toString());
            finalObject.put("userDetails", requestJson);
            Log.d(TAG + " register", "finalObject" + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_REGISTER, finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG+ " register", response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                onSuccessFullRegister();
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
                progressBar.setVisibility(View.GONE);

                error.printStackTrace();
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));

            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void onSuccessFullRegister() {

        Snackbar snackbar = Snackbar
                .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), "Registered successfully", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();

       /*
        singletonUtil.showSnackBar("Registered successfully", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private boolean validate() {
        boolean valid = true;
        String contactNo = editTextContactNo.getText().toString();
//        String displayName = editTextDisplayName.getText().toString();
//        String vatTinNo = editTextVatTinNo.getText().toString();
//        String panNo = editTextPANNo.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextFirstName.getText().toString();
//        String email = autocompleteEmail.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            editTextFirstName.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            valid = false;
        } else
            editTextFirstName.setError(null);
/*

        if (displayName.isEmpty() || displayName.length() < 3) {
            editTextDisplayName.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            valid = false;
        } else
            editTextDisplayName.setError(null);
*/


        /*if (vatTinNo.isEmpty()) {
            editTextVatTinNo.setError(Html.fromHtml("<font color='red'>Enter VAT TIN No.</font>"));
            valid = false;
        } else
            editTextVatTinNo.setError(null);


        if (panNo.isEmpty()) {
            editTextPANNo.setError(Html.fromHtml("<font color='red'>Enter PAN No.</font>"));
            valid = false;
        } else
            editTextPANNo.setError(null);


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            autocompleteEmail.setError(Html.fromHtml("<font color='red'>Enter valid email address</font>"));
            valid = false;
        } else
            autocompleteEmail.setError(null);*/

        if (contactNo.isEmpty() || contactNo.length() != 10 || !Patterns.PHONE.matcher(contactNo).matches()) {
            editTextContactNo.setError(Html.fromHtml("<font color='red'>Enter valid contact no</font>"));
            valid = false;
        } else
            editTextContactNo.setError(null);


        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            editTextPassword.setError(Html.fromHtml("<font color='red'>Enter between 4 and 30 alphanumeric characters</font>"));
            valid = false;
        } else editTextPassword.setError(null);
        return valid;
    }

    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

}
