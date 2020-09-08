package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search_result;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.ListMyCardRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMyCardResultPresenter implements SearchMyCardResultContract.Presenter {
    private Context mContext;
    private SearchMyCardResultContract.View mViewModel;

    public SearchMyCardResultPresenter(Context mContext, SearchMyCardResultContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void getDataMyCard(String mobilePhone, String keyFilter) {
        final String functionName = Function.FunctionName.GET_DATA_MY_CARD;
        final String functionCode =  Function.FunctionCode.GET_DATA_MY_CARD;

        RequestBase64 requestAfter = new RequestBase64();
//        requestAfter.setBody(StringUtils.convertObjectToBase64(new ListMyCardRequestDefault(LCConfig.getPhoneNumber(), "")));
        requestAfter.setBody(StringUtils.convertObjectToBase64(new ListMyCardRequestDefault(mobilePhone, keyFilter,0,5,false)));
        requestAfter.setRestHeader(Config.Header.getHeader());

        Repository.getInstance().getListMyCard(requestAfter).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                if (response.body() != null && response.isSuccessful()) {
                    byte[] data = Base64.decode(response.body().getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        ListCardResponseDefault model = new Gson().fromJson(text, ListCardResponseDefault.class);
                        //

                        if (model.getOwnCardDtos() != null) {
                            mViewModel.showContent(true);
                            mViewModel.setDataMyCard(model.getOwnCardDtos());

                        } else {
                            mViewModel.showContent(false);
                        }
//                        setView("W");
//                        Log.e("setview", "onResponse: " + model.getOwnCardDtos().get(0).getStatus());
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e,mContext,functionName,functionCode);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Exception.handleMessageRequestFailure(t,mContext,functionName,functionCode);
                mViewModel.hideLoading();
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
