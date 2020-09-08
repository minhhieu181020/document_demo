package com.lpb.lifecardsdk.ui.home.fragment.buycard;

import android.content.Context;

public class MainBuyCardPresenter implements MainBuyCardContract.Presenter {
    private Context mContext;
    private MainBuyCardContract.View mViewModel;

    MainBuyCardPresenter(Context mContext, MainBuyCardContract.View mViewModel) {
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
