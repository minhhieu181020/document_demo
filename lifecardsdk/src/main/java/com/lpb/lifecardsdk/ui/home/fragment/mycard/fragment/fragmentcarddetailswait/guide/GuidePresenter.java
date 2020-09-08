package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.BranchRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.response.default_.GuideResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuidePresenter implements GuideContract.Presenter {
    private Context mContext;
    private GuideContract.View mViewModel;

    GuidePresenter(Context mContext, GuideContract.View mViewModel) {
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
    public void getDataGuide(String body) {
        final String functionName = Function.FunctionName.GET_DATA_GUIDE;
        final String functionCode = Function.FunctionCode.GET_DATA_GUIDE;

        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getDataGuide(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        GuideResponse guideResponse = new Gson().fromJson(text, GuideResponse.class);
                        mViewModel.setDataGuide(guideResponse);
                      mViewModel.hideProgressBar(Constants.PROVIDER);
                    } catch (java.lang.Exception e) {
                        mViewModel.hideProgressBar(Constants.PROVIDER);
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (s.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideProgressBar(Constants.PROVIDER);
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (s.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideProgressBar(Constants.PROVIDER);
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (s.equals(Exception.Type.SERVER)) {
                    mViewModel.hideProgressBar(Constants.PROVIDER);
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }

            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideProgressBar(Constants.PROVIDER);
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }


    @Override
    public void getDataBranch(String areaCode, String areaType, String providerId, final int pageIndex, int rowCount) {
        final String functionName = Function.FunctionName.GET_DATA_BRANCH;
        final String functionCode = Function.FunctionCode.GET_DATA_BRANCH;

        BranchRequest branchRequest = new BranchRequest(areaCode, areaType, providerId, pageIndex, rowCount, true);
        String body = StringUtils.convertObjectToBase64(branchRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getBranch(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String status = Exception.checkError(response, functionName, functionCode);
                if (status.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        BranchResponse branchResponse = new Gson().fromJson(text, BranchResponse.class);
                        mViewModel.setDataBranch(branchResponse.getList());
                        mViewModel.hideProgressBar(Constants.BRANCH);
                    } catch (java.lang.Exception e) {
                        mViewModel.hideProgressBar(Constants.BRANCH);
                        Exception.handleException(e, mContext, functionName, functionCode);
                    }
                } else if (status.equals(Exception.Type.KNOWN)) {
                    ResponseBase64 responseBase64 = response.body();
                    mViewModel.hideProgressBar(Constants.BRANCH);
                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
                } else if (status.equals(Exception.Type.UNKNOWN)) {
                    mViewModel.hideProgressBar(Constants.BRANCH);
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_unknown_error), functionCode);
                } else if (status.equals(Exception.Type.SERVER)) {
                    mViewModel.hideProgressBar(Constants.BRANCH);
                    mViewModel.showError(mContext.getString(R.string.lifecardsdk_sever_error), functionCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideProgressBar(Constants.BRANCH);
                Exception.handleMessageRequestFailure(t, mContext, functionName, functionCode);
            }
        });
    }


}
