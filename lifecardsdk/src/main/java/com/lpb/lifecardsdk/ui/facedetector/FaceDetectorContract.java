package com.lpb.lifecardsdk.ui.facedetector;

import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;
import com.lpb.lifecardsdk.widget.facedetector.TVSelfieImage;

/**
 * Created by vannh.lvt on 13/07/2020
 */
public interface FaceDetectorContract {
    interface View extends BaseView {
         void faceVerifySuccess();
        void faceVerifyFail();
    }

    interface Presenter extends BasePresenter {
        String convertBase64ImageLive(TVSelfieImage selfieImage);
        void faceVerify(String body);
    }
}
