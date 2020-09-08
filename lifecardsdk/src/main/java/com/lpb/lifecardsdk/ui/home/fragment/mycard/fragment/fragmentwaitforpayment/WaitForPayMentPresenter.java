package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment;

import android.content.Context;

import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;

public class WaitForPayMentPresenter implements WaitForPayMentContract.Presenter {
    private Context mContext;
    private WaitForPayMentContract.View mViewModel;

    public WaitForPayMentPresenter(Context mContext, WaitForPayMentContract.View mViewModel) {
        this.mContext = mContext;
        this.mViewModel = mViewModel;
        getDataMyCard();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getDataMyCard() {
//        List<CardWaitForPayMent> cardWaitForPayMents = new ArrayList<>();
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.imglogo3}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        cardWaitForPayMents.add(new CardWaitForPayMent(R.mipmap.lifecardsdk_background, new int[]{R.mipmap.lifecardsdk_logo, R.mipmap.lifecardsdk_logo2}, "Sử dụng dịch vụ đồ uống", "123456", "3.000.000 đ", "<del>5.000.000 đ</del>", "Tặng kèm 01 bộ ly uống trà mang thương\n" +
//                "hiệu Higlands Coffee trị giá <b>200.000 đ</b>"));
//        mViewModel.setDataMyCard(cardWaitForPayMents);

    }

    @Override
    public void onClickCardItem(CardWaitForPayMent dataCard) {

    }
}
