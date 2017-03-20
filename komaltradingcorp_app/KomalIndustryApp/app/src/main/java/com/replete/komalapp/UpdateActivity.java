package com.replete.komalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Replete Android on 9/1/2016.
 */
public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnUpdate:

                String url = "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(url));
                    startActivity(viewIntent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;

            case R.id.btnCancel:
                finish();
                break;

        }

    }
}