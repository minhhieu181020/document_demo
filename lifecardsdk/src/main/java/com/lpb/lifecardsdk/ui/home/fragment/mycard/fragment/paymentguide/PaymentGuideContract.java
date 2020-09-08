package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse2;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentMethodResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface PaymentGuideContract {
    interface View extends BaseView {
        void setPaymentGuide2(PaymentGuideResponse2 data);
        void setPaymentMethodResponse(PaymentMethodResponse paymentMethodResponse);
    }

    interface Presenter extends BasePresenter {
        void getPaymentGuide2(String transID);
        void callAPIMethod();
    }
}
