package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails_v2.guide;

import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.data.model.response.default_.GuideResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface GuideContract {
    interface View extends BaseView {
        void setDataGuide(GuideResponse dataGuide);
        void setDataBranch(List<BranchResponse.List> items);
        void hideProgressBar(String type);
    }

    interface Presenter extends BasePresenter {
        void getDataGuide(String body);
        void getDataBranch(String areaCode, String areaType, String providerId, int pageIndex, int rowCount);
    }
}
