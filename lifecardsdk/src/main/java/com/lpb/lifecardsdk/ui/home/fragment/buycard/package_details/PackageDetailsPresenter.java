package com.lpb.lifecardsdk.ui.home.fragment.buycard.package_details;

import android.content.Context;

public class PackageDetailsPresenter implements PackageDetailsContract.Presenter {
    private Context mContext;
    private PackageDetailsContract.View mViewModel;

     PackageDetailsPresenter(Context mContext, PackageDetailsContract.View mViewModel) {
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
