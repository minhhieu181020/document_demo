package com.lpb.lifecardsdk.data.source.remote;


import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.constant.RetRestApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static ListService listService;
    private static ListService mapDirections;

    public static ListService getInstance() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization", LCConfig.getAuthorization()).build();
                        return chain.proceed(request);
                    }
                });
        if (listService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetRestApi.API_END_POINT_RET)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            listService = retrofit.create(ListService.class);
        }
        return listService;
    }

    public static ListService getMapDirections() {
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        if (mapDirections == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder)
                    .baseUrl(RetRestApi.API_MAP_DIRECTIONS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mapDirections = retrofit.create(ListService.class);
        }
        return mapDirections;
    }
}
