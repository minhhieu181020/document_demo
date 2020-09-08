package com.lpb.lifecardsdk.ui.provider.fragment.cardlist;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.CategoryRequest;
import com.lpb.lifecardsdk.data.model.request.default_.PackageSearchRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCardSearchResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardListPresenter implements CardListContract.Presenter {
    private Context mContext;
    private CardListContract.View mViewModel;
    private int status;

    CardListPresenter(Context mContext, CardListContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void showContent() {
        if (status == 1) {
            //todo
            //  status ==2 khi lay category
            mViewModel.hideLoading();
            mViewModel.showContent();
        }
    }

    @Override
    public void getDataCategory(String providerID) {
        final String functionName = Function.FunctionName.GET_DATA_CATEGORY_PROVIDER;
        final String functionCode = Function.FunctionCode.GET_DATA_CATEGORY_PROVIDER;

        String body = StringUtils.convertObjectToBase64(new CategoryRequest(providerID, Constants.CategoryType.PKG));
        RequestBase64  requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getCategory(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        CategoryResponse categoryResponse = new Gson().fromJson(text, CategoryResponse.class);
                        mViewModel.setDataCategory(categoryResponse.getLstCate());
                        status++;
                        showContent();
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName,functionCode);
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
                Exception.handleMessageRequestFailure(t, mContext, functionName,functionCode);
            }
        });
    }

    @Override
    public void getDataCard(Integer categoryID, String keywordQuery, String sort, String mobilePhone, final int pageIndex, int pageSize, int providerID, final int type) {
        final String functionName = Function.FunctionName.GET_DATA_CARD_PROVIDER;
        final String functionCode = Function.FunctionCode.GET_DATA_CARD_PROVIDER;


        PackageSearchRequest packageSearchRequest = new PackageSearchRequest(categoryID, keywordQuery, sort, mobilePhone, pageIndex, pageSize, providerID);
        String body = StringUtils.convertObjectToBase64(packageSearchRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body,functionName,mContext);

        Repository.getInstance().getDefCard(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        DefCardSearchResponse defCardSearchResponse = new Gson().fromJson(text, DefCardSearchResponse.class);
                        if (pageIndex == 1) {
                            mViewModel.setDataCard(defCardSearchResponse.getCardListConfig());
                        } else {
                            mViewModel.addDataCard(defCardSearchResponse.getCardListConfig());
                        }
                        if (type == Constants.QueryType.FIRST) {
                            status++;
                            showContent();
                        } else if (type == Constants.QueryType.WITH_OPTION) {
                            mViewModel.showContent();
                            mViewModel.hideLoading();
                        }
                    } catch (java.lang.Exception e) {
                        mViewModel.hideLoading();
                        Exception.handleException(e, mContext, functionName,functionCode);
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
                mViewModel.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                mViewModel.setRefreshing(false);
                Exception.handleMessageRequestFailure(t, mContext, functionName,functionCode);
            }
        });
    }
}
