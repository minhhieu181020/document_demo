package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.TransactionHistoryRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.TransactionHistoryResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryServicePresenter implements HistoryServiceContract.Presenter {
    private Context mContext;
    private HistoryServiceContract.View mViewModel;

    public HistoryServicePresenter(Context mContext, HistoryServiceContract.View mViewModel) {
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
    public void getDataCard(String mobilePhone, String getCode, String research, final Integer pageIndex, Integer pageSize, String researchStartTime, String researchEndTime) {
        final String functionName = Function.FunctionName.GET_TRANS_HISTORY;
        final String functionCode = Function.FunctionCode.GET_TRANS_HISTORY;

        String body =StringUtils.convertObjectToBase64(new TransactionHistoryRequestDefault(mobilePhone, getCode, research, pageIndex, pageSize, researchStartTime, researchEndTime));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getTransactionHistory(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();

                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        TransactionHistoryResponseDefault model = new Gson().fromJson(text, TransactionHistoryResponseDefault.class);
                        mViewModel.SetView(model.getUnitType());
                        mViewModel.setData(model);
                        mViewModel.showStatus(model.getStatus());
                        if (model.getAccountEntryDtos().size()==0) {
                            mViewModel.setviewnodata(true);
                        }
                        if (pageIndex == 0) {
                            if (model.getUnitType().equalsIgnoreCase("DAY")) {
                                mViewModel.setDataHistoryServiceC1R2(model.getAccountEntryDtos());
                            } else if (model.getUnitType().equalsIgnoreCase("LIMIT")) {
                                mViewModel.setDataHistoryServiceC2R3(model.getAccountEntryDtos());
                            } else if (model.getUnitType().equalsIgnoreCase("TIMES")) {
                                mViewModel.setDataHistoryServiceC3R4(model.getAccountEntryDtos());
                            }
                            mViewModel.hideLoading();
                        } else {
                            if (model.getUnitType().equalsIgnoreCase("DAY")) {
                                mViewModel.addDataHistoryServiceC1R2(model.getAccountEntryDtos());
                            } else if (model.getUnitType().equalsIgnoreCase("LIMIT")) {
                                mViewModel.addDataHistoryServiceC2R3(model.getAccountEntryDtos());
                            } else if (model.getUnitType().equalsIgnoreCase("TIMES")) {
                                mViewModel.addDataHistoryServiceC3R4(model.getAccountEntryDtos());
                            }
                            mViewModel.setviewnodata(false);
                            mViewModel.hideLoading();
                        }
                        Log.e("getTypeunit", "onResponse: " + model.getUnitType());
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e,mContext,functionName,functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideLoading();
                    if (responseBase64.getResultCode().equalsIgnoreCase("00027")) {
                        mViewModel.setviewnodata(true);
                    }
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
                Exception.handleMessageRequestFailure(t,mContext,functionName,functionCode);

            }
        });

    }

    @Override
    public void getCardHistoryServiceC1R2() {

    }

    @Override
    public void getCardHistoryServiceC2R3() {

    }

    @Override
    public void getCardHistoryServiceC3R4() {

    }

    @Override
    public void onClickC1R2(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC1R2) {

    }

    @Override
    public void onClickC2R3(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC2R3) {

    }

    @Override
    public void onClickC3R4(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC3R4) {

    }
}
