package com.replete.komalapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.R;
import com.replete.komalapp.activity.LoginActivity;
import com.replete.komalapp.activity.MainActivity;
import com.replete.komalapp.rowitem.Category;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 14-Mar-16.
 */
public class SingletonUtil {
    private static SingletonUtil singletonUtil;

    private String TAG = "SingletonUtil";

    List<Category> categories = new ArrayList<>();

    private SingletonUtil() {
    }

    public static SingletonUtil getSingletonConfigInstance() {
        if (singletonUtil == null)
            singletonUtil = new SingletonUtil();
        return singletonUtil;
    }

    /**
     * display message in snackbar
     *
     * @param message        Message to show in snackbar
     * @param relativeLayout RelativeLayout in which Snackbar is to be shown
     */
    public static Snackbar showSnackBar(String message, RelativeLayout relativeLayout) {

        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
        return snackbar;
    }


    /**
     * common method for webservice call and getting JSONobj
     *
     * @param url         : to call particular webservice
     * @param requestJson : response fetched from web service
     */
    public void requestToJson(String url, JSONObject requestJson) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, requestJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //returns complete jsonObj
                        returnJSONResponse(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //returens "VolleyError" String if volley error occured
                returnJSONResponse("VolleyError");
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);

    }

    /**
     * returns String having either JSONREsponse or "VolleyError"
     *
     * @param response
     * @return
     */
    private String returnJSONResponse(String response) {
        return response;
    }


    public boolean isConnectingToInternet(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() == true) {
            return true;
        }
        return false;
    }

    public boolean checkNetConnection(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        boolean wifiConnectionStatus = false;
        boolean mobileConnectionStatus = false;
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                wifiConnectionStatus = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                mobileConnectionStatus = true;
            }
        } else {
            // not connected to the internet
            Toast.makeText(context, context.getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();
        }

        if (mobileConnectionStatus == true || wifiConnectionStatus == true) return true;

        else return false;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "16", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public Bitmap getByteToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //convert String to Bitmap
    public Bitmap getBitmapFromString(String galImage) {
        byte[] imageAsBytes = Base64.decode(galImage.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return bitmap;
    }

    public boolean checkGPS(Context context) {
        boolean isGPSON = false;
        LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            isGPSON = false;
        } else
            isGPSON = true;
        return isGPSON;
    }

    public void showDialogToLogin(final Context context) {

        AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(context);
        removeItemDialog.setMessage("You are not logged in yet!! Please login to continue");
        removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

        removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog alertDialog = removeItemDialog.create();
        Window view = ((AlertDialog) alertDialog).getWindow();
        view.setBackgroundDrawableResource(R.color.white);
        alertDialog.show();
    }
}
