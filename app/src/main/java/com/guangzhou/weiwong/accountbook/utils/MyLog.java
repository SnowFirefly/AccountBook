package com.guangzhou.weiwong.accountbook.utils;

import android.util.Log;

/**
 * Created by Tower on 2016/6/2.
 */
public class MyLog {
    public static boolean isDebuggable = true;
    public static boolean isSimpleName = true;
    public static final String exTag = "wht(MyLog): ";

    public static void i(String tag, String msg) {
        if (isDebuggable) {
            Log.i(exTag + tag, msg);
        }
    }

    public static void i(Object object, String msg) {
        if (isDebuggable) {
            if (isSimpleName) {
                Log.i(exTag + object.getClass().getSimpleName(), msg);
            } else {
                Log.i(exTag + object.getClass().getName(), msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (isDebuggable) {
            Log.d(exTag + tag, msg);
        }
    }

    public static void d(Object object, String msg) {
        if (isDebuggable) {
            if (isSimpleName) {
                Log.d(exTag + object.getClass().getSimpleName(), msg);
            } else {
                Log.d(exTag + object.getClass().getName(), msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (isDebuggable) {
            Log.e(exTag + tag, msg);
        }
    }

    public static void e(Object object, String msg) {
        if (isDebuggable) {
            if (isSimpleName) {
                Log.e(exTag + object.getClass().getSimpleName(), msg);
            } else {
                Log.e(exTag + object.getClass().getName(), msg);
            }
        }
    }
}
