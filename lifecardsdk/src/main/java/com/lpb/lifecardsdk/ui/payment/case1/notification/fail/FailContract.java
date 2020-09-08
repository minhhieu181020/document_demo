package com.lpb.lifecardsdk.ui.payment.case1.notification.fail;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentResultResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface FailContract {
    interface View extends BaseView {
        void setPaymentResult(PaymentResultResponse result);
        void setResultDesc();
    }

    interface Presenter extends BasePresenter {
        void getPaymentResult(String mobilePhone, String orderNo, String paymentStatus);
    }
}
