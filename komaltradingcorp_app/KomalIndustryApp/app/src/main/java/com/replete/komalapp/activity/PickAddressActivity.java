package com.replete.komalapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.replete.komalapp.Config.Constants;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.ShippingAddress;

/**
 * Created by MR JOSHI on 26-Mar-16.
 */
public class PickAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPinCode;
    private EditText editTextAddress;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextContact;
    private EditText editTextAlternateContact;
    private Button buttonProceed;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    SharedPreferences tempSharedPref;
    private String TAG = "PickAddressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ShippingAddress shippingAddress = databaseHandler.getShippingAddress();
            Log.d(TAG, "SHIPPING ADDRESS OBJ VALUE=" + shippingAddress.toString());
            tempSharedPref = getSharedPreferences(Constants.TEMP_SHARED_PREF, MODE_PRIVATE);
            if (shippingAddress != null && tempSharedPref.getBoolean("shouldNotEdit", true)) {
                Log.d(TAG, "IF NOT NULL" + shippingAddress.toString());
                Intent newIntent = new Intent(this, ReviewProductActivity.class);
                startActivity(newIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_shipping_address);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shipping address");

        tempSharedPref = getSharedPreferences(Constants.TEMP_SHARED_PREF, MODE_PRIVATE);

        editTextPinCode = (EditText) findViewById(R.id.input_pincode);
        editTextAddress = (EditText) findViewById(R.id.input_address);
        editTextCity = (EditText) findViewById(R.id.input_city);
        editTextState = (EditText) findViewById(R.id.input_state);
        editTextContact = (EditText) findViewById(R.id.input_contact);
        editTextAlternateContact = (EditText) findViewById(R.id.input_alternate_contact);
        buttonProceed = (Button) findViewById(R.id.button_proceed_to_review);
        buttonProceed.setOnClickListener(this);
        editTextAddress.setMovementMethod(new ScrollingMovementMethod());

        try {
            ShippingAddress shippingAddress = databaseHandler.getShippingAddress();
            Log.d(TAG, "SHIPPING ADDRESS OBJ VALUE=" + shippingAddress.toString());
            if (shippingAddress != null) {
                editTextPinCode.setText(shippingAddress.getPincode());
                editTextAddress.setText(shippingAddress.getAddress());
                editTextCity.setText(shippingAddress.getCity());
                editTextState.setText(shippingAddress.getState());
//                editTextContact.setText(shippingAddress.getContactNo());
//                editTextAlternateContact.setText(shippingAddress.getAlternateContactNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!tempSharedPref.getBoolean("shouldNotEdit", true)) {
            //on back pressed , if was redistected from ReviewActivity than it go back there only
            Intent newIntent = new Intent(this, ReviewProductActivity.class);
            SharedPreferences.Editor editor = tempSharedPref.edit();
            editor.putBoolean("shouldNotEdit", true);
            editor.commit();

            startActivity(newIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private boolean validate() {
        boolean valid = true;
        String pincode = editTextPinCode.getText().toString();
        String contact = editTextContact.getText().toString();
        String alternateContact = editTextAlternateContact.getText().toString();
        String city = editTextCity.getText().toString();
        String state = editTextState.getText().toString();
        String address = editTextAddress.getText().toString();


        if (pincode.isEmpty() || pincode.length() < 6) {
            editTextPinCode.setError(Html.fromHtml("<font color='red'>Enter at least 6 numbers</font>"));
            requestFocus(editTextPinCode);
            valid = false;
        } else
            editTextPinCode.setError(null);


        if (city.isEmpty() || city.length() < 3) {
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


        if (address.isEmpty()) {
            editTextAddress.setError(Html.fromHtml("<font color='red'>Enter valid address name</font>"));
            requestFocus(editTextAddress);
            valid = false;
        } else
            editTextAddress.setError(null);


        if (contact.isEmpty() || contact.length() != 10 || !Patterns.PHONE.matcher(contact).matches()) {
            editTextContact.setError(Html.fromHtml("<font color='red'>Enter valid contact no</font>"));
            requestFocus(editTextContact);
            valid = false;
        } else
            editTextContact.setError(null);


       /* if (pincode.isEmpty() || pincode.length() < 6) {
            ((TextInputLayout) findViewById(R.id.input_layout_pincode)).setError(Html.fromHtml("<font color='red'>Enter at least 6 numbers</font>"));
            requestFocus(editTextPinCode);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_pincode)).setErrorEnabled(false);
            requestFocus(editTextPinCode);
            editTextPinCode.setHintTextColor(this.getResources().getColor(R.color.primary));

        }

        if (city.isEmpty() || city.length() < 3) {
            ((TextInputLayout) findViewById(R.id.input_layout_city)).setError(Html.fromHtml("<font color='red'>Enter valid city name</font>"));
            requestFocus(editTextCity);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_city)).setErrorEnabled(false);
            requestFocus(editTextCity);
            editTextCity.setHintTextColor(this.getResources().getColor(R.color.primary));
        }

        if (state.isEmpty() || state.length() < 3) {
            ((TextInputLayout) findViewById(R.id.input_layout_state)).setError(Html.fromHtml("<font color='red'>Enter valid state name</font>"));
            requestFocus(editTextState);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_state)).setErrorEnabled(false);
            requestFocus(editTextState);
            editTextState.setHintTextColor(this.getResources().getColor(R.color.primary));
        }
        if (address.isEmpty()) {
            ((TextInputLayout) findViewById(R.id.input_layout_address)).setError(Html.fromHtml("<font color='red'>Enter valid address name</font>"));
            requestFocus(editTextAddress);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_address)).setErrorEnabled(false);
            requestFocus(editTextAddress);
            editTextAddress.setHintTextColor(this.getResources().getColor(R.color.primary));
        }
        if (contact.isEmpty() || contact.length() != 10 || !Patterns.PHONE.matcher(contact).matches()) {
            ((TextInputLayout) findViewById(R.id.input_layout_contact)).setError(Html.fromHtml("<font color='red'>Enter valid contact no</font>"));
            requestFocus(editTextContact);
            valid = false;
        } else {
            ((TextInputLayout) findViewById(R.id.input_layout_contact)).setErrorEnabled(false);
            requestFocus(editTextContact);
            editTextContact.setHintTextColor(this.getResources().getColor(R.color.primary));
        }

*/
        return valid;
    }


    public void showHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed_to_review:
                if (!validate())
                    Toast.makeText(this, "Please fill valid details", Toast.LENGTH_SHORT).show();
                else {
                    Bundle b = getIntent().getExtras();
                    /*ShippingAddress shippingAddress = new ShippingAddress(editTextPinCode.getText().toString(), editTextAddress.getText().toString(),
                            editTextCity.getText().toString(), editTextState.getText().toString(),
                            editTextContact.getText().toString(), editTextAlternateContact.getText().toString().equals(null) ? "" : editTextAlternateContact.getText().toString());
                    databaseHandler.addShippingAddress(shippingAddress);
                    Intent newIntent = new Intent(this, ReviewProductActivity.class);
                    SharedPreferences.Editor editor = tempSharedPref.edit();
                    editor.putBoolean("shouldNotEdit", true);
                    editor.commit();
                    startActivity(newIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();*/

                }
                break;
        }
    }
}
