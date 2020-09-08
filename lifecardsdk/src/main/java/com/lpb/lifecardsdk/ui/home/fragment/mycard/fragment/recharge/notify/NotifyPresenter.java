package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.notify;

import android.content.Context;

/**
 * Created by vannh.lvt on 10/08/2020
 */
public class NotifyPresenter implements NotifyContract.Presenter {
    private Context mContext;
    private NotifyContract.View mViewModel;

    NotifyPresenter(Context mContext, NotifyContract.View mViewModel) {
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
