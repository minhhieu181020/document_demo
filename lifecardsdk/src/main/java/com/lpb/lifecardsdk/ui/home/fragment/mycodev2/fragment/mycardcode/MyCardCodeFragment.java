package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.mycardcode;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.QrPersonalRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.ListMyCardCodeResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.QrPersonalResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.MainMyCodeV2Fragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.adapter.MyCodeV2Adapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.HeightWrappingViewPager;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;
import com.lpb.lifecardsdk.widget.zxing.BarcodeEncoder;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.BarcodeFormat;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.MultiFormatWriter;
import com.lpb.lifecardsdk.widget.zxing.zxing.barcodescaner.WriterException;
import com.lpb.lifecardsdk.widget.zxing.zxing.common.BitMatrix;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCardCodeFragment extends BaseDataFragment<MyCardCodePresenter> implements MyCardCodeContract.ViewModel {
    private TextView tvContent;
    private SwipeRefreshLayout mainSwiperefresh;
    private NestedScrollView nsvContainer;
    private LinearLayout llMycodeFragment;
    private TextView tvNameCards;
    private TextView tvNumberCard;
    private ImageView imgGenerateQR;
    private LinearLayout lltimer;
    private TextView tvTimeCount;
    private LinearLayout lltimeout;
    private LinearLayout lltvtotalNumber;
    private TextView tvtotalNumber;
    private CirclePageIndicator indicator;
    private HeightWrappingViewPager mpager;
    private MyCodeV2Adapter adapter;
    private ListMyCardCodeResponseDefault listMyCardCodeResponseDefault;
    private List<ListMyCardCodeResponseDefault.ListActiveCardDto> listActiveCardDtos;
    private int i = 180;
    private int i1 = 179;
    private int i2 = 181;
    private int mposition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_my_card_code;
    }

    @Override
    protected void initView() {
        mainSwiperefresh = view.findViewById(R.id.main_swiperefresh);
        nsvContainer = view.findViewById(R.id.nsvContainer);
        llMycodeFragment = view.findViewById(R.id.llMycodeFragment);
        tvNameCards = view.findViewById(R.id.tvNameCards);
        tvNumberCard = view.findViewById(R.id.tvNumberCard);
        imgGenerateQR = view.findViewById(R.id.imgGenerateQR);
        lltimer = view.findViewById(R.id.lltimer);
        tvTimeCount = view.findViewById(R.id.tvTimeCount);
        lltimeout = view.findViewById(R.id.lltimeout);
        tvContent = view.findViewById(R.id.tvContent);
        lltvtotalNumber = view.findViewById(R.id.lltvtotalNumber);
        tvtotalNumber = view.findViewById(R.id.tvtotalNumber);
        indicator = view.findViewById(R.id.indicator);
        mpager = view.findViewById(R.id.pager);
        listActiveCardDtos = new ArrayList<>();
        tvContent.setText(StringUtils.convertHTMLToString(mActivity.getString(R.string.lifecardsdk_my_card_code_conten), mActivity));
        mainSwiperefresh = view.findViewById(R.id.main_swiperefresh);
        mainSwiperefresh.setColorSchemeResources(R.color.lifecardsdk_orange);

        indicator = view.findViewById(R.id.indicator);
        indicator.setRadius(ScreenUtils.sdpToPixel(mActivity, 4, 4));
        adapter = new MyCodeV2Adapter(listActiveCardDtos, mActivity);
        mpager.setAdapter(adapter);
        indicator.setViewPager(mpager);
    }

    @Override
    protected void initData() {
        mPresenter = new MyCardCodePresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        listMyCardCodeResponseDefault = (ListMyCardCodeResponseDefault) bundle.getSerializable("listMyCardCodeResponseDefault");
        setDataListCard(listMyCardCodeResponseDefault);
//        if (PresenterUtils.isNetworkConnected(mActivity)) {
//            mPresenter.getDataListCard(LCConfig.getPhoneNumber(), 0L, 100L);
//        } else {
//            hideLoading();
//            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
//        }
    }

    @Override
    protected void initAction() {
        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                mposition=position;
                tvtotalNumber.setText((position + 1) + " / " + listActiveCardDtos.size());
                tvNameCards.setText(listActiveCardDtos.get(position).getName());
                tvNumberCard.setText(listActiveCardDtos.get(position).getCardNoDisplay());

//                Hàm call API mã QR thẻ + mainSwiperefresh
                call_perform_action(listActiveCardDtos.get(position).getCardNoDisplay(), LCConfig.getPhoneNumber(), null);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mainSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    mpager.setCurrentItem(0, true);
                    indicator.setCurrentItem(0);
                    adapter.clearData();
                    mPresenter.getDataListCard(LCConfig.getPhoneNumber(), 0L, 100L);
                    call_perform_action(listActiveCardDtos.get(0).getCardNoDisplay(), LCConfig.getPhoneNumber(), null);
                    mainSwiperefresh.setRefreshing(false);
                } else {
                    mainSwiperefresh.setRefreshing(false);
                    hideLoading();
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        adapter.setOnClickListener(new MyCodeV2Adapter.OnClickListener() {
            @Override
            public void onClickItem(ListMyCardCodeResponseDefault.ListActiveCardDto item) {
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, item.getCardNoDisplay());
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY, item.getCardNoDisplay());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);
                cardInforFragment.setArguments(bundle1);
                MainMyCodeV2Fragment.getInstance().addFragment(cardInforFragment, getParentFragment());
                Toast.makeText(mActivity, "" + item.getCardNoDisplay(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickItem(List<ListMyCardCodeResponseDefault.ListActiveCardDto> expireCardList) {
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, expireCardList.get(mposition).getCardNo());
//                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, "210000108782");
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY, expireCardList.get(mposition).getCardNoDisplay());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);
                cardInforFragment.setArguments(bundle1);
                MainMyCodeV2Fragment.getInstance().addFragment(cardInforFragment, getParentFragment());
//                Toast.makeText(mActivity, "" + expireCardList.get(mposition).getCardNoDisplay(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void setDataListCard(ListMyCardCodeResponseDefault listCard) {
        listActiveCardDtos.clear();
        listActiveCardDtos.addAll(listCard.getListActiveCardDto());
        if (listActiveCardDtos.size() == 0) {
            mpager.setVisibility(View.GONE);
            indicator.setVisibility(View.GONE);
            lltvtotalNumber.setVisibility(View.GONE);
        } else if (listCard.getListActiveCardDto().size() <= 10) {
            mpager.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.VISIBLE);
            lltvtotalNumber.setVisibility(View.GONE);
            tvNameCards.setText(listActiveCardDtos.get(0).getName());
            tvNumberCard.setText(listActiveCardDtos.get(0).getCardNoDisplay());
        } else {
            mpager.setVisibility(View.VISIBLE);
            indicator.setVisibility(View.GONE);
            lltvtotalNumber.setVisibility(View.VISIBLE);
            tvNameCards.setText(listCard.getListActiveCardDto().get(0).getName());
            tvNumberCard.setText(listCard.getListActiveCardDto().get(0).getCardNoDisplay());
            tvtotalNumber.setText((1) + "/" + listCard.getListActiveCardDto().size());
        }
        if (i == 180) {
            perform_action(listActiveCardDtos.get(0).getCardNoDisplay(), LCConfig.getPhoneNumber(), null);
        }
        adapter.changeData(listCard.getListActiveCardDto());
    }

    private void perform_action(final String cardNo, final String mobilePhone, final String code) {

        final String functionName = Function.FunctionName.GENERATE_QR_FRAGMENT;
        final String functionCode = Function.FunctionCode.GENERATE_QR_FRAGMENT;

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                i--;
                if (i <= -1) {
                    lltimer.setVisibility(View.GONE);
                    lltimeout.setVisibility(View.VISIBLE);
                    if (i == -2) {
                        i = i2;
                    }
                } else if (i == i1) {

                    lltimeout.setVisibility(View.GONE);
                    lltimer.setVisibility(View.VISIBLE);
                    RequestBase64 requestBase64 = new RequestBase64();
                    requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault(cardNo, mobilePhone, code)));
                    requestBase64.setRestHeader(Config.Header.getHeader());
                    Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                byte[] data = Base64.decode(response.body().getBody(), Base64.DEFAULT);
                                try {
                                    String text = new String(data, StandardCharsets.UTF_8);
                                    QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                                    GenerateQR(model.getToken());
//                                    i = 180;
                                    i = model.getTimeToken();
                                    i1 = i;
                                    i2 = i + 1;
//
                                    Log.e("MyCodeQr", "onResponse: " + model.getToken());
//                                    Toast.makeText(mActivity, "" + model.getToken(), Toast.LENGTH_SHORT).show();
                                } catch (java.lang.Exception e) {
                                    Exception.handleException(e, mActivity, functionName, functionCode);
                                    hideLoading();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBase64> call, Throwable t) {
                            hideLoading();
                            Exception.handleMessageRequestFailure(t, mActivity, functionName, functionCode);
                        }
                    });

                }
                tvTimeCount.setText(String.valueOf(i));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void GenerateQR(String string) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgGenerateQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void call_perform_action(final String cardNo, final String mobilePhone, final String code) {

        final String functionName = Function.FunctionName.GENERATE_QR_FRAGMENT;
        final String functionCode = Function.FunctionCode.GENERATE_QR_FRAGMENT;
        RequestBase64 requestBase64 = new RequestBase64();
        requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault(cardNo, mobilePhone, code)));
        requestBase64.setRestHeader(Config.Header.getHeader());
        Repository.getInstance().getDataQrPersonal(requestBase64).enqueue(new Callback<ResponseBase64>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBase64> call, Response<ResponseBase64> response) {
                if (response.body() != null && response.isSuccessful()) {
                    byte[] data = Base64.decode(response.body().getBody(), Base64.DEFAULT);
                    try {
                        String text = new String(data, StandardCharsets.UTF_8);
                        QrPersonalResponseDefault model = new Gson().fromJson(text, QrPersonalResponseDefault.class);
                        GenerateQR(model.getToken());
//                        Toast.makeText(mActivity, "" + model.getToken(), Toast.LENGTH_SHORT).show();
                        i = model.getTimeToken();
                    } catch (java.lang.Exception e) {
                        Exception.handleException(e, mActivity, functionName, functionCode);
                        hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBase64> call, Throwable t) {
                hideLoading();
                Exception.handleMessageRequestFailure(t, mActivity, functionName, functionCode);
            }
        });
    }
}
