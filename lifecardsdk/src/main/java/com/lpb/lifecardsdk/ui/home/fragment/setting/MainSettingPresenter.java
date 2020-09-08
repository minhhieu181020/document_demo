package com.lpb.lifecardsdk.ui.home.fragment.setting;

import android.content.Context;

/**
 * Created by vannh.lvt on 17/07/2020
 */
class MainSettingPresenter implements MainSettingContract.Presenter {
    private Context mContext;
    private MainSettingContract.View mViewModel;

    MainSettingPresenter(Context mContext, MainSettingContract.View mViewModel) {
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
