package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.confirm;

import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitRechargeResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 24/07/2020
 */
public interface ConfirmContract {
    interface View extends BaseView {
        void setDataPaymentWait(PaymentWaitRechargeResponse data);
    }

    interface Presenter extends BasePresenter {
        void getDataPaymentWait(String body);
    }
}
