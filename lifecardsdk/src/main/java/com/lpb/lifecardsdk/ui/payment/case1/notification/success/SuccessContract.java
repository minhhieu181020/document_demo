package com.lpb.lifecardsdk.ui.payment.case1.notification.success;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentResultResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface SuccessContract {
    interface View extends BaseView {
        void setPaymentResult(PaymentResultResponse result);
    }

    interface Presenter extends BasePresenter {
        void getPaymentResult(String mobilePhone, String orderNo, String paymentStatus);
    }
}
