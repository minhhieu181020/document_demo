package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.listservice;

import android.content.Context;

/**
 * Created by vannh.lvt on 17/06/2020
 */
public class ListServicePresenter implements ListServiceContract.Presenter {

    private Context mContext;
    private ListServiceContract.View mViewModel;

    ListServicePresenter(Context mContext, ListServiceContract.View mViewModel) {
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
