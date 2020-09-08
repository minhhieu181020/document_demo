package com.lpb.lifecardsdk.util;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

public class CommonUtils {

//    public static boolean isConnectingToInternet() {
//        ConnectivityManager connectivity = (ConnectivityManager) MFPApplication.self()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null || connectivity.getAllNetworkInfo() == null) return false;
//        NetworkInfo[] info = connectivity.getAllNetworkInfo();
//        for (NetworkInfo anInfo : info) {
//            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static Locale getLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public static String getTag(Class cls) {
        String className = cls.getSimpleName();

        if (className.length() > 23)
            return className.substring(0, 23);
        return className;
    }

    public static boolean isNumber(char c) {
        try {
            short ret = Short.parseShort(String.valueOf(c));
            return ret >= 0;
        } catch (Exception e) {
            Logger.e(CommonUtils.class, e);
            return false;
        }
    }
}
