package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage;

import android.content.Context;

public class CardDetailPackagePresenter implements CardDetailPackageContract.Presenter {
    private Context mContext;
    private CardDetailPackageContract.View mViewModel;

    public CardDetailPackagePresenter(Context mContext, CardDetailPackageContract.View mViewModel) {
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
