package com.lpb.lifecardsdk.ui.home.fragment.mycodev2;

import android.content.Context;

public class MainMyCodeV2Presenter implements MainMyCodeV2Contract.Presenter {
    private Context mContext;
    private MainMyCodeV2Contract.View mViewModel;

    MainMyCodeV2Presenter(Context mContext, MainMyCodeV2Contract.View mViewModel) {
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
