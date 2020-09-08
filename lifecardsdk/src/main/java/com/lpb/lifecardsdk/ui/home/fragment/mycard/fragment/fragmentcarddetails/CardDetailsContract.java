package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails;

import com.lpb.lifecardsdk.data.model.CardDeatilsInFor;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface CardDetailsContract {
    interface View extends BaseView {

        void showStatus(String s);

        void setView(MyCardDetailsWaitResponseDefault siew);


    }

    interface Presenter extends BasePresenter {
        void getCardDetails(String mobilePhone, String cardNo);


    }
}
