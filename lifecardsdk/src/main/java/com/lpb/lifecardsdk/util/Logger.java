package com.lpb.lifecardsdk.util;

import android.util.Log;

import com.lpb.lifecardsdk.BuildConfig;


public class Logger {

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void e(String tag, Exception e) {
        Log.e(tag, e.getMessage());
    }

    public static void e(String tag, Throwable e) {
        Log.e(tag, e.getMessage());
    }

    public static void e(Class clazz, Exception e) {

        Log.e(CommonUtils.getTag(clazz), e.getMessage());

        if (BuildConfig.DEBUG) {
            for (StackTraceElement s : e.getStackTrace())
                System.out.println(s);
        }
    }

    public static void e(Class clazz, Throwable e) {

        Log.e(CommonUtils.getTag(clazz), e.getMessage());

        if (BuildConfig.DEBUG) {
            for (StackTraceElement s : e.getStackTrace())
                System.out.println(s);
        }
    }

    public static void e(Class clazz, String message) {
        Log.e(CommonUtils.getTag(clazz), message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void d(Class clazz, String message) {
        Log.d(CommonUtils.getTag(clazz), message);
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void i(Class clazz, String message) {
        Log.d(CommonUtils.getTag(clazz), message);
    }
}
