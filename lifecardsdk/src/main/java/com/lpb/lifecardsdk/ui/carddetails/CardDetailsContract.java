package com.lpb.lifecardsdk.ui.carddetails;

import com.lpb.lifecardsdk.data.model.CardDetails;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface CardDetailsContract {
    interface View extends BaseView {
        void setDataCardDeatils(List<CardDetails> cardList);
    }

    interface Presenter extends BasePresenter {
        void getCardDeatils();

        void onClickCardItem(CardDetails card);
    }
}
