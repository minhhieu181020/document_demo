package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment;

import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface MyCardContract {
    interface View extends BaseView {

        void setDataCardWaitForPayMent(List<PackageSearchResponse.DefCardDto> packageDtos);
        void addDataCardWaitForPayMent(List<PackageSearchResponse.DefCardDto> packageDtos);
        void setDataMyCard(ListCardResponseDefault dataMyCard);
        void addDataMyCard(List<ListCardResponseDefault.OwnCardDto> dataMyCard);

        void showContent();

        void setView(String s);

        void setEKYCConfigure(EKYCConfigureResponse ekycConfigureResponse);
        void setDefaultEKYCConfigure();
    }

    interface Presenter extends BasePresenter {


        void getDataCardWaitForPayMent(Integer categoryID,String keywordQuery,String sort,String mobilePhone,int pageIndex,int pageSize,boolean isFirst);

        void onClickCardItem(ListCardResponseDefault.OwnCardDto card);

        void getDataMyCard(String mobilePhone,String keyFilter,int page, int pageSize, boolean b);

        void onClickCardWaitItem(CardWaitForPayMent dataCard);

        void getEKYCConfigure();
    }

}




