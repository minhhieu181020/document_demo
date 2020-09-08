package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.card_information;

import android.content.Context;

public class CardInforPresenter implements CardInforContract.Presenter {
    private Context mContext;
    private CardInforContract.View mViewModel;

    CardInforPresenter(Context mContext, CardInforContract.View mViewModel) {
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
