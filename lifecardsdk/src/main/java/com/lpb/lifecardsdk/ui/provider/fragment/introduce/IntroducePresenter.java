package com.lpb.lifecardsdk.ui.provider.fragment.introduce;

import android.content.Context;

public class IntroducePresenter implements IntroduceContract.Presenter {
    private Context mContext;
    private IntroduceContract.View mViewModel;

     IntroducePresenter(Context mContext, IntroduceContract.View mViewModel) {
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
