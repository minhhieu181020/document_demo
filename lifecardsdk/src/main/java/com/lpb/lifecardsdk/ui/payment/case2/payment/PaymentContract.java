package com.lpb.lifecardsdk.ui.payment.case2.payment;

import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public interface PaymentContract {
    interface View extends BaseView {
        void setDataOrderInfo(InitPaymentResponse data);
    }

    interface Presenter extends BasePresenter {
        void getDataOrderInfo(String productCode);
    }
}
