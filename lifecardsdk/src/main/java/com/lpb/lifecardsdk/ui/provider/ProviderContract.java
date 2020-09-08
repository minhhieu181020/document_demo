package com.lpb.lifecardsdk.ui.provider;

import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface ProviderContract {
    interface View extends BaseView {
        void setDataProvider(ProviderResponse data);
    }

    interface Presenter extends BasePresenter {
        void getDataProvider(String providerID);
    }
}
