package com.lpb.lifecardsdk.ui.home.fragment.buycard.search.search_result;

import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface SearchResultContract {

    interface View extends BaseView {
        void setDataCategory(List<CategoryResponse.LstCate> categoryItemList);

        void setDataCard(List<PackageSearchResponse.DefCardDto> packageDtos);

        void addDataCard(List<PackageSearchResponse.DefCardDto> packageDtos);

        void showContent(boolean isEmpty);

        void stopLoadMore();
    }

    interface Presenter extends BasePresenter {
        void getDataCategory(String providerID);

        void getDataCard(Integer categoryID, String keywordQuery, String sort, String mobilePhone, int pageIndex, int pageSize, Integer providerID, int status);
    }

}
