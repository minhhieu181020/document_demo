package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.infopakage;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.CardPackageServiceDetailRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.CardPackageServiceDetailResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPakagePresenter implements InfoPakageContract.Presenter {
    private Context mContext;
    private InfoPakageContract.View mViewModel;

    public InfoPakagePresenter(Context mContext, InfoPakageContract.View mViewModel) {
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
    public void getDataInforCard(String mobilePhone, String code) {
        final String functionName = Function.FunctionName.PACKAGE_SERVICE_DETAILS;
        final String functionCode = Function.FunctionCode.PACKAGE_SERVICE_DETAILS;

        String body = StringUtils.convertObjectToBase64(new CardPackageServiceDetailRequestDefault(mobilePhone, code));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getCardPackgeServiceDetail(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        CardPackageServiceDetailResponseDefault model = new Gson().fromJson(text, CardPackageServiceDetailResponseDefault.class);
                        mViewModel.setData(model);
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
            }


            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });

    }


}
