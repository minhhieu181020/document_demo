package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.white;

import android.content.Context;


public class WhitePresenter implements WhiteContract.Presenter {
    private Context mContext;
    private WhiteContract.View mViewModel;

    WhitePresenter(Context mContext, WhiteContract.View mViewModel) {
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