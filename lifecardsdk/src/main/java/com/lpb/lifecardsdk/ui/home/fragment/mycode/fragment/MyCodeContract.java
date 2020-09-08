package com.lpb.lifecardsdk.ui.home.fragment.mycode.fragment;

import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface MyCodeContract {
    interface View extends BaseView {
        void setDataMyCard(ListCardResponseDefault dataMyCard);

        void setDataCardMyCode(MyCardDetailsWaitResponseDefault dataCardMyCode);

        void setview();
        void setEKYCConfigure(EKYCConfigureResponse ekycConfigureResponse);
        void setDefaultEKYCConfigure();
    }

    interface Presenter extends BasePresenter {
        void getDataMyCard(String mobilePhone, String keyFilter);

        void getCardMyCode(String mobilePhone, String cardNo);

        void getEKYCConfigure();
    }
}
