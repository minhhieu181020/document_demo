package com.lpb.lifecardsdk.ui.search;

import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface SearchContract {
    interface View extends BaseView {
        void addFragment();
    }

    interface Presenter extends BasePresenter {

    }
}
