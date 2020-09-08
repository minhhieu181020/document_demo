package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodepayment;

import android.content.Context;

public class QrCodePaymentPresenter implements QrCodePaymentContract.Presenter {
    private Context mContext;
    private QrCodePaymentContract.View mViewModel;

    public QrCodePaymentPresenter(Context mContext, QrCodePaymentContract.View mViewModel) {
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
    public void getListCardService() {

    }

    @Override
    public void getListCardService(String id) {
        //todo
        // phần này nếu mở comment thì sửa lại theo base req api
//        RequestBase64 requestBase64 = new RequestBase64();
//        final CardDetailRequestDefault detailRequestDefault = new CardDetailRequestDefault();
//        detailRequestDefault.setTransId(id);
//        requestBase64.setBody(StringUtils.convertObjectToBase64(detailRequestDefault));
//        Config.Header.setClientRequestId(String.valueOf(PresenterUtils.getClientRequestId(mContext)));
//        requestBase64.setRestHeader(Config.Header.getHeader());
//        Log.e("cardDetail", "get: " + requestBase64.toString());
//        Repository.getInstance().cardDetail(requestBase64).enqueue(new Callback<ResponseBase64>() {
//            @Override
//            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
//
//                String s = Exception.checkError(response, "cardDetail");
//                if (s.equals(Exception.Type.SUCCESS)) {
//                    ResponseBase64 responseBase64 = response.body();
//                    byte[] data = Base64.decode(responseBase64.getBody(), Base64.DEFAULT);
//                    try {
//                        String text = new String(data, Config.CHARSET_NAME);
//                        CardDetailResponseDefault cardDetailResponseDefault = new Gson().fromJson(text, CardDetailResponseDefault.class);
//                        mViewModel.getData(cardDetailResponseDefault);
//                        mViewModel.setListCardService(cardDetailResponseDefault.getServiceDetailDatas());
//
//                    } catch (UnsupportedEncodingException e) {
//                        mViewModel.hideLoading();
//                        mViewModel.showError(e.toString(), "");
//                    }
//                } else if (s.equals(Exception.Type.KNOWN)) {
//                    ResponseBase64 responseBase64 = response.body();
//                    mViewModel.hideLoading();
//                    mViewModel.showError(responseBase64.getResultDesc(), responseBase64.getResultCode());
//                } else if (s.equals(Exception.Type.UNKNOWN)) {
//                    mViewModel.hideLoading();
//                    mViewModel.showError(mContext.getString(R.string.lifecardmerchant_sever_error), "");
//                } else if (s.equals(Exception.Type.SERVER)) {
//                    mViewModel.hideLoading();
//                    mViewModel.showError(mContext.getString(R.string.lifecardmerchant_sever_error), "");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBase64> call, Throwable t) {
//                Log.e("onFailure1: ", t.getMessage());
//            }
//        });
    }
}
