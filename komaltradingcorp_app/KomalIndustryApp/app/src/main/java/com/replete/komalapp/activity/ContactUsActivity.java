package com.replete.komalapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.replete.komalapp.R;
import com.replete.komalapp.utils.GpsTracker;
import com.replete.komalapp.utils.SingletonUtil;

import org.apache.http.util.TextUtils;

import java.util.Locale;

/**
 * Created by MR JOSHI on 09-Jun-16.
 */
public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewCall1, imageViewCall2, imageViewCall3, imageViewCall4;
    private ImageView imageViewGoDirection;
    private TextView textView_tel_no;
    private TextView textView_cust_care_no;
    private TextView textView_call_2;
    private TextView textView_call_1;
    private String TAG = "ContactUsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");

//        imageViewCall=(ImageView) findViewById(R.id.imageViewCall);
        imageViewGoDirection = (ImageView) findViewById(R.id.imageViewGoDirection);
        imageViewCall1 = (ImageView) findViewById(R.id.imageViewCall1);
        imageViewCall2 = (ImageView) findViewById(R.id.imageViewCall2);
        imageViewCall3 = (ImageView) findViewById(R.id.imageViewTellCall);
        imageViewCall4 = (ImageView) findViewById(R.id.imageView_cust_call_1);
        textView_tel_no = (TextView) findViewById(R.id.textView_tel_no);
        textView_cust_care_no = (TextView) findViewById(R.id.textView_cust_care_no);
        textView_call_2 = (TextView) findViewById(R.id.textView_call_2);
        textView_call_1 = (TextView) findViewById(R.id.textView_call_1);

        imageViewGoDirection.setOnClickListener(this);
        imageViewCall1.setOnClickListener(this);
        imageViewCall2.setOnClickListener(this);
        imageViewCall3.setOnClickListener(this);
        imageViewCall4.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageViewGoDirection:

                if (SingletonUtil.getSingletonConfigInstance().checkGPS(this)) {
                    int locationMode = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        try {
                            locationMode = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
                        } catch (Settings.SettingNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    if (locationMode == 3) {
                        GpsTracker gps = new GpsTracker(this);
                        String Latitude = null;
                        String Longitude = null;

                        if (gps.canGetLocation()) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            Latitude = String.valueOf(latitude);
                            Longitude = String.valueOf(longitude);
                            Log.d(TAG, "Current Lat= " + Latitude + "Current Long= " + Longitude);
                            //set Intent to redirect to google map
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                                    latitude, longitude, "Your location", 19.25242, 72.85783, "Komal Trading Corporation");
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                            startActivity(intent);
                        } else {
//                    MainActivity.showSnackBar("Unable to connect server!! Try again", (RelativeLayout) findViewById(R.id.relativeLayout));
                            Toast.makeText(this, "Unable to connect to GPS..Please try Again", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        final AlertDialog.Builder adb2 = new AlertDialog.Builder(
                                ContactUsActivity.this);
                        adb2.setMessage("Please turn ON High Accuracy Mode in setting");
                        adb2.setPositiveButton("SETTING", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });
                        adb2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        final AlertDialog alertDialog = adb2.create();
                        Window view = ((AlertDialog) alertDialog).getWindow();
                        view.setBackgroundDrawableResource(R.color.white);
                        adb2.show();
                    }
                } else {
                    final AlertDialog.Builder adb2 = new AlertDialog.Builder(
                            ContactUsActivity.this);
                    adb2.setMessage("Please turn ON GPS");
                    adb2.setPositiveButton("SETTING", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    adb2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    final AlertDialog alertDialog = adb2.create();
                    Window view = ((AlertDialog) alertDialog).getWindow();
                    view.setBackgroundDrawableResource(R.color.white);
                    adb2.show();
                }
                break;

            case R.id.imageViewCall1:
                callToContact(textView_call_1.getText().toString());
                break;

            case R.id.imageViewCall2:
                callToContact(textView_call_2.getText().toString());
                break;

            case R.id.imageViewTellCall:
                callToContact(textView_tel_no.getText().toString());
                break;
            case R.id.imageView_cust_call_1:
                callToContact(textView_cust_care_no.getText().toString());
                break;
        }
    }

    private void callToContact(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }


}