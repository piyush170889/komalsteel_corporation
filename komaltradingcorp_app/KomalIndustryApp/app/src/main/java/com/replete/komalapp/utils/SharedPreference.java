package com.replete.komalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.replete.komalapp.rowitem.CartProducts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<CartProducts> favorites) {
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public void addFavorite(Context context, CartProducts product) {
        List<CartProducts> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<CartProducts>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, CartProducts product) {
        ArrayList<CartProducts> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            Log.d("SharedPreferance", favorites.toString());
            saveFavorites(context, favorites);
        }
    }

    public int getListSize(Context context) {
        ArrayList<CartProducts> favorites = getFavorites(context);
        int size = 0;
        if (favorites != null) {
            size = favorites.size();
        }
        return size;
    }


    public ArrayList<CartProducts> getFavorites(Context context) {
        SharedPreferences settings;
        List<CartProducts> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            CartProducts[] favoriteItems = gson.fromJson(jsonFavorites,
                    CartProducts[].class);

            favorites = Arrays.asList(favoriteItems);
        } else
            return null;

        return (ArrayList<CartProducts>) favorites;
    }

    public boolean isContains(Context context, CartProducts cartProducts) {
        ArrayList<CartProducts> favorites = getFavorites(context);
        boolean isContained = false;
        if (favorites != null) {
            isContained=favorites.contains(cartProducts);
        }
        return isContained;
    }
}
