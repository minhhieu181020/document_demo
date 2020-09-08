package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode;

import android.content.Context;

public class MainScanQrCodePresenter implements MainScanQrCodeContract.Presenter {
    private Context mContext;
    private MainScanQrCodeContract.View mViewModel;

    MainScanQrCodePresenter(Context mContext, MainScanQrCodeContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
