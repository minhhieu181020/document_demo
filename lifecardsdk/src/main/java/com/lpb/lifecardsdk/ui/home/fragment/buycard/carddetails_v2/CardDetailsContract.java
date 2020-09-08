package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2;

import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface CardDetailsContract {
    interface View extends BaseView {
        void showContentAndInitFragment(PackageDetailResponse data);
    }

    interface Presenter extends BasePresenter {
        void getDataCardInformation(String cardCode);
    }
}
