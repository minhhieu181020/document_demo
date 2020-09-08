package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.mycardcode;

import com.lpb.lifecardsdk.data.model.response.default_.ListMyCardCodeResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

public interface MyCardCodeContract {
    interface ViewModel extends BaseView {
        void setDataListCard(ListMyCardCodeResponseDefault listCard);
    }

    interface Presenter extends BasePresenter {
        void getDataListCard(String mobilePhone,long pageIndex,long pageSize);
    }
}
