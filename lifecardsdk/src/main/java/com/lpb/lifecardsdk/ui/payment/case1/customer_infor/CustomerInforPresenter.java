package com.lpb.lifecardsdk.ui.payment.case1.customer_infor;

import android.content.Context;

public class CustomerInforPresenter implements CustomerInfoContract.Presenter {
    private Context mContext;
    private CustomerInfoContract.View mViewModel;

    CustomerInforPresenter(Context mContext, CustomerInfoContract.View mViewModel) {
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
