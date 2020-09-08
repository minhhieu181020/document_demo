package com.lpb.lifecardsdk.exception;


import android.content.Context;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomToast;

import java.net.SocketTimeoutException;

import retrofit2.Response;

public class Exception {

    private static final String TAG = "checkError: ";

    private static final boolean showResultCode = true;
    public static boolean isShowToast = true;

    private static final int TOAST_INTERVAL = 4000;
    private static long mLastToastTime;
    private static final String RESULT_CODE_SUCCESS = "0";

    public static void handleMessageRequestFailure(Throwable throwable, Context context, String functionName, String functionCode) {

        Log.e(TAG, functionName + "(" + functionCode + ") " + " ***handleMessageRequestFailure*** " + throwable.getMessage());
        if (isShowToast && isIsShowToast())
            if (throwable instanceof SocketTimeoutException) {
                CustomToast.makeText(context, context.getString(R.string.lifecardsdk_time_out) + (showResultCode && !StringUtils.isEmpty(functionCode) ? " (" + functionCode + ")" : ""), TOAST_INTERVAL, CustomToast.ERROR);
            } else {
                CustomToast.makeText(context, context.getString(R.string.lifecardsdk_sever_error) + (showResultCode && !StringUtils.isEmpty(functionCode) ? " (" + functionCode + ")" : ""), TOAST_INTERVAL, CustomToast.ERROR);
            }
    }

    public static void handleException(java.lang.Exception exception, Context context, String functionName, String functionCode) {
        Log.e(TAG, functionName + "(" + functionCode + ") " + " ***handleException*** " + exception.getMessage());
        exception.printStackTrace();
        if (isShowToast && isIsShowToast())
            CustomToast.makeText(context, context.getString(R.string.lifecardsdk_realtime_error) + (showResultCode && !StringUtils.isEmpty(functionCode) ? " (" + functionCode + ")" : ""), TOAST_INTERVAL, CustomToast.ERROR);
    }

    public static void handleMessageRequestFailure(Context context, String message, String functionCode) {
        if (isShowToast && isIsShowToast())
            CustomToast.makeText(context, message + (showResultCode && !StringUtils.isEmpty(functionCode) ? " (" + functionCode + ")" : ""), TOAST_INTERVAL, CustomToast.ERROR);
    }

    public static void handleMessageSuccess(Context context, String message) {
        if (isShowToast && isIsShowToast())
            CustomToast.makeText(context, message, TOAST_INTERVAL, CustomToast.SUCCESS);
    }

    private static boolean isIsShowToast() {
        long currentToastTime = SystemClock.uptimeMillis();
        long elapsedTime = currentToastTime - mLastToastTime;
        if (elapsedTime >= TOAST_INTERVAL) {
            mLastToastTime = currentToastTime;
            return true;
        }
        return false;
    }

    public static String checkError(Response<ResponseBase64> response, String functionName, String functionCode) {
        String status;
        if (response.body() != null && response.isSuccessful()) {
            ResponseBase64 responseBase64 = response.body();
            Log.d("responseBase64: ", functionName + "(" + functionCode + ")" + " *** " + responseBase64.toString());
            if (responseBase64.getResultCode() != null) {
                if (responseBase64.getResultCode().equals(RESULT_CODE_SUCCESS)) {
                    status = Type.SUCCESS;
                    Log.d("responseBodyEncode: ", functionName + "(" + functionCode + ")" + " *** " + responseBase64.getBody());
                    try {
                        byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                        String requestBody = new String(data, Config.CHARSET_NAME);
                        Log.d("responseBodyDecode: ", functionName + "(" + functionCode + ")" + " *** " + requestBody);
                        Log.d("response: ", functionName + "(" + functionCode + ")" + "----------------------------------\n");
                    } catch (java.lang.Exception ignored) {
                    }
                } else {
                    status = Type.KNOWN;
                    Log.e(TAG, functionName + "(" + functionCode + ")" + " ***error*** " + status);
                }
            } else {
                status = Type.UNKNOWN;
                Log.e(TAG, functionName + "(" + functionCode + ")" + " ***error*** " + status);
            }
        } else {
            status = Type.SERVER;
            Log.e(TAG, functionName + "(" + functionCode + ")" + " ***error*** " + status);
        }
        return status;
    }

    public static class Type {
        public static String SUCCESS = "success";
        public static String KNOWN = "known";
        public static String UNKNOWN = "unknown";
        public static String SERVER = "sever";
    }

}
