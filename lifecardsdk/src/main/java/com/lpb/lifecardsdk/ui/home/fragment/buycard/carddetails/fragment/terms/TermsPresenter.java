package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.terms;

import android.content.Context;

public class TermsPresenter implements TermsContract.Presenter {
    private Context mContext;
    private TermsContract.View mViewModel;

    TermsPresenter(Context mContext, TermsContract.View mViewModel) {
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
