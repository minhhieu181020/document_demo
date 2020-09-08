package com.lpb.lifecardsdk.ui.provider.fragment.branch;

import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface BranchContract {
    interface View extends BaseView {
        void setDataBranch(List<BranchResponse.List> items);

        void addDataBranch(List<BranchResponse.List> items);

        void setRefreshing(boolean b);

        void hideProgressBar();
    }

    interface Presenter extends BasePresenter {
        void getDataBranch(String areaCode, String areaType, String providerId, int pageIndex, int rowCount);
    }
}
