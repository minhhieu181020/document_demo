package com.lpb.lifecardsdk.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static final String KEY_FILE = "KEY_FILE";

    public static void setString(Context context, String fileName, String content) {
        SharedPreferences pref = context.getSharedPreferences(KEY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(fileName, content);
        edit.apply();
    }

    public static String getString(Context context, String fileName, String defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(KEY_FILE, Context.MODE_PRIVATE);
        if (pref != null) {
            return pref.getString(fileName, defaultValue);
        } else {
            return defaultValue;
        }
    }
}
