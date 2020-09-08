package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment;

import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface WaitForPayMentContract {
    interface View extends BaseView {

        void setDataMyCard(List<CardWaitForPayMent> dataCard);
    }

    interface Presenter extends BasePresenter {
        void getDataMyCard();

        void onClickCardItem(CardWaitForPayMent dataCard);

    }
}
