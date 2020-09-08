package com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search_result;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

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
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultPresenter implements SearchResultContract.Presenter {
    private Context mContext;
    private SearchResultContract.View mViewModel;
    private int status;

    SearchResultPresenter(Context mContext, SearchResultContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    private void showContent() {
        if (status == 2) {
            mViewModel.hideLoading();
            mViewModel.showContent(true);
        }
    }

    @Override
    public void getDataCategory(String providerID) {
        final String functionName = Function.FunctionName.GET_DATA_CATEGORY_SEARCH;
        final String functionCode = Function.FunctionCode.GET_DATA_CATEGORY_SEARCH;

        String body = StringUtils.convertObjectToBase64(new CategoryRequest(providerID, Constants.CategoryType.PKG));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);


        Repository.getInstance().getCategory(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
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

    @Override
    public void getDataCard(Integer categoryID, String keywordQuery, String sort, String mobilePhone, final int pageIndex, int pageSize, Integer providerID, final int searchType) {
        final String functionName = Function.FunctionName.GET_DATA_CARD_SEARCH;
        final String functionCode = Function.FunctionCode.GET_DATA_CARD_SEARCH;

        final PackageSearchRequest packageSearchRequest = new PackageSearchRequest(categoryID, keywordQuery, sort, mobilePhone, pageIndex, pageSize, providerID);
        String body = StringUtils.convertObjectToBase64(packageSearchRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body,functionName,mContext);

        Repository.getInstance().getPackage(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName,functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PackageSearchResponse packageSearchResponse = new Gson().fromJson(text, PackageSearchResponse.class);
                        if (pageIndex == 0) {
                            mViewModel.setDataCard(packageSearchResponse.getDefCardDtos());
                        } else {
                            mViewModel.addDataCard(packageSearchResponse.getDefCardDtos());
                        }
                        if (searchType == Constants.QueryType.FIRST) {
                            if (packageSearchResponse.getDefCardDtos().size() != 0) {
                                status++;
                                showContent();
                            } else {
                                mViewModel.hideLoading();
                                mViewModel.showContent(false);
                            }
                        } else if (searchType == Constants.QueryType.NEXT) {
                            if (packageSearchResponse.getDefCardDtos().size() == 0)
                                mViewModel.showContent(false);
                            else mViewModel.showContent(true);
                            mViewModel.hideLoading();
                        } else if (searchType == Constants.QueryType.WITH_OPTION) {
                            mViewModel.showContent(true);
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
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                mViewModel.hideLoading();
                Exception.handleMessageRequestFailure(t, mContext, functionName,functionCode);
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
