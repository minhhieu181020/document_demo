package com.lpb.lifecardsdk.ui.search;

import android.content.Context;

public class SearchPresenter implements SearchContract.Presenter {
    private Context mContext;
    private SearchContract.View mViewModel;

    public SearchPresenter(Context mContext, SearchContract.View mViewModel) {
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
