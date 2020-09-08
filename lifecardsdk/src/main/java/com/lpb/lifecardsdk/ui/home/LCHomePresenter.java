package com.lpb.lifecardsdk.ui.home;

import android.content.Context;


public class LCHomePresenter implements LCHomeContract.Presenter {
    private Context mContext;
    private LCHomeContract.View mViewModel;

    public LCHomePresenter(Context mContext, LCHomeContract.View mViewModel) {
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
