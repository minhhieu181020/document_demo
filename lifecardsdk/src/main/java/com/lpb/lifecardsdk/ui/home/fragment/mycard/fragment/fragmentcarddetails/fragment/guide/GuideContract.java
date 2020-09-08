package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide;

import com.lpb.lifecardsdk.data.model.response.default_.GuideResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

/**
 * Created by vannh.lvt on 17/06/2020
 */
public interface GuideContract {
    interface View extends BaseView {
        void setDataGuide(GuideResponse dataGuide);
    }

    interface Presenter extends BasePresenter {
        void getDataGuide(String body);
    }
}
