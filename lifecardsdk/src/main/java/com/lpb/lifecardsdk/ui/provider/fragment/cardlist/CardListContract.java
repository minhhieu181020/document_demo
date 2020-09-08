package com.lpb.lifecardsdk.ui.provider.fragment.cardlist;

import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardListConfig;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface CardListContract {
    interface View extends BaseView {
        void setDataCategory(List<CategoryResponse.LstCate> categoryItemList);
        void setDataCard(List<CardListConfig> packageDtos);
        void addDataCard(List<CardListConfig> packageDtos);
        void showContent();
        void stopLoadMore();
        void setRefreshing(boolean b);
    }

    interface Presenter extends BasePresenter {
        void getDataCategory(String providerID);
        void getDataCard(Integer categoryID,String keywordQuery,String sort,String mobilePhone,int pageIndex,int pageSize,int providerID,int status);
    }
}
