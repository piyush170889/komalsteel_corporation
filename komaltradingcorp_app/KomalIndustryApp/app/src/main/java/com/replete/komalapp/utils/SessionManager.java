package com.replete.komalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Akshata on 06-Jan-16.
 * Modified On: 06-Jan-16
 * Modified By:Akshata
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "KomalAppLogin";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        //pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void clear() {
        editor.remove(KEY_IS_LOGGED_IN);
        editor.clear();
        editor.commit();
    }

    public static void putPref(String key, String value, Context context) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putPref(String key, int value, Context context) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static int getIntPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void removeKey(String key){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
    public static boolean contains(String key){
        return pref.contains(key);
    }
}

