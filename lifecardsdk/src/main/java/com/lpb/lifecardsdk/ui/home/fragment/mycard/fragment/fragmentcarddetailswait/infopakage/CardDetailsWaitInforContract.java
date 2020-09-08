package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.infopakage;

import com.lpb.lifecardsdk.data.model.response.default_.CardPackageServiceDetailResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface CardDetailsWaitInforContract {
    interface View extends BaseView {
        void setData(CardPackageServiceDetailResponseDefault data);
    }

    interface Presenter extends BasePresenter {
        void getDataInforCard(String mobilePhone, String code);
    }
}
