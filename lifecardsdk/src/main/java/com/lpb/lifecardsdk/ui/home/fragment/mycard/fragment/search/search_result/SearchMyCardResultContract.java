package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.search.search_result;

import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface SearchMyCardResultContract {
    interface View extends BaseView {
        void setDataMyCard(List<ListCardResponseDefault.OwnCardDto> dataMyCard);
        void showContent(boolean isEmpty);

    }

    interface Presenter extends BasePresenter {
        void getDataMyCard(String mobilePhone,String keyFilter);

    }
}
