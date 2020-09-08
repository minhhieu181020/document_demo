package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.qrpersonal;

import android.content.Context;

public class QrPersonalPresenter implements QrPersonalContract.Presenter {

    private Context mContext;
    private QrPersonalContract.ViewModel mViewModel;

    public QrPersonalPresenter(Context mContext, QrPersonalContract.ViewModel mViewModel) {
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
