package com.lpb.lifecardsdk.ui.home.fragment.buycard.content;

import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface BuyCardContract {
    interface View extends BaseView {
        void setDataLocation(List<AreaResponse.ListArea> locationItems);
        void setDataProvider(List<ProviderResponse.List> providerItems);
        void setDataCategory(List<CategoryResponse.LstCate> categoryItemList);
        void setDataCard(List<PackageSearchResponse.DefCardDto> packageDtos);
        void addDataCard(List<PackageSearchResponse.DefCardDto> packageDtos);
        void stopLoadMore();
        void showContent();
        void setRefreshing(boolean b);
    }

    interface Presenter extends BasePresenter {
        void getDataCategory(boolean isFirst);
        void getDataLocation();
        void getDataProvider();
        void getDataCard(Integer categoryID,String keywordQuery,String sort,String mobilePhone,int pageIndex,int pageSize,boolean isFirst,boolean... isRefresh);

    }
}
