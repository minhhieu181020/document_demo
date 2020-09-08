package com.lpb.lifecardsdk.ui.payment.case2.notify;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface NotifyContract {
    interface View extends BaseView {
        void showContent(PaymentWaitResponse data);
    }

    interface Presenter extends BasePresenter {
        void getDataPayment(Integer amount,String defCardNo,String mobilePhone,String orderNo);
    }
}
