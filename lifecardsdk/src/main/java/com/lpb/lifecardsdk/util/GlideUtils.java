package com.lpb.lifecardsdk.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;

public class GlideUtils {
    private static SharedPreferences spGlideCache;
    private static SharedPreferences.Editor editGlideCache;
    private Context context;
    public static void setSpGlideCache(SharedPreferences spGlideCache) {
        GlideUtils.spGlideCache = spGlideCache;
    }

    public static void setEditGlideCache(SharedPreferences.Editor editGlideCache) {
        GlideUtils.editGlideCache = editGlideCache;
    }

    @SuppressLint("CheckResult")
    public static void loadImageUrl(ImageView imageView, String url, Context context, String type) {
//        long lastTime = spGlideCache.getLong(Constants.SharePref.TIME_SAVE_CACHE + url, 0L);
        long lastTime = spGlideCache.getLong(Constants.SharePref.TIME_SAVE_CACHE, 0L);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        RequestOptions requestOptions = new RequestOptions();
        switch (type) {
            case Constants.PlaceHolderType.BACKGROUND_CARD:
            case Constants.PlaceHolderType.BACKGROUND_PROVIDER:
                requestOptions.placeholder(R.mipmap.lifecardsdk_ic_default_card);
                requestOptions.error(R.mipmap.lifecardsdk_ic_default_card);
                break;
            case Constants.PlaceHolderType.LOGO_CATEGORY:
            case Constants.PlaceHolderType.LOGO_PROMOTION:
                requestOptions.placeholder(R.mipmap.lifecardsdk_ic_default_category);
                requestOptions.error(R.mipmap.lifecardsdk_ic_default_category);
                break;
            case Constants.PlaceHolderType.LOGO_PROVIDER:
                requestOptions.placeholder(R.mipmap.lifecardsdk_ic_default_provider);
                requestOptions.error(R.mipmap.lifecardsdk_ic_default_provider);
                break;
        }

//        if (lastTime == 0L) {
//            Glide.with(context)
//                    .load(url)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .apply(requestOptions)
//                    .into(imageView);
//            editGlideCache.putLong(Constants.SharePref.TIME_SAVE_CACHE + url, System.currentTimeMillis());
//            editGlideCache.commit();
//        } else {
//            Glide.with(context)
//                    .load(url)
//                    .apply(requestOptions)
//                    .into(imageView);
//        }
        if (lastTime == 0) {
            editGlideCache.putLong(Constants.SharePref.TIME_SAVE_CACHE, System.currentTimeMillis());
            editGlideCache.commit();
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
            return;
        }
        if (DateUtils.compareCurrentDate(lastTime, 1)) {
            ClearCacheAsyncTask clearCacheAsyncTask = new ClearCacheAsyncTask(context, url, imageView, requestOptions);
            clearCacheAsyncTask.execute(context);
        } else {
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }


    }

    public static class ClearCacheAsyncTask extends AsyncTask<Context, Void, Boolean> {
        private Context context;
        private String url;
        private ImageView imageView;
        private RequestOptions requestOptions;

        ClearCacheAsyncTask(Context context, String url, ImageView imageView, RequestOptions requestOptions) {
            this.context = context;
            this.url = url;
            this.imageView = imageView;
            this.requestOptions = requestOptions;
        }

        @Override
        protected Boolean doInBackground(Context... params) {
            // process in background
            Glide.get(params[0]).clearDiskCache();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (isSuccess) {
                Glide.with(context)
                        .load(url)
                        .apply(requestOptions)
                        .into(imageView);

                editGlideCache.putLong(Constants.SharePref.TIME_SAVE_CACHE, System.currentTimeMillis());
                editGlideCache.commit();
            }
        }
    }


}
