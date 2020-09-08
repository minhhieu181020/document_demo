package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.setting;

import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.mobile_config.MobileConfigResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 15/07/2020
 */
public interface SettingContract {
    interface View extends BaseView {
        void setDataSettings(MobileConfigResponse mobileConfigResponse);
        void setEKYCConfigure(EKYCConfigureResponse ekycConfigureResponse);
        void setDefaultEKYCConfigure();
    }

    interface Presenter extends BasePresenter {
        void getDataSettings(String body);
        void getEKYCConfigure();
    }
}
