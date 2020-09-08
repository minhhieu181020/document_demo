package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search;

import android.content.Context;

public class SearchMyCardsPresenter implements SearchMyCardsContract.Presenter {
    private Context mContext;
    private SearchMyCardsContract.View mViewModel;

    SearchMyCardsPresenter(Context mContext, SearchMyCardsContract.View mViewModel) {
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
