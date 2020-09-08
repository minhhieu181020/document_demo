package com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard;

import android.content.Context;

public class MainMyCardsPresenter implements MainMyCardsContract.Presenter {
    private Context mContext;
    private MainMyCardsContract.View mViewModel;

    MainMyCardsPresenter(Context mContext, MainMyCardsContract.View mViewModel) {
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
