package com.lpb.lifecardsdk.ui.payment.case2.customer_infor;

import android.content.Context;

public class CustomerInforPresenter implements CustomerInforContract.Presenter {
    private Context mContext;
    private CustomerInforContract.View mViewModel;

    public CustomerInforPresenter(Context mContext, CustomerInforContract.View mViewModel) {
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
