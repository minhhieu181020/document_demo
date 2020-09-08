package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.face_verify_infor;

import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public interface FaceVerifyInforContract {
    interface View extends BaseView {
        void backToHome(int tabPosition);
    }

    interface Presenter extends BasePresenter {
        void deleteFace();
    }
}
