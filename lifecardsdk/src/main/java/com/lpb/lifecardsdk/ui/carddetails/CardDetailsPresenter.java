package com.lpb.lifecardsdk.ui.carddetails;

import android.content.Context;
import android.widget.Toast;

import com.lpb.lifecardsdk.data.model.CardDetails;

public class CardDetailsPresenter implements CardDetailsContract.Presenter {
    private Context mContext;
    private CardDetailsContract.View mViewModel;

    public CardDetailsPresenter(Context mContext, CardDetailsContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        getCardDeatils();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getCardDeatils() {
//        List<CardDetails> cardDeatils = new ArrayList<>();
//
//        List<HistoryDeatilC2R3> historyDeatils1 = new ArrayList<>();
//
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//        historyDeatils1.add(new HistoryDeatilC2R3("01/02/2019", "+100.000 VNĐ", "500.000 VNĐ"));
//
//        List<HistoryDeatilC1R2> historyDeatilC1R2s = new ArrayList<>();
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//        historyDeatilC1R2s.add(new HistoryDeatilC1R2("02/02/2020", "20.202.020 VNĐ"));
//
//        List<HistoryDeatilC3R4> historyDeatilC3R4s = new ArrayList<>();
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        historyDeatilC3R4s.add(new HistoryDeatilC3R4("08/08/2019", "+100.000 VNĐ", "1", "500.000VND"));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", historyDeatilC1R2s, null, null, 1));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, historyDeatils1, null, 2));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, null, historyDeatilC3R4s, 3));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", historyDeatilC1R2s, null, null, 1));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, historyDeatils1, null, 2));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, null, historyDeatilC3R4s, 3));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", historyDeatilC1R2s, null, null, 1));
////        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, historyDeatils1, null, 2));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, null, historyDeatilC3R4s, 3));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", historyDeatilC1R2s, null, null, 1));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, historyDeatils1, null, 2));
//        cardDeatils.add(new CardDetails(R.mipmap.lifecardsdk_logo, R.drawable.qrcode, "highlands coffee", "Sử dụng dịch vụ đồ uống",
//                "Còn hiệu lực", "1.000.000 VNĐ", "500.000 VNĐ", "01/02/2019-31/12/2020", null, null, historyDeatilC3R4s, 3));

//        mViewModel.setDataCardDeatils(cardDeatils);
    }

    @Override
    public void onClickCardItem(CardDetails card) {

    }
}
