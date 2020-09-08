package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.addcard;

import com.lpb.lifecardsdk.data.model.response.default_.CardPhysicalResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface AddCardContract {
    interface View extends BaseView {
        void setData(CardPhysicalResponseDefault.OwnCardDto cardPhysicalResponseDefault);
    }

    interface Presenter extends BasePresenter {
        void getDataCard(String cardNo, String customerName, String mobilePhone, String token);
    }
}
