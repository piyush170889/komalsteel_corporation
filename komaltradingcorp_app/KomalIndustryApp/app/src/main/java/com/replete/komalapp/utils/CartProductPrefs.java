package com.replete.komalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.replete.komalapp.rowitem.CartProducts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MR JOSHI on 17-Mar-16.
 */
public class CartProductPrefs {
    /**
     * This application's preferences label
     */
    private static final String PREFS_NAME = "com.our.package.CartProductsPrefs";
    private String TAG = "CartProductPrefs";
    private  List<CartProducts>  cartProductsList = new ArrayList<>(); ;
    /**
     * This application's preferences
     */
    private static SharedPreferences settings;
    /**
     * This application's settings editor
     */
    private static SharedPreferences.Editor editor;

    /**
     * Constructor takes an android.content.Context argument
     */
    public CartProductPrefs(Context ctx) {
        if (settings == null) {
            settings = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
       /*
        * Get a SharedPreferences editor instance.
        * SharedPreferences ensures that updates are atomic
        * and non-concurrent
        */
        editor = settings.edit();
    }
  /*  ** The prefix for flattened CartProducts keys */

    public static final String KEY_PREFIX = "com.our.package.KEY";

    /**
     * Method to return a unique key for any field belonging to a given object
     *
     * @param id of the object
     * @return key String uniquely identifying the object's field
     */

    private String getFieldKey(int id) {
        return KEY_PREFIX + id;
    }

    /**
     * Store or Update
     */
    public void setCartProducts(CartProducts cartProducts) {
        if (cartProducts == null)
            return; // don't bother
        Gson gson = new Gson();
        String product_json = gson.toJson(cartProducts);
// store in SharedPreferences
        String id = "" + cartProducts.getProductId(); // get storage key
        editor.putString(id, product_json);
        editor.commit();
    }

    /**
     * Retrieve
     */
    public List<CartProducts> getCartProducts() {
        Map<String, ?> keysMap = settings.getAll();
        Log.d(TAG, "size=" + cartProductsList.size());
        Set<String> allKeys = keysMap.keySet();
        Log.d(TAG, "allKeys" + allKeys);
        for (String keyValue : allKeys) {
            Log.d(TAG, "keyValue" + keyValue);
            String product_json = settings.getString(keyValue, "");
            Gson gson = new Gson();
            CartProducts cartProducts = gson.fromJson(product_json, CartProducts.class);
            cartProductsList.add(cartProducts);
            Log.d(TAG, "cartProductsList.size()=" + cartProductsList.size());
            editor.putInt("listSize", cartProductsList.size());
            editor.commit();
        }
        return cartProductsList;
    }

    /**
     * Delete
     */
    public void deleteCartProduct(CartProducts cartProducts) {
        if (cartProducts == null)
            return; // don't bother
        String id = cartProducts.getProductId();
        cartProductsList.remove(cartProducts);

        editor.remove(id);
        editor.commit();
        //get Object from id and remove that object
    }

    public Map<String, ?> getAll() {
        return settings.getAll();
    }

    public boolean isProductKeyAlreadyExists(String id) {
        return settings.contains(id);
    }

    public void removeAll() {
        editor.clear().commit();
        cartProductsList.clear();
    }

    public int retrieveListSize() {
        return settings.getInt("listSize", 0);
    }
}
