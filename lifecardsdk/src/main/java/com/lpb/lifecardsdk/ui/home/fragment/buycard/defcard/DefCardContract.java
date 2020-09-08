package com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard;

import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardListConfig;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface DefCardContract {
    interface View extends BaseView {
        void setDataLocation(List<AreaResponse.ListArea> locationItems);
        void setDataProvider(List<ProviderResponse.List> providerItems);
        void setDataCategory(List<CategoryResponse.LstCate> categoryItemList);
        void setDataCard(List<CardListConfig> cardListConfigs);
        void addDataCard(List<CardListConfig> cardListConfigs);
        void stopLoadMore();
        void showContent();
        void setRefreshing(boolean b);
    }

    interface Presenter extends BasePresenter {
        void getDataCategory();
        void getDataLocation();
        void getDataProvider();
        void getDataCard(Integer categoryID, String keywordQuery, String sort, String mobilePhone, int pageIndex, int pageSize);

    }
}
