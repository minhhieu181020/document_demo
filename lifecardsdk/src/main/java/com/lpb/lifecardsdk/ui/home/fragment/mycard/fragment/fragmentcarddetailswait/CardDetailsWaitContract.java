package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait;

import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface CardDetailsWaitContract {
    interface View extends BaseView {
        void getDataCard(MyCardDetailsWaitResponseDefault model);

    }

    interface Presenter extends BasePresenter {
        void getCardDeatils(String mobilePhone, String cardNo);



        void onClick();

    }
}
