package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information;

import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface CardInforWaitContact {
    interface View extends BaseView {
        void setDataPackage(List<MyCardDetailsWaitResponseDefault.OwnServiceDto> items);
    }

    interface Presenter extends BasePresenter {

    }
}
