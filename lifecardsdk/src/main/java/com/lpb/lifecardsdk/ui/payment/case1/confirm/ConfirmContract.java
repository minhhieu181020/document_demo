package com.lpb.lifecardsdk.ui.payment.case1.confirm;

import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface ConfirmContract {
    interface View extends BaseView {
        void setBill(InitPaymentResponse data);
    }

    interface Presenter extends BasePresenter {
        void getBill(String phoneNumber,String customerName,String defCardCode);
    }
}
