package com.replete.komalapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.replete.komalapp.R;

/**
 * Created by MR JOSHI on 28-Mar-16.
 */
public class WebViewPayNowActivity extends AppCompatActivity {
    private static final String TAG = "WebViewBookService";
    String postData;
    String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = new WebView(this);
        setContentView(webView);

        String url = getIntent().getExtras().getString("url");

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("WebView", "url=" + url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("Webview", "page finished" + url);
            /*if (url.contains("success.php")) {
                //call intent to navigate to activity
                Log.d("Webview", "URL on success: " + url);

            } else if (url.contains("failure.php")) {
                Log.d("Webview", "URL on failure: " + url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }*/
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WebViewPayNowActivity.this, MainProductListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();

    }
}
