package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.scanqr;


import android.net.Uri;

import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface ScanQRContract {
    interface View extends BaseView {
        void setview(boolean b,String cardCode);
        void setviewServicesByQRBIll(boolean b, ListServicePaymentQrBillResponse listservicePaymentQrBillResponse);

    }

    interface Presenter extends BasePresenter {
        String decodeQRImage(String path);

        String getPathFromUri(Uri uri);


        void getDataCardInformation(String cardCode);
        void getServicesByQRBIll(String qrcode);
    }
}
