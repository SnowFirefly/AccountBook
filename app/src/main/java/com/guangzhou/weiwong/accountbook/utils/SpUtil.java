package com.guangzhou.weiwong.accountbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tower on 2016/6/18.
 */
public class SpUtil {
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String KEY_USER_PASSWORD = "KEY_USER_PASSWORD";
    public static final String KEY_USER_AGE = "KEY_USER_AGE";
    public static final String KEY_USER_CAREER = "KEY_USER_CAREER";
    public static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    public static final String KEY_GROUP_NAME = "KEY_GROUP_NAME";

    public static void putStringPreference(Context context, String key, String value) {
        getSharedPreference(context).edit().putString(key, value).apply();
    }

    public static String getStringPreference(Context context, String key, String def) {
        return getSharedPreference(context).getString(key, def);
    }

    public static void putBooleanPreference(Context context, String key, boolean value) {
        getSharedPreference(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanPreference(Context context, String key,boolean defValue) {
        return getSharedPreference(context).getBoolean(key, defValue);
    }

    public static void putIntPreference(Context context, String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(key, value).apply();
    }

    public static int getIntPreference(Context context, String key, int def) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, def);
    }

    public static void removeKey(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().remove(key).apply();
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences",
                Context.MODE_PRIVATE);
    }
}
