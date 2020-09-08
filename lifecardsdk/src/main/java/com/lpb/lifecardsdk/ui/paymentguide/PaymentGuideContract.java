package com.lpb.lifecardsdk.ui.paymentguide;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse2;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface PaymentGuideContract {
    interface View extends BaseView {
        void setPaymentGuide(PaymentGuideResponse data);

        void setPaymentGuide2(PaymentGuideResponse2 data);

    }

    interface Presenter extends BasePresenter {
        void getPaymentGuide(String transID);
        void getPaymentGuide2(String transID);
    }
}
