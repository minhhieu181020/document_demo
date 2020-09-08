package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information;

import android.content.Context;

public class CardInforWaitPresenter implements CardInforWaitContact.Presenter {
    private Context mContext;
    private CardInforWaitContact.View mViewModel;

    CardInforWaitPresenter(Context mContext, CardInforWaitContact.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    public void getDataPackage() {
    }
}
