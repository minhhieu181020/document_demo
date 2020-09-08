package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.terms;

import android.content.Context;

public class TermsPresenter1 implements TermsContact1.Presenter {
    private Context mContext;
    private TermsContact1.View mViewModel;

    TermsPresenter1(Context mContext, TermsContact1.View mViewModel) {
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
