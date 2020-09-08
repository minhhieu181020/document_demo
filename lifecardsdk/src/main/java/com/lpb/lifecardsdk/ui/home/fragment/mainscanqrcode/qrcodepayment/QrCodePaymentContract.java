package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodepayment;

import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface QrCodePaymentContract {
    interface View extends BaseView {
        void setListCardService(List<ListServicePaymentQrBillResponse.ServiceDetailData> listCardService);

        void getData(ListServicePaymentQrBillResponse data);
    }

    interface Presenter extends BasePresenter {
        void getListCardService();

        void getListCardService(String id);
    }
}
