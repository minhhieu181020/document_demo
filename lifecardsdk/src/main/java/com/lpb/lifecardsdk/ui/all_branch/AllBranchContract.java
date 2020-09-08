package com.lpb.lifecardsdk.ui.all_branch;

import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;


/**
 * Created by vannh.lvt on 07/07/2020
 */
public interface AllBranchContract {

    interface View extends BaseView {
        void setAllBranches2Map(List<BranchResponse.List> items);
    }

    interface Presenter extends BasePresenter {
        void getAllBranches(String body);
    }
}
