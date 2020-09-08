package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.ListMyCardRequestDefault;
import com.lpb.lifecardsdk.data.model.request.default_.PackageSearchRequest;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.util.ReqApiUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCardPresenter implements MyCardContract.Presenter {
    private Context mContext;
    private MyCardContract.View mViewModel;
    private int status;

    MyCardPresenter(Context context, MyCardContract.View viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private void showContent() {
        if (status == 4) {
            mViewModel.hideLoading();
            mViewModel.showContent();
        }
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
    public void getDataCardWaitForPayMent(Integer categoryID, String keywordQuery, String sort, String mobilePhone, final int pageIndex, int pageSize, final boolean isFirst) {
        final String functionName =  Function.FunctionName.GET_DATA_CARD_WAIT_PAYMENT;
        final String functionCode = Function.FunctionCode.GET_DATA_CARD_WAIT_PAYMENT;


        PackageSearchRequest packageSearchRequest = new PackageSearchRequest(categoryID, keywordQuery, sort, mobilePhone, pageIndex, pageSize, null);
        String body = StringUtils.convertObjectToBase64(packageSearchRequest);
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body,functionName,mContext);

        Repository.getInstance().getPackage(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {

                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, Config.CHARSET_NAME);
                        PackageSearchResponse packageSearchResponse = new Gson().fromJson(text, PackageSearchResponse.class);
                        if (pageIndex == 0) {
                            mViewModel.setDataCardWaitForPayMent(packageSearchResponse.getDefCardDtos());
                        } else {
                            mViewModel.addDataCardWaitForPayMent(packageSearchResponse.getDefCardDtos());
                        }
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
    public void onClickCardItem(ListCardResponseDefault.OwnCardDto card) {

    }

    @Override
    public void getDataMyCard(final String mobilePhone, final String keyFilter, final int pageIndex, int pageSize, boolean b) {
        final String functionName = Function.FunctionName.GET_LIST_MY_CARD;
        final String functionCode = Function.FunctionCode.GET_LIST_MY_CARD;

        String body = StringUtils.convertObjectToBase64(new ListMyCardRequestDefault(mobilePhone, keyFilter, pageIndex, pageSize, false));
        RequestBase64 requestBase64 = ReqApiUtils.createRequest(body, functionName, mContext);

        Repository.getInstance().getListMyCard(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                String s = Exception.checkError(response, functionName, functionCode);
                if (s.equals(Exception.Type.SUCCESS)) {
                    ResponseBase64 responseBase64 = response.body();
                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        ListCardResponseDefault model = new Gson().fromJson(text, ListCardResponseDefault.class);
                        //
                        if (pageIndex == 0) {
                            mViewModel.setDataMyCard(model);
                            int a = 0;

                            for (int i = 0; i < model.getOwnCardDtos().size(); i++) {
                                if (model.getOwnCardDtos().get(i).getStatus().equalsIgnoreCase("A") || model.getOwnCardDtos().get(i).getStatus().equalsIgnoreCase("O")) {
//                                mViewModel.setView("Còn hiệu lực");
//                                Log.e("Setview", "Setview: Không còn thẻ có hiệu lực");
                                    a = 1;
                                } else {

                                }

                            }
//                        a = 1 trang thai con hieu luc a= 0 la trang thai hiet hieu luc
                            if (a == 1) {
//                            Có thẻ còn hiệu lực
                                mViewModel.setView("ListAOECW");
                            } else {
//                            Không có thẻ còn hiệu lực
                                mViewModel.setView("ListNoA");
                            }

//                        Log.e("okokok", "onResponse: " + a);
//                        Log.e("okokok", "onResponse: " + e);

                            if (model.getOwnCardDtos().size() == 0) {
                                mViewModel.setView("NoListAOECW");
                                Log.e("Setview", "Setview: Không còn thẻ có Danh sach thẻ");
//                                getDataCardWaitForPayMent(1, "", Constants.SortType.MN, LCConfig.getPhoneNumber(), 1, 5, true);
                                mViewModel.hideLoading();
                            }

                        } else {
                            if (model.getOwnCardDtos() == null) {
                                Log.e("getDataMyCard: ", "null");
                            } else {
                                mViewModel.addDataMyCard(model.getOwnCardDtos());
//                                Log.e("getDataMyCard: ", "" + model.getOwnCardDtos().size());
                            }

                        }

//                        mViewModel.setDataMyCard(model.getOwnCardDtos());
//                      check danh sách thẻ còn hiệu lực , hết hiệu lực

//                        Log.e("setview", "onResponse: " + model.getOwnCardDtos().get(0).getStatus());
//                        Toast.make9Text(mContext, model.getOwnCardDtos().get(0).getCustomerName(), Toast.LENGTH_SHORT).show();
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
    public void onClickCardWaitItem(CardWaitForPayMent dataCard) {

    }

    public void setView(String status) {
        mViewModel.setView(status);
    }
}
