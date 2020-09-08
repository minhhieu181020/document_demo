package com.lpb.lifecardsdk.ui.home.fragment.mycode.fragment;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.ListMyCardRequestDefault;
import com.lpb.lifecardsdk.data.model.request.default_.MyCardDetailsReqestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCodePresenter implements MyCodeContract.Presenter {
    private Context mContext;
    private MyCodeContract.View mViewModel;

    MyCodePresenter(Context context, MyCodeContract.View viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getDataMyCard(String mobilePhone, String keyFilter) {
        final String functionName = Function.FunctionName.GET_DATA_MY_CARD_2;
        final String functionCode = Function.FunctionCode.GET_DATA_MY_CARD_2;

        String body = StringUtils.convertObjectToBase64(new ListMyCardRequestDefault(mobilePhone, keyFilter, 0, 100, true));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getListMyCard(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        ListCardResponseDefault model = new Gson().fromJson(text, ListCardResponseDefault.class);

//                        mViewModel.setview()
                        if (model.getOwnCardDtos().size()==0) {
                            mViewModel.hideLoading();
                        } else {
                            mViewModel.setDataMyCard(model);
                        }
                        mViewModel.hideLoading();
//                        setView("W");
//                        mViewModel.getDataQr();

//                        Log.e("setview", "onResponse: " + model.getOwnCardDtos().get(0).getStatus());
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
                mViewModel.hideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });

    }
    @Override
    public void getEKYCConfigure() {
        final String functionName = Function.FunctionName.GET_EKYC_CONFIGURE;
        final String functionCode = Function.FunctionCode.GET_EKYC_CONFIGURE;

        RequestBase64 requestBase64 = ReqApiUtils.createRequest("eyAiMSI6ICIiIH0=", functionName, mContext);

        Repository.getInstance().getEKYCConfigure(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        EKYCConfigureResponse ekycConfigureResponse = new Gson().fromJson(text, EKYCConfigureResponse.class);
                        mViewModel.setEKYCConfigure(ekycConfigureResponse);
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        mViewModel.setDefaultEKYCConfigure();
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.setDefaultEKYCConfigure();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.setDefaultEKYCConfigure();
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.setDefaultEKYCConfigure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.setDefaultEKYCConfigure();
            }
        });
    }
    @Override
    public void getCardMyCode(String mobilePhone, String cardNo) {
        final String functionName = Function.FunctionName.GET_DATA_MY_CODE;
        final String functionCode = Function.FunctionCode.GET_DATA_MY_CODE;

        String body = StringUtils.convertObjectToBase64(new MyCardDetailsReqestDefault(mobilePhone, cardNo, true));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getMyCardDetail(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {

                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        final MyCardDetailsWaitResponseDefault model = new Gson().fromJson(text, MyCardDetailsWaitResponseDefault.class);

                        if (model == null) {
                            mViewModel.hideLoading();
                        } else {
                            mViewModel.setDataCardMyCode(model);
                            mViewModel.hideLoading();
                        }
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                    mViewModel.hideLoading();
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                    mViewModel.setDataCardMyCode(null);
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
                mViewModel.hideLoading();

            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }


}
