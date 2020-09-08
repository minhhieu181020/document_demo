package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide;

import android.content.Context;
import android.util.Base64;
import android.view.View;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.PaymentGuideRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse2;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentMethodResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGuidePresenter implements PaymentGuideContract.Presenter {
    private Context mContext;
    private PaymentGuideContract.View mViewModel;

    public PaymentGuidePresenter(Context mContext, PaymentGuideContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void callAPIMethod() {
        final String functionName = Function.FunctionName.GET_PAYMENT_METHOD;
        final String functionCode = Function.FunctionCode.GET_PAYMENT_METHOD;
        // body không cần truyền đúng
        final String body = "eyAiMSI6ICIiIH0=";
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().get_Payment_Method(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentMethodResponse paymentMethodResponse = new Gson().fromJson(text, PaymentMethodResponse.class);
                        if (paymentMethodResponse.getData() == null || paymentMethodResponse.getData().size() == 0) {
                            Exception.handleMessageRequestFailure(mContext, mContext.getString(R.string.lifecardsdk_cant_fint_payment_method), functionCode);
                            return;
                        }
                        mViewModel.setPaymentMethodResponse(paymentMethodResponse);
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    Exception.handleMessageRequestFailure(mContext, responseBase64.getResultDesc(), functionCode);
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    Exception.handleMessageRequestFailure(mContext, mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    Exception.handleMessageRequestFailure(mContext, mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }

    @Override
    public void getPaymentGuide2(String transID) {
        final String functionName = Function.FunctionName.GET_DATA_PAYMENT_GUIDE;
        final String functionCode = Function.FunctionCode.GET_DATA_PAYMENT_GUIDE;

        String body = StringUtils.convertObjectToBase64(new PaymentGuideRequest(transID));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getPaymentGuide(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentGuideResponse2 guideResponse = new Gson().fromJson(text, PaymentGuideResponse2.class);
                        mViewModel.setPaymentGuide2(guideResponse);
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }
}
