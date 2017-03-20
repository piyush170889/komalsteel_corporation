package com.replete.komalapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by MR JOSHI on 25-Apr-16.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String url="http://physiotherapistpune.in/kiimages/Kawla.png";
        Picasso.with(this).load(url).into(imageView);
    }

}
