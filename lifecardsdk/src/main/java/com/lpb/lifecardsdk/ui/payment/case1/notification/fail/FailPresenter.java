package com.lpb.lifecardsdk.ui.payment.case1.notification.fail;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.PaymentResultRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentResultResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FailPresenter implements FailContract.Presenter {
    private Context mContext;
    private FailContract.View mViewModel;

    FailPresenter(Context mContext, FailContract.View mViewModel) {
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
    public void getPaymentResult(String mobilePhone, String orderNo, String paymentStatus) {
        final String functionName = Function.FunctionName.GET_PAYMENT_RESULT_FAIL_C1;
        final String functionCode = Function.FunctionCode.GET_PAYMENT_RESULT_FAIL_C1;

        PaymentResultRequest paymentResultRequest = new PaymentResultRequest(mobilePhone, orderNo, paymentStatus);
        String body = StringUtils.convertObjectToBase64(paymentResultRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body,functionName,mContext);

        Repository.getInstance().getPaymentResult(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PaymentResultResponse paymentResultResponse = new Gson().fromJson(text, PaymentResultResponse.class);
                        mViewModel.setPaymentResult(paymentResultResponse);
                        mViewModel.hideLoading();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        mViewModel.setResultDesc();
                        Log.e("checkError", functionName + " ***handleException*** " + e.getMessage());
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.setResultDesc();
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideLoading();
                    mViewModel.setResultDesc();
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideLoading();
                    mViewModel.setResultDesc();
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Log.e("checkError", functionName + " ***handleMessageRequestFailure*** " + t.getMessage());
            }
        });
    }
}
