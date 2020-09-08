package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentreceivegifts;

import android.content.Context;

public class ReceiveGiftsPresenter implements ReceiveGiftsContract.Presenter {
    private Context mContext;
    private ReceiveGiftsContract.View mViewModel;

    public ReceiveGiftsPresenter(Context mContext, ReceiveGiftsContract.View mViewModel) {
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
