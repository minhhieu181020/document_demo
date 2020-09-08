package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.card_information;

import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface CardInforContract {
    interface View extends BaseView {
        void setDataPackage(List<PackageDetailResponse.DefServiceDto> items);
    }

    interface Presenter extends BasePresenter {

    }
}
