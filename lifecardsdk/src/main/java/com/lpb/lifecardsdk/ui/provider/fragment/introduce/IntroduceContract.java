package com.lpb.lifecardsdk.ui.provider.fragment.introduce;

import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface IntroduceContract {
    interface View extends BaseView {
        void setDataIntroduce(ProviderResponse data);
    }

    interface Presenter extends BasePresenter {

    }
}
