package com.lpb.lifecardsdk.ui.home.fragment.mycode;

import android.content.Context;

public class MainMyCodePresenter implements MainMyCodeContract.Presenter {
    private Context mContext;
    private MainMyCodeContract.View mViewModel;

    MainMyCodePresenter(Context mContext, MainMyCodeContract.View mViewModel) {
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
