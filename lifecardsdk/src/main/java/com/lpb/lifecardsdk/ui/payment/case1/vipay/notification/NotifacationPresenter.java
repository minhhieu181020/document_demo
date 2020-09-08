package com.lpb.lifecardsdk.ui.payment.case1.vipay.notification;

import android.content.Context;

public class NotifacationPresenter implements NotifacationContract.Presenter {
    private Context mContext;
    private NotifacationContract.View mViewModel;

    public NotifacationPresenter(Context mContext, NotifacationContract.View mViewModel) {
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