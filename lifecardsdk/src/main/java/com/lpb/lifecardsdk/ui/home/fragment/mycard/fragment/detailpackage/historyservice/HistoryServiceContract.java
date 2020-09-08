package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice;

import com.lpb.lifecardsdk.data.model.response.default_.TransactionHistoryResponseDefault;
import com.lpb.lifecardsdk.ui.base.BasePresenter;
import com.lpb.lifecardsdk.ui.base.BaseView;

import java.util.List;

public interface HistoryServiceContract {
    interface View extends BaseView {
        void setDataHistoryServiceC1R2(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC1R2s);
        void addDataHistoryServiceC1R2(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC1R2s);

        void setDataHistoryServiceC2R3(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC2R3);
        void addDataHistoryServiceC2R3(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC2R3);

        void setDataHistoryServiceC3R4(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC3R4);
        void addDataHistoryServiceC3R4(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC3R4);

        void setData(TransactionHistoryResponseDefault transactionHistoryResponseDefault);


        void showStatus(String s);

        void SetView(String s);
        void setviewnodata(boolean b);
    }

    interface Presenter extends BasePresenter {
        void getDataCard(String mobilePhone, String Code, String research, Integer pageIndex, Integer pageSize, String researchStartTime, String researchEndTime);

        void getCardHistoryServiceC1R2();

        void getCardHistoryServiceC2R3();

        void getCardHistoryServiceC3R4();

        void onClickC1R2(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC1R2);

        void onClickC2R3(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC2R3);

        void onClickC3R4(TransactionHistoryResponseDefault.AccountEntryDto historyServiceC3R4);

    }
}
