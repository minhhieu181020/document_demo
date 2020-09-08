package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodedeatailpayment;

import com.lpb.lifecardsdk.data.model.request.default_.ServicePaymentQrBillRequest;
import com.lpb.lifecardsdk.data.model.response.default_.ServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface QrCodePaymentDetailContract {
    interface View extends BaseView {
        void setdata(ServicePaymentQrBillResponse qrBillRequest);
    }

    interface Presenter extends BasePresenter {
        void callApiConFirm(ServicePaymentQrBillRequest qrBillRequest);
    }
}
