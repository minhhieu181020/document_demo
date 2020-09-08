package com.lpb.lifecardsdk.ui.paymentguide.fragment.transfer;

import android.content.Context;

public class TransferPresenter implements TransferContract.Presenter {
    private Context mContext;
    private TransferContract.View mViewModel;

     TransferPresenter(Context mContext, TransferContract.View mViewModel) {
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
