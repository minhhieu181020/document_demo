package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.fragment.direct;

import android.content.Context;

public class DirectPresenter implements DirectContract.Presenter {
    private Context mContext;
    private DirectContract.View mViewModel;

    DirectPresenter(Context mContext, DirectContract.View mViewModel) {
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
