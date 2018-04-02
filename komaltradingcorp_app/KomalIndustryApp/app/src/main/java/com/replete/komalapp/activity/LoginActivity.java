package com.replete.komalapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.AllDistributors;
import com.replete.komalapp.utils.SessionManager;
import com.replete.komalapp.utils.SingletonUtil;
import com.replete.komalapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 14-Mar-16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //    private EditText editTextEmail;


    private int TIME_OUT_IN_MS = 10000;
    private int RETRY_COUNT = 0;
    private float BACKOFF_MULTIPLIER = 1F;

    private EditText editTextPassword;
    private Button buttonSignIn;
    private SingletonUtil singletonUtil;
    private ProgressBar progressBar;
    private SessionManager session;
    private boolean isDealer, isDistributor;
    //    private RadioGroup radioGroupUserType;
    private AllDistributors allDistributors;
    private List<AllDistributors> allDistributorsList = new ArrayList<>();
    private String TAG = "LoginActivity";
    private String login_success_msg = "Logged In Successfully";

    private DatabaseHandler databaseHandler;
    private TextView textViewShowHidePassword;
    private EditText editTextContactNo;
    private Snackbar snackbar;
    private Button btnSkipLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
       /* if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/
        setContentView(R.layout.activity_login_new);

        singletonUtil = SingletonUtil.getSingletonConfigInstance();

        databaseHandler = new DatabaseHandler(this);


//        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContactNo = (EditText) findViewById(R.id.editTextContactNo);

        buttonSignIn = (Button) findViewById(R.id.btn_login);
        btnSkipLogin = (Button) findViewById(R.id.btn_skip_login);
        btnSkipLogin.setOnClickListener(this);

        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        TextView textViewLinkToSignUp = (TextView) findViewById(R.id.textViewLinkToSignUp);
        TextView textviewForgotPassword = ((TextView) findViewById(R.id.tvForgotPassword));
//        radioGroupUserType = (RadioGroup) findViewById(R.id.radioGroupUserType);
//        RadioButton radioButtonDealer = (RadioButton) findViewById(R.id.radioButtonDealer);
//        RadioButton radioButtonDistributor = (RadioButton) findViewById(R.id.radioButtonDistributor);
        textViewShowHidePassword = (TextView) findViewById(R.id.textView_show_hide_password);
        textViewShowHidePassword.setOnClickListener(this);

        buttonSignIn.setOnClickListener(this);
        textViewLinkToSignUp.setOnClickListener(this);
        textviewForgotPassword.setOnClickListener(this);

        Animation slideRight = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        editTextContactNo.startAnimation(slideRight);
        editTextPassword.startAnimation(slideRight);
        buttonSignIn.startAnimation(fadeIn);
        btnSkipLogin.startAnimation(fadeIn);
        textViewLinkToSignUp.startAnimation(fadeIn);
        textViewShowHidePassword.startAnimation(slideRight);
        textviewForgotPassword.startAnimation(fadeIn);
//        radioGroupUserType.startAnimation(fadeIn);
//        ((TextView) findViewById(R.id.textView_usertype_message)).startAnimation(fadeIn);
        ((TextView) findViewById(R.id.textView11)).startAnimation(fadeIn);


       /* final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
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
            case R.id.btn_login:
                if (validate()) {
                    showHideKeyboard();
                    //Start background process
                    if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this))
                        callLoginWebService();
                    else
                        singletonUtil.showSnackBar(getString(R.string.check_net_connection), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                } else {
                    showHideKeyboard();
                    singletonUtil.showSnackBar("Please provide valid details", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                }
                break;

            case R.id.textViewLinkToSignUp:
                Intent intent = new Intent(LoginActivity.this, VerifyContactActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btn_skip_login:
//                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intentMain);
                finish();
                break;

            case R.id.tvForgotPassword:
                showForgetPasswordDialog();
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

    /*Integrate request JSON:*/
    private void callLoginWebService() {
        progressBar.setVisibility(View.VISIBLE);
//        final RadioButton radiobtn = (RadioButton) findViewById(radioGroupUserType.getCheckedRadioButtonId());

        JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            requestJson.put("loginId", editTextContactNo.getText().toString());
            requestJson.put("password", editTextPassword.getText().toString());
//            requestJson.put("userType", radiobtn.getText().toString());
            requestJson.put("cmpnyInfoId", "56");
            finalObject.put("request", requestJson);
            Log.d(TAG + "login ", "finalObject" + finalObject.toString());
            Log.d(TAG, ConfigUrls.URL_LOGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_LOGIN, finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, ConfigUrls.URL_LOGIN);
                        Log.d(TAG+ "login", response.toString());

                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            if (responseObj.getString("status").equals("200")) {
                                JSONObject userDetailsObj = response.getJSONObject("userDetails");

                                databaseHandler.addUserDetails(userDetailsObj.getString("userTrackId"), userDetailsObj.getString("firstName"),
                                        userDetailsObj.getString("lastName"), userDetailsObj.getString("loginId"),
                                        userDetailsObj.getString("cntc_num"), userDetailsObj.getString("panNo"),
                                        userDetailsObj.getString("tinNo"), userDetailsObj.getString("userType"),
                                        userDetailsObj.getString("cmpnyInfoId"), userDetailsObj.getString("status"),
                                        userDetailsObj.getString("displayName"));

                              /*  // if user type is dealer than only add distributors
                                if (radiobtn.getText().toString().equals(getString(R.string.USER_TYPE_DEALER))) {
                                    Log.d(TAG, radiobtn.getText().toString());

                                    if (userDetailsObj.getJSONArray("associatedDistributorsList").length() == 0) {
                                        //when user logged in first time
                                        //user is dealer and not selected associated AssociatedDistributor yet
                                        Log.d(TAG, userDetailsObj.getJSONArray("associatedDistributorsList").length() + "");
                                        //redirect to DistributorActivity
                                        singletonUtil.showSnackBar(login_success_msg, (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                                        session.putPref("UserName", editTextContactNo.getText().toString(), LoginActivity.this);
                                        session.putPref("Password", editTextPassword.getText().toString(), LoginActivity.this);
                                        session.setLogin(true);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(LoginActivity.this, DistributorActivity.class);
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                                            }
                                        }, 1000);


                                    } else if (userDetailsObj.getJSONArray("associatedDistributorsList").length() > 0) {
                                        //when user logged in second time
                                        List<AssociatedDistributor> associatedDistributorList = new ArrayList<>();
                                        JSONArray assDistributorsListArrayObj = userDetailsObj.getJSONArray("associatedDistributorsList");
                                        AssociatedDistributor associatedDistributor;
                                        for (int i = 0; i < assDistributorsListArrayObj.length(); i++) {
                                            JSONObject elem = assDistributorsListArrayObj.getJSONObject(i);
//                                            Log.d(TAG, "distributorsListArrayObj.getJSONObject(i)" + distributorsListArrayObj.getJSONObject(i));
                                            if (elem != null) {
                                                associatedDistributor = new AssociatedDistributor(elem.getString("userDistributionListId"), elem.getString("firstName"), elem.getString("lastName"), elem.getString("displayName"), false);
//                                                associatedDistributorList.add(associatedDistributor);
                                                //add associated distributor list fetched in response in dB
                                                databaseHandler.addAssociatedDistributor(associatedDistributor);
                                            }
                                        }

//                                        SharedPreferences associatedDistributorPref = getSharedPreferences("associatedDistributorPref", MODE_PRIVATE);
//                                        SharedPreferences.Editor associatedDistributorPrefEditor = associatedDistributorPref.edit();
//                                        Gson gson = new Gson();
//                                        //store selected distributors in shared preferance
//                                        String gsonAssociatedDistributorsList = gson.toJson(associatedDistributorList);
//                                        Log.d("AllDistributorAdapter", gsonAssociatedDistributorsList);
//                                        associatedDistributorPrefEditor.putString("GsonAssociatedDistributorsList", gsonAssociatedDistributorsList);
//                                        associatedDistributorPrefEditor.commit();
                                        //redirect to DistributorActivity
                                        singletonUtil.showSnackBar(login_success_msg, (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                                        onSuccessfullLogin();
                                    }
                                } */
                                singletonUtil.showSnackBar(login_success_msg, (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                                onSuccessfullLogin();
                            } else {
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            }
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                singletonUtil.showSnackBar("Unable to connect server!!Try Again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                error.printStackTrace();
            }
        })

        {
        };

        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    /**
     * TODOs after successfull Login
     */
    private void onSuccessfullLogin() {

        session.putPref("UserName", editTextContactNo.getText().toString(), this);
        session.putPref("Password", editTextPassword.getText().toString(), this);
        session.setLogin(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
//                overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
            }
        }, 1000);
    }

    /**
     * To check either all fields are validate or not
     *
     * @return true: if all validate
     * false: if minimum one field is invalid
     */
    private boolean validate() {
        boolean valid = true;
        String contactNo = editTextContactNo.getText().toString();
        String password = editTextPassword.getText().toString();
        if (contactNo.isEmpty() || contactNo.length() != 10 || !Patterns.PHONE.matcher(contactNo).matches()) {
            editTextContactNo.setError(Html.fromHtml("<font color='red'>Enter valid contact no</font>"));
            valid = false;
        } else
            editTextContactNo.setError(null);


        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            editTextPassword.setError(Html.fromHtml("<font color='red'>Enter between 4 and 30 alphanumeric characters</font>"));
            requestFocus(editTextPassword);
            valid = false;
        } else editTextPassword.setError(null);


        /*if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((TextInputLayout) findViewById(R.id.input_layout_email)).setError(Html.fromHtml("<font color='red'>Enter at least 6 numbers</font>"));
            ((TextInputLayout) findViewById(R.id.input_layout_email)).setBackgroundColor(this.getResources().getColor(R.color.transcluent));
            requestFocus(editTextEmail);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_email)).setErrorEnabled(false);
            requestFocus(editTextEmail);
            editTextEmail.setHintTextColor(this.getResources().getColor(R.color.primary));
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            ((TextInputLayout) findViewById(R.id.input_layout_password)).setError(Html.fromHtml("<font color='red'>Enter between 4 and 10 alphanumeric characters</font>"));
            ((TextInputLayout) findViewById(R.id.input_layout_email)).setBackgroundColor(this.getResources().getColor(R.color.transcluent));

            requestFocus(editTextPassword);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_password)).setErrorEnabled(false);
            requestFocus(editTextPassword);
            editTextPassword.setHintTextColor(this.getResources().getColor(R.color.primary));
        }*/
        return valid;
    }

    /**
     * hide and show keyboard
     */
    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();

        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    protected void showForgetPasswordDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forget_password);
        final EditText editTextContactNoToVerify = (EditText) dialog.findViewById(R.id.editText_contact_no_to_verify);
//        TextInputLayout inputLayoutEditTextEmailToVerify= (TextInputLayout) dialog.findViewById(R.id.input_layout_editText_email_to_verify);
        Button buttonSendOTP = (Button) dialog.findViewById(R.id.button_send_otp);

        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contactNoToVerify = editTextContactNoToVerify.getText().toString();

                if (contactNoToVerify.isEmpty() || contactNoToVerify.length() != 10 || !Patterns.PHONE.matcher(contactNoToVerify).matches()) {
                    editTextContactNoToVerify.setError(Html.fromHtml("<font color='red'>Enter valid contact no</font>"));
                } else {
                    editTextContactNoToVerify.setError(null);
                    showHideKeyboard();
                    dialog.dismiss();
                    //call Webservice to send otp
                    requestForForgetPassword(contactNoToVerify);
                }
            }
        });
        Button buttonCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void requestForForgetPassword(final String contactNo) {
        showHideKeyboard();
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_FORGET_PASSWORD + contactNo + "/",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + "Forgot password", response.toString());
                        Log.d(TAG + "Forgot password", "onResponse: URL=" + ConfigUrls.URL_FORGET_PASSWORD + contactNo + "/");
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                snackbar = singletonUtil.showSnackBar("OTP sent successfully", (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                                        intent.putExtra("contactNo", contactNo);
                                        startActivity(intent);
                                        snackbar.dismiss();
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                }, 1000);

                            } else {
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                singletonUtil.showSnackBar("Network Problem!! Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                progressBar.setVisibility(View.GONE);
            }
        }) {
        };

        String tag_string_req = "json_request";
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

}
