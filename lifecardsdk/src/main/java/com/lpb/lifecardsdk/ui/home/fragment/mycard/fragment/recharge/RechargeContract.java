package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge;

import com.lpb.lifecardsdk.data.model.response.default_.DiscountRechargeResponse;
import com.lpb.lifecardsdk.data.model.response.default_.RechargeResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 24/07/2020
 */
public interface RechargeContract {
    interface View extends BaseView {
        void setOwnCardRecharge(RechargeResponse recharge);
        void setDiscountRecharge(DiscountRechargeResponse discountRecharge);
    }

    interface Presenter extends BasePresenter {
        void ownCardRecharge(String body);
        void getDiscountRecharge(String body);
    }
}
