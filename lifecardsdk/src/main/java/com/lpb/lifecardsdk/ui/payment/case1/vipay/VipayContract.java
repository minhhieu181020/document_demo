package com.lpb.lifecardsdk.ui.payment.case1.vipay;

import com.lpb.lifecardsdk.data.model.response.default_.ResultVipayResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface VipayContract {
    interface View extends BaseView {
        void LoadUrlViPay(String url);
        void getStatus(ResultVipayResponse response,boolean a);
        }

    interface Presenter extends BasePresenter {
        void getUrlViPay(String customerName, String mobilePhone, String defCardCode);
        void getResultUrlViPay(String url);
    }
}
