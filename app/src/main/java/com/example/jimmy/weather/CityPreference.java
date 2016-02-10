package com.example.jimmy.weather;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jimmy on 2/3/2016.
 */
public class CityPreference {
    private static final String PREFS_NAME = "CityPreference";
    public static void storePreference(String city, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(AppConfig.CITY, city);
        editor.commit();
    }

    public static String getPreference(String name, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(name, "");
        return value;
    }

    public static boolean isEmpty(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        if (settings.contains(AppConfig.CITY))
            return false;
        else
            return true;
    }
}
