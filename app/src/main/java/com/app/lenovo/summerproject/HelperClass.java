package com.app.lenovo.summerproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class HelperClass
{

    public static void putSharedPreferencesString(Context context, String key, String val){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=preferences.edit();
        edit.putString(key, val);
        edit.commit();
    }
    public static void putSharedPreferencesBoolean(Context context, String key, boolean val){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit=preferences.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }
    public static String getSharedPreferencesString(Context context, String key, String _default){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, _default);
    }
    public static boolean getSharedPreferencesBoolean(Context context, String key, boolean _default){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, _default);
    }
}