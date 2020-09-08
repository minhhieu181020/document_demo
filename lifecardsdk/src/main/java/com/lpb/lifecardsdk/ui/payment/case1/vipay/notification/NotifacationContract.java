package com.lpb.lifecardsdk.ui.payment.case1.vipay.notification;

import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface NotifacationContract {
    interface View extends BaseView {
        void paymentSuccess(String orderNo);
        void paymentFail(String orderNo);
        }

    interface Presenter extends BasePresenter {

    }
}
