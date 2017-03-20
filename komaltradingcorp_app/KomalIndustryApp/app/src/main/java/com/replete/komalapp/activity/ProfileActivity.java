package com.replete.komalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.R;
import com.replete.komalapp.StaggeredGridView.StaggeredGridView;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.recyclerutils.State;
import com.replete.komalapp.rowitem.AssociatedDistributor;
import com.replete.komalapp.rowitem.ShippingAddress;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MR JOSHI on 01-Apr-16.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPinCode;
    private EditText editTextAddress;
    //    private EditText editTextCity;
//    private EditText editTextState;
    private Spinner spinnerState;
    private Spinner spinnerCity;

    private EditText editTextFirstName, editTextEmail, editTextContactNo, editTextDisplayName, editTextPanNo,
            editTextVatTinNo, editTextUserType;
    private Button buttonSave;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    //    SharedPreferences tempSharedPref;
    private String TAG = "ProfileActivity";
    private SwitchCompat swEditProfile;
    private String userTrackId;
    private ProgressBar progressBar;
    private TextView textView_change_password_link;
    //    private TextView textView_change_associated_distributor_link;
    private HashMap<String, String> userDetails;
    private SingletonUtil singletonUtil;
    private EditText editTextLastName;
    private String currentState;
    private ArrayList<State> stateList;
    private String selectedCity;
    private String selectedState;
    private String SELECT_STATE = "Select State";
    private String SELECT_CITY = "Select City";
    private EditText editTextMark;
    private EditText editTextDestination;
    private EditText editTextTransporterName;
    private int shippingAddressId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        callToInitComponents();
//        setUserDetailsFromDB();
        callToGetProfile();

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if (currentState != spinnerState.getItemAtPosition(position).toString()) {
//                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ProfileActivity.this,
//                        R.layout.category_simple_spinner_item_category, new ArrayList<String>());
//                spinnerCity.setAdapter(emptyAdapter);

                ArrayList<String> cityStringList = new ArrayList<>();
                if (position >= 1) {
                    State state = stateList.get(position - 1);
                    callTogetCity(state.getStateId());
                }
                if (spinnerState.getItemAtPosition(position).toString().equals(SELECT_STATE)) {
                    cityStringList.add(SELECT_CITY);

                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                            R.layout.simple_spinner_dropdown_item, cityStringList);
                    spinnerCity.setAdapter(cityAdapter);
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        swEditProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isChecked) {
                    if (imm != null) {
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                    showHideEditTexts(true);
                    editTextFirstName.requestFocus();
                    textView_change_password_link.setVisibility(View.GONE);
//                    textView_change_associated_distributor_link.setVisibility(View.GONE);
                    buttonSave.setEnabled(true);
                    buttonSave.setClickable(true);

                } else {
                    View view = ProfileActivity.this.getCurrentFocus();
                    if (view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    showHideEditTexts(false);
                    textView_change_password_link.setVisibility(View.VISIBLE);
                    /*if (userDetails.get("userType").equals(getString(R.string.USER_TYPE_DEALER))) {
                        textView_change_associated_distributor_link.setVisibility(View.VISIBLE);
                    }*/
                    buttonSave.setEnabled(false);
                    buttonSave.setClickable(false);
                }
            }
        });

        if (swEditProfile.isChecked()) {
            showHideEditTexts(true);
        }
    }

    private void callToInitComponents() {

        singletonUtil = SingletonUtil.getSingletonConfigInstance();

        editTextFirstName = (EditText) findViewById(R.id.input_first_name);
        editTextLastName = (EditText) findViewById(R.id.input_last_name);
        editTextPinCode = (EditText) findViewById(R.id.input_pincode);
        editTextAddress = (EditText) findViewById(R.id.input_address);
        editTextMark = (EditText) findViewById(R.id.input_mark);
        editTextDestination = (EditText) findViewById(R.id.input_destination);
        editTextTransporterName = (EditText) findViewById(R.id.input_transName);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextContactNo = (EditText) findViewById(R.id.input_contact_no);
        editTextDisplayName = (EditText) findViewById(R.id.input_display_name);
        editTextPanNo = (EditText) findViewById(R.id.input_pan_no);
        editTextVatTinNo = (EditText) findViewById(R.id.input_vat_tin_no);
        editTextUserType = (EditText) findViewById(R.id.input_user_type);
        swEditProfile = (SwitchCompat) findViewById(R.id.swEditProfile);
        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        textView_change_password_link = (TextView) findViewById(R.id.textView_change_password_link);
//        textView_change_associated_distributor_link = ((TextView) findViewById(R.id.textView_change_associated_distributor_link));
        textView_change_password_link.setOnClickListener(this);
        buttonSave = (Button) findViewById(R.id.button_update);
        buttonSave.setOnClickListener(this);

        buttonSave.setEnabled(false);
        buttonSave.setClickable(false);

        userDetails = databaseHandler.getUserDetails();
        stateList = new ArrayList<>();
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
/*
    private void setUserDetailsFromDB() {
//        userDetails = databaseHandler.getUserDetails();
        editTextFirstName.setText(userDetails.get("firstName"));
//        editTextLastName.setText(userDetails.get("lastName"));
        editTextEmail.setText(userDetails.get("email"));
        editTextContactNo.setText(userDetails.get("contact_no"));
        editTextVatTinNo.setText(userDetails.get("tinNo"));
        editTextPanNo.setText(userDetails.get("panNo"));
        editTextUserType.setText(userDetails.get("userType"));
        editTextDisplayName.setText(userDetails.get("displayname"));
        userDetails.get("status");
        userDetails.get("companyInfoId");
        userTrackId = userDetails.get("userTrackId");

        if (userDetails.get("userType").equals(getString(R.string.USER_TYPE_DEALER))) {
            textView_change_associated_distributor_link.setVisibility(View.VISIBLE);
            textView_change_associated_distributor_link.setOnClickListener(this);
        } else {
            textView_change_associated_distributor_link.setVisibility(View.GONE);
        }
    }*/

    private void showHideEditTexts(boolean value) {
        editTextFirstName.setFocusableInTouchMode(value);
        editTextFirstName.setFocusable(value);
        editTextFirstName.setCursorVisible(value);

        editTextLastName.setFocusableInTouchMode(value);
        editTextLastName.setFocusable(value);
        editTextLastName.setCursorVisible(value);

        editTextEmail.setFocusableInTouchMode(value);
        editTextEmail.setFocusable(value);
        editTextEmail.setCursorVisible(value);

        editTextDisplayName.setFocusableInTouchMode(value);
        editTextDisplayName.setFocusable(value);
        editTextDisplayName.setCursorVisible(value);

        editTextPinCode.setFocusableInTouchMode(value);
        editTextPinCode.setFocusable(value);
        editTextPinCode.setCursorVisible(value);

        editTextAddress.setFocusableInTouchMode(value);
        editTextAddress.setFocusable(value);
        editTextAddress.setCursorVisible(value);

        editTextMark.setFocusableInTouchMode(value);
        editTextMark.setFocusable(value);
        editTextMark.setCursorVisible(value);

        editTextDestination.setFocusableInTouchMode(value);
        editTextDestination.setFocusable(value);
        editTextDestination.setCursorVisible(value);

        editTextTransporterName.setFocusableInTouchMode(value);
        editTextTransporterName.setFocusable(value);
        editTextTransporterName.setCursorVisible(value);


        spinnerCity.setFocusableInTouchMode(value);
        spinnerCity.setFocusable(value);
        spinnerCity.setEnabled(value);


        spinnerState.setFocusableInTouchMode(value);
        spinnerState.setFocusable(value);
        spinnerState.setEnabled(value);

    }

    private boolean validate() {
        boolean valid = true;
//        String contactNo = editTextContactNo.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String pincode = editTextPinCode.getText().toString();
//        String city = spinnerCity.getSelectedItem().toString();
//        String state = editTextState.getText().toString();
//        String city=spinnerCity.getSelectedItem().toString();
//        String state=spinnerState.getSelectedItem().toString();
        String address = editTextAddress.getText().toString();
        String mark = editTextMark.getText().toString();
        String destination = editTextDestination.getText().toString();
        String transporterName = editTextTransporterName.getText().toString();


        if (pincode.isEmpty() || pincode.length() < 6) {
            editTextPinCode.setError(Html.fromHtml("<font color='red'>Enter at least 6 numbers</font>"));
            requestFocus(editTextPinCode);
            valid = false;
        } else
            editTextPinCode.setError(null);



      /*  if (city.isEmpty() || city.length() < 3) {
            editTextCity.setError(Html.fromHtml("<font color='red'>Enter valid city name</font>"));
            requestFocus(editTextCity);
            valid = false;
        } else
            editTextCity.setError(null);


        if (state.isEmpty() || state.length() < 3) {
            editTextState.setError(Html.fromHtml("<font color='red'>Enter valid state name</font>"));
            requestFocus(editTextState);
            valid = false;
        } else
            editTextState.setError(null);
*/
        if (address.isEmpty()) {
            editTextAddress.setError(Html.fromHtml("<font color='red'>Enter valid address name</font>"));
            requestFocus(editTextAddress);
            valid = false;
        } else
            editTextAddress.setError(null);

        if (firstName.isEmpty() || firstName.length() < 3) {
            editTextFirstName.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            requestFocus(editTextFirstName);
            valid = false;
        } else editTextFirstName.setError(null);

        if (mark.isEmpty() || mark.length() < 3) {
            editTextMark.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            requestFocus(editTextMark);
            valid = false;
        } else editTextMark.setError(null);

        if (destination.isEmpty() || destination.length() < 3) {
            editTextDestination.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            requestFocus(editTextDestination);
            valid = false;
        } else editTextDestination.setError(null);

        if (transporterName.isEmpty() || transporterName.length() < 3) {
            editTextTransporterName.setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            requestFocus(editTextTransporterName);
            valid = false;
        } else editTextTransporterName.setError(null);

       /* if (contactNo.isEmpty() || contactNo.length() != 10 || !Patterns.PHONE.matcher(contactNo).matches()) {
            editTextContactNo.setError(Html.fromHtml("<font color='red'>Enter valid contact number</font>"));
            requestFocus(editTextContactNo);
            valid = false;
        } else editTextContactNo.setError(null);
*/


       /* if (name.isEmpty() || name.length() < 3) {
            ((TextInputLayout) findViewById(R.id.input_layout_first_name)).setError(Html.fromHtml("<font color='red'>Enter at least 3 characters</font>"));
            requestFocus(editTextFirstName);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_first_name)).setErrorEnabled(false);
            requestFocus(editTextFirstName);
            editTextFirstName.setHintTextColor(this.getResources().getColor(R.color.primary));
        }

        if (contactNo.isEmpty() || contactNo.length() != 10 || !Patterns.PHONE.matcher(contactNo).matches()) {

            ((TextInputLayout) findViewById(R.id.input_layout_contact_no)).setError(Html.fromHtml("<font color='red'>Enter valid contact number</font>"));
            requestFocus(editTextContactNo);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_contact_no)).setErrorEnabled(false);
            requestFocus(editTextContactNo);
            editTextContactNo.setHintTextColor(this.getResources().getColor(R.color.primary));
        }*/
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                if (validate()) {
                    if (!spinnerState.getSelectedItem().toString().equals(SELECT_STATE)) {
                        if (!spinnerCity.getSelectedItem().toString().equals(SELECT_CITY)) {
                            try {
                                showHideKeyboard();
                                if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this))
                                    callToUpdateProfile(userDetails.get("userTrackId"));
                                else
                                    Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Enter details before update", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(this, "Please select city name", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Please select state name", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Please fill valid details", Toast.LENGTH_SHORT).show();
                break;
           /* case R.id.textView_change_associated_distributor_link:
                Intent intent = new Intent(ProfileActivity.this, DistributorActivity.class);
                startActivity(intent);
                break;
*/
            case R.id.textView_change_password_link:
                Intent intent2 = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void callToUpdateProfile(final String userTrackId) {
        progressBar.setVisibility(View.VISIBLE);
        JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            requestJson.put("firstName", editTextFirstName.getText().toString());
            requestJson.put("lastName", editTextLastName.getText().toString());
            requestJson.put("email", editTextEmail.getText().toString() == null ? "" : editTextEmail.getText().toString());
            requestJson.put("displayName", editTextDisplayName.getText().toString() == null ? "" : editTextDisplayName.getText().toString());
            requestJson.put("pincode", editTextPinCode.getText().toString());
            requestJson.put("address", editTextAddress.getText().toString());
            requestJson.put("city", spinnerCity.getSelectedItem().toString() == null ? "" : spinnerCity.getSelectedItem().toString());
            requestJson.put("state", spinnerState.getSelectedItem().toString() == null ? "" : spinnerState.getSelectedItem().toString());

            requestJson.put("mark", editTextMark.getText().toString());
            requestJson.put("destination", editTextDestination.getText().toString());
            requestJson.put("tranNm", editTextTransporterName.getText().toString());


            finalObject.put("request", requestJson);
            Log.d(TAG, "finalObject" + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, ConfigUrls.URL_UPDATE_PROFILE + userTrackId, finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Log.d(TAG, ConfigUrls.URL_UPDATE_PROFILE + userTrackId);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                JSONObject updatedResponseObj = response.getJSONObject("request");
                                databaseHandler.updateUserDetails(userTrackId, updatedResponseObj.getString("firstName"), updatedResponseObj.getString("lastName"));

                                ShippingAddress shippingAddress = new ShippingAddress(shippingAddressId, updatedResponseObj.getString("pincode"), updatedResponseObj.getString("address"),
                                        updatedResponseObj.getString("city"), updatedResponseObj.getString("state"),
                                        updatedResponseObj.getString("mark"), updatedResponseObj.getString("destination"), updatedResponseObj.getString("tranNm")
                                        , updatedResponseObj.getString("tinNo"));
                                databaseHandler.addShippingAddress(shippingAddress);

//                                setUserDetailsFromDB();
                                swEditProfile.setChecked(false);
                                Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    /**
     * call to get profile details from server
     */

    private void callToGetProfile() {

        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_PROFILE + userDetails.get("userTrackId"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        Log.d(TAG, "onResponse: " + ConfigUrls.URL_GET_PROFILE + userDetails.get("userTrackId"));
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {

                                JSONObject userDetailsObj = response.getJSONObject("userDetails");
                                editTextFirstName.setText(userDetailsObj.getString("firstName"));
                                editTextLastName.setText(userDetailsObj.getString("lastName"));
                                editTextEmail.setText(userDetailsObj.getString("loginId"));
                                editTextContactNo.setText(userDetailsObj.getString("cntc_num"));
                                editTextVatTinNo.setText(userDetailsObj.getString("tinNo"));
                                editTextPanNo.setText(userDetailsObj.getString("panNo"));


                                selectedCity = userDetailsObj.getString("city");
                                Log.d(TAG, "onResponse: |=" + selectedCity);

                                selectedState = userDetailsObj.getString("state");

//default value to state
                                /*ArrayList<String> stateStringList = new ArrayList<>();
                                if (selectedState != null) {
                                    stateStringList.add(selectedState);
                                } else
                                    stateStringList.add("Select State");

                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                        R.layout.category_simple_spinner_item_category, stateStringList);
                                spinnerState.setAdapter(stateAdapter);*/


                                //default value to city
                                ArrayList<String> cityStringList = new ArrayList<>();

                                if (selectedCity.equals("null") || selectedCity.isEmpty())
                                    cityStringList.add(SELECT_CITY);
                                else
                                    cityStringList.add(selectedCity);

                                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                        R.layout.simple_spinner_dropdown_item, cityStringList);
                                spinnerCity.setAdapter(cityAdapter);

                                shippingAddressId = Integer.parseInt(userDetailsObj.getString("otherAddressId"));
                                editTextUserType.setText(userDetailsObj.getString("userType") == "null" ? "" : userDetailsObj.getString("userType"));
                                editTextDisplayName.setText(userDetailsObj.getString("displayName") == "null" ? "" : userDetailsObj.getString("displayName"));

                                editTextPinCode.setText(userDetailsObj.getString("postalCode") == "null" ? "" : userDetailsObj.getString("postalCode"));
                                editTextAddress.setText(userDetailsObj.getString("stAddress1") == "null" ? "" : userDetailsObj.getString("stAddress1"));

                                editTextMark.setText(userDetailsObj.getString("mark") == "null" ? "" : userDetailsObj.getString("mark"));
                                editTextDestination.setText(userDetailsObj.getString("destination") == "null" ? "" : userDetailsObj.getString("destination"));
                                editTextTransporterName.setText(userDetailsObj.getString("tranNm") == "null" ? "" : userDetailsObj.getString("tranNm"));

//                                editTextState.setText(userDetailsObj.getString("state") == "null" ? "" : userDetailsObj.getString("state"));
//                                editTextCity.setText(userDetailsObj.getString("city") == "null" ? "" : userDetailsObj.getString("city"));

//                                userDetails.get("status");
//                                userDetails.get("companyInfoId");
//                                userTrackId = userDetails.get("userTrackId");

                                callTogetState();

                                /*if (userDetailsObj.getJSONArray("associatedDistributorsList").length() > 0) {
                                    //when user logged in second time
//                                    List<AssociatedDistributor> associatedDistributorList = new ArrayList<>();
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
                                } else if (userDetailsObj.getJSONArray("associatedDistributorsList").length() == 0) {

                                }*/

                                ShippingAddress shippingAddress = new ShippingAddress(shippingAddressId, userDetailsObj.getString("postalCode"), userDetailsObj.getString("stAddress1"),
                                        userDetailsObj.getString("city"), userDetailsObj.getString("state"),
                                        userDetailsObj.getString("mark"), userDetailsObj.getString("destination"), userDetailsObj.getString("tranNm"),
                                        userDetailsObj.getString("tinNo"));
                                databaseHandler.addShippingAddress(shippingAddress);
/*
                                if (userDetailsObj.getString("userType").equals(getString(R.string.USER_TYPE_DEALER))) {
                                    textView_change_associated_distributor_link.setVisibility(View.VISIBLE);
                                    textView_change_associated_distributor_link.setOnClickListener(ProfileActivity.this);
                                } else {
                                    textView_change_associated_distributor_link.setVisibility(View.GONE);
                                }*/
                                showHideEditTexts(false);
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
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);

    }

    private void callTogetState() {

        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_STATE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            int currentStateID = 0;
                            if (responseObj.getString("status").equals("200")) {
                                JSONArray userDetailsArrayObj = response.getJSONArray("response");
                                ArrayList<String> stateStringList = new ArrayList<>();
                                State stateObj = null;
                                stateStringList.add(SELECT_STATE);

                                for (int i = 0; i < userDetailsArrayObj.length(); i++) {
                                    currentState = userDetailsArrayObj.getJSONObject(0).getString("locationName");
                                    currentStateID = userDetailsArrayObj.getJSONObject(0).getInt("locationId");
                                    JSONObject jsonObject = userDetailsArrayObj.getJSONObject(i);
                                    stateObj = new State(jsonObject.getInt("locationId"), jsonObject.getString("locationName"));

                                    stateList.add(stateObj);
                                    stateStringList.add(jsonObject.getString("locationName"));
                                }

                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                        R.layout.simple_spinner_dropdown_item, stateStringList);
                                spinnerState.setAdapter(stateAdapter);

                                if (selectedState != null || !selectedState.isEmpty()) {
                                    spinnerState.setSelection(stateAdapter.getPosition(selectedState));
                                }

//                                callTogetCity(currentStateID);

                              /*  spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if (currentState != spinnerState.getItemAtPosition(position).toString()) {
                                            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                                    R.layout.category_simple_spinner_item_category, new ArrayList<String>());
                                            spinnerCity.setAdapter(emptyAdapter);
                                            State state = stateList.get(position);
                                            callTogetCity(state.getStateId());
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });*/


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
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    private void callTogetCity(int stateId) {
        progressBar.setVisibility(View.VISIBLE);
        String urlStr = ConfigUrls.URL_GET_CITY + "?stateId=" + stateId;
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URI uri = null;
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            if (responseObj.getString("status").equals("200")) {
                                JSONArray userDetailsArrayObj = response.getJSONArray("response");
                                ArrayList<String> cityList = new ArrayList<>();

                                cityList.add(SELECT_CITY);
                                for (int i = 0; i < userDetailsArrayObj.length(); i++) {
//                                    currentState = userDetailsArrayObj.getJSONObject(0).getString("locationName");
                                    JSONObject jsonObject = userDetailsArrayObj.getJSONObject(i);
                                    cityList.add(jsonObject.getString("locationName"));
                                }

                                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                        R.layout.simple_spinner_dropdown_item, cityList);
                                spinnerCity.setAdapter(cityAdapter);
//                                spinnerCity.setSelection(cityAdapter.getPosition(selectedCity));

                                if (!selectedCity.equals("null") || !selectedCity.isEmpty()) {
                                    spinnerCity.setSelection(cityAdapter.getPosition(selectedCity));
                                }

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
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    /**
     * hide and show keyboard
     */
    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.spinnerCity:
                break;

            case R.id.spinnerState:
                if (currentState != spinnerState.getItemAtPosition(position).toString()) {
                    ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                            R.layout.category_simple_spinner_item_category, new ArrayList<String>());
                    spinnerCity.setAdapter(emptyAdapter);
                    State state = stateList.get(position);
                    callTogetCity(state.getStateId());
                }

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
