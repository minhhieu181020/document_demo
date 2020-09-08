package com.lpb.lifecardsdk.ui.home.fragment.mycode.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.EKYCConfig;
import com.lpb.lifecardsdk.constant.Function;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.request.base64.RequestBase64;
import com.lpb.lifecardsdk.data.model.request.default_.QrPersonalRequestDefault;
import com.lpb.lifecardsdk.data.model.response.base64.ResponseBase64;
import com.lpb.lifecardsdk.data.model.response.default_.EKYCConfigureResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.data.model.response.default_.QrPersonalResponseDefault;
import com.lpb.lifecardsdk.data.source.remote.Repository;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.facedetector.FaceDetectorActivity;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.CardDetailPackageFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet.MyCodeQr;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.adapter.CardDetailsInForAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.infopakage.CardDetailsWaitInforFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.adapter.CardMyCodeAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.adapter.MyCodeAdapter;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.HeightWrappingViewPager;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
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


public class MyCodeFragment extends BaseDataFragment<MyCodePresenter> implements MyCodeContract.View {
    TextView tv;
    private int i = 180;
    private int i1 = 179;
    private int i2 = 181;
    //
    private MyCodeAdapter adapter;
    private List<ListCardResponseDefault.OwnCardDto> OwnCardDtoList;
    private CirclePageIndicator indicator;
    private HeightWrappingViewPager mPager;
    private RecyclerView rvCardMyCode;
    private ImageView imgGenerateQR;
    private ImageView imgBack;
    private CardMyCodeAdapter cardMyCodeAdapter;
    private NestedScrollView nsvContainer;
    private TextView tvTitleToolbar;
    private LinearLayout lltimer, lltimeout, llDetailsCard, llCardInfor, imgQRcode, llPackageDetails;
    private TextView tvName, tvPhoneNumber, tvtotalNumber, tvUseLimit, tvLimitRemaining, tvExpirationDate, tvLabelExpiration;
    private LinearLayout llGuideFace;
    private TextView tvGuideFace;
    private ImageView imgClose;
    private LinearLayout llMycodeFragment, lltvtotalNumber;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String cardCode;
    private String serviceName;
    private boolean isCallAPINotify;
    private String CardNo = "";

    private boolean isShowGuideFace = true;
    private BroadcastReceiver changeStatusFaceVerify = null;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_mycode;
    }

    @Override
    protected void initView() {
        changeStatusFaceVerify = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isShowGuideFace = false;
                llGuideFace.setVisibility(View.GONE);
            }
        };

        LocalBroadcastManager.getInstance(mActivity).registerReceiver(changeStatusFaceVerify,
                new IntentFilter("changeStatusGuideFace"));

        tv = view.findViewById(R.id.tvTimeCount);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_common_my_code));
        nsvContainer = view.findViewById(R.id.nsvContainer);
        nsvContainer.setNestedScrollingEnabled(false);
        lltimer = view.findViewById(R.id.lltimer);
        lltimeout = view.findViewById(R.id.lltimeout);
        imgBack = view.findViewById(R.id.imgBack);
        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        llMycodeFragment = view.findViewById(R.id.llMycodeFragment);
        tvtotalNumber = view.findViewById(R.id.tvtotalNumber);
        llDetailsCard = view.findViewById(R.id.llDetailsCard);
        llCardInfor = view.findViewById(R.id.llCardInfor);
        imgQRcode = view.findViewById(R.id.imgQRcode);
        tvUseLimit = view.findViewById(R.id.tvUseLimit);
        tvLimitRemaining = view.findViewById(R.id.tvLimitRemaining);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        llPackageDetails = view.findViewById(R.id.llPackageDetails);
        lltvtotalNumber = view.findViewById(R.id.lltvtotalNumber);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        llGuideFace = view.findViewById(R.id.llGuideFace);
        tvGuideFace = view.findViewById(R.id.tvGuideFace);
        imgClose = view.findViewById(R.id.imgClose);
        OwnCardDtoList = new ArrayList<>();
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        imgGenerateQR = view.findViewById(R.id.imgGenerateQR);
        indicator.setRadius(ScreenUtils.sdpToPixel(mActivity, 4, 4));

        adapter = new MyCodeAdapter(OwnCardDtoList, mActivity);
        mPager.setAdapter(adapter);
        indicator.setViewPager(mPager);


        cardMyCodeAdapter = new CardMyCodeAdapter(mActivity);
        rvCardMyCode = view.findViewById(R.id.rvCardMyCode);
        rvCardMyCode.setAdapter(cardMyCodeAdapter);
        rvCardMyCode.setLayoutManager(new LinearLayoutManager(mActivity));
        tvName.setText(LCConfig.getCustomerName());
        tvPhoneNumber.setText(LCConfig.getPhoneNumber());

        swipeRefreshLayout = view.findViewById(R.id.main_swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.lifecardsdk_orange);

        //new Thread(new CallAPINotify()).start();

//        Lưu token

    }
//test

    @Override
    protected void initData() {
        mPresenter = new MyCodePresenter(mActivity, this);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            adapter.clearData();
            mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), "");
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
        perform_action();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected void initAction() {
        llGuideFace.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (EKYCConfig.isCallAPIConfigure()) {
                    startActivity(new Intent(mActivity, FaceDetectorActivity.class));
                } else {
                    if (PresenterUtils.isNetworkConnected(mActivity)) {
                        mPresenter.getEKYCConfigure();
                    } else {
                        showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                    }
                }
            }
        });
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mActivity.finish();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    mPager.setCurrentItem(0, true);
                    indicator.setCurrentItem(0);
                    adapter.clearData();
                    mPresenter.getDataMyCard(LCConfig.getPhoneNumber(), "");
                    call_perform_action();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    hideLoading();
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });
        imgQRcode.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                MyCodeQr qrCodeFragment = new MyCodeQr();
                Bundle bundle1 = new Bundle();
//                bundle1.putString(Constants.BundleConstants.CODECARD, cardCode);
                bundle1.putString(Constants.BundleConstants.PRODUCT_NAME, serviceName);
                bundle1.putString(Constants.BundleConstants.CARD_NO, CardNo);
                qrCodeFragment.setArguments(bundle1);
                qrCodeFragment.show(mActivity.getSupportFragmentManager(), qrCodeFragment.getTag());
            }
        });
        llCardInfor.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                CardDetailPackageFragment cardDetailPackageFragment = new CardDetailPackageFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.CARD);
                bundle1.putString(Constants.BundleConstants.CODECARD, cardCode);
                bundle1.putString(Constants.BundleConstants.SERVICE_NAME, serviceName);
                cardDetailPackageFragment.setArguments(bundle1);
                MainMyCodeFragment.getInstance().addFragment(cardDetailPackageFragment, MyCodeFragment.this);
            }
        });
        llDetailsCard.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                CardDetailPackageFragment cardDetailPackageFragment = new CardDetailPackageFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.CARD);
                bundle1.putString(Constants.BundleConstants.CODECARD, cardCode);
                bundle1.putString(Constants.BundleConstants.SERVICE_NAME, serviceName);
                cardDetailPackageFragment.setArguments(bundle1);
                MainMyCodeFragment.getInstance().addFragment(cardDetailPackageFragment, MyCodeFragment.this);
            }
        });
        cardMyCodeAdapter.setOnClickListener(new CardDetailsInForAdapter.OnClickListener() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.OwnServiceDto item) {
                if (item.getStatus().equalsIgnoreCase("W")) {
                    CardDetailsWaitInforFragment cardDetailsWaitFragment = new CardDetailsWaitInforFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(Constants.BundleConstants.MY_CARD_INFORMATION, item);
                    cardDetailsWaitFragment.setArguments(bundle1);
                    MainMyCodeFragment.getInstance().addFragment(cardDetailsWaitFragment, MyCodeFragment.this);

                } else {
                    CardDetailPackageFragment cardDetailPackageFragment = new CardDetailPackageFragment();

                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.SERVICE);
                    bundle1.putString(Constants.BundleConstants.CODECARD, item.getCode());
                    bundle1.putString(Constants.BundleConstants.SERVICE_NAME, item.getNamDefService());
                    cardDetailPackageFragment.setArguments(bundle1);
                    MainMyCodeFragment.getInstance().addFragment(cardDetailPackageFragment, MyCodeFragment.this);
                }
            }

            @Override
            public void onClickQrCode(MyCardDetailsWaitResponseDefault.OwnServiceDto item) {
                MyCodeQr qrCodeFragment = new MyCodeQr();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.SEE_QR_CODE, item);
                qrCodeFragment.setArguments(bundle1);
                qrCodeFragment.show(mActivity.getSupportFragmentManager(), qrCodeFragment.getTag());
            }
        });

        cardMyCodeAdapter.setOnClickLogoListener(new CardMyCodeAdapter.OnClickLogo() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);
                providerFragment.setArguments(bundle);
                MainMyCodeFragment.getInstance().addFragment(providerFragment, MyCodeFragment.this);
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                tvtotalNumber.setText((position + 1) + " / " + OwnCardDtoList.size());
//                Toast.makeText(mActivity, (position+1)+"-"+OwnCardDtoList.size(), Toast.LENGTH_SHORT).show();
                showLoading(true);
                mPresenter.getCardMyCode(LCConfig.getPhoneNumber(), OwnCardDtoList.get(position).getCardNo());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter.setOnClickLogoListener(new MyCodeAdapter.OnClickLogo() {
            @Override
            public void onClickItem(ListCardResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);
                providerFragment.setArguments(bundle);
                MainMyCodeFragment.getInstance().addFragment(providerFragment, MyCodeFragment.this);
            }
        });
        imgClose.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                llGuideFace.setVisibility(View.GONE);
                isShowGuideFace = false;
                LocalBroadcastManager.getInstance(mActivity).sendBroadcast(new Intent("changeStatusGuideFace"));
            }
        });
    }

    public void setview() {
        llMycodeFragment.setVisibility(View.VISIBLE);

    }
    @Override
    public void setEKYCConfigure(EKYCConfigureResponse ekycConfigureResponse) {
        try {
            EKYCConfig.setCallAPIConfigure(true);
            EKYCConfig.setHeight(Integer.parseInt(ekycConfigureResponse.getHeight()));
            EKYCConfig.setWidth(Integer.parseInt(ekycConfigureResponse.getWidth()));
            EKYCConfig.setImageQuality(Integer.parseInt(ekycConfigureResponse.getImageQuality()));
            EKYCConfig.setNumberOfActions(Integer.parseInt(ekycConfigureResponse.getNumberOfActions()));
            EKYCConfig.setActions(ekycConfigureResponse.getActions());
            startActivity(new Intent(mActivity, FaceDetectorActivity.class));
        } catch (java.lang.Exception e) {
            setDefaultEKYCConfigure();
        }
    }

    @Override
    public void setDefaultEKYCConfigure() {
        EKYCConfig.setHeight(null);
        EKYCConfig.setWidth(null);
        EKYCConfig.setImageQuality(null);
        EKYCConfig.setNumberOfActions(null);
        EKYCConfig.setActions(null);

        startActivity(new Intent(mActivity, FaceDetectorActivity.class));
    }
    private void perform_action() {

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
                    requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault("", LCConfig.getPhoneNumber(), null)));
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
                tv.setText(String.valueOf(i));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void GenerateQR(String string) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgGenerateQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void call_perform_action() {

        final String functionName = Function.FunctionName.GENERATE_QR_FRAGMENT;
        final String functionCode = Function.FunctionCode.GENERATE_QR_FRAGMENT;
        RequestBase64 requestBase64 = new RequestBase64();
        requestBase64.setBody(StringUtils.convertObjectToBase64(new QrPersonalRequestDefault("", LCConfig.getPhoneNumber(), null)));
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

    @Override
    public void setDataMyCard(ListCardResponseDefault dataMyCard) {
            OwnCardDtoList.clear();
            OwnCardDtoList.addAll(dataMyCard.getOwnCardDtos());
            CardNo = dataMyCard.getOwnCardDtos().get(0).getCardNo();
            if (OwnCardDtoList.size() == 1) {
                indicator.setVisibility(View.GONE);
                lltvtotalNumber.setVisibility(View.GONE);
            } else if (OwnCardDtoList.size() <= 10) {
                indicator.setVisibility(View.VISIBLE);
                lltvtotalNumber.setVisibility(View.GONE);
            } else {
                indicator.setVisibility(View.GONE);
                lltvtotalNumber.setVisibility(View.VISIBLE);
                tvtotalNumber.setText((1) + "/" + OwnCardDtoList.size());
            }
            if (dataMyCard.getOwnCardDtos().size() == 0) {
                hideLoading();
            } else {
                mPresenter.getCardMyCode(LCConfig.getPhoneNumber(), dataMyCard.getOwnCardDtos().get(0).getCardNo());
                showLoading(true);
            }


            if (isShowGuideFace) {
                if (!StringUtils.isEmpty(dataMyCard.getFaceRegistrationStatus())) {
                    if (dataMyCard.getFaceRegistrationStatus().equalsIgnoreCase("N")) {
                        if (StringUtils.isEmpty(dataMyCard.getGuideFaceRegistration())) {
                            llGuideFace.setVisibility(View.GONE);
                        } else {
                            llGuideFace.setVisibility(View.VISIBLE);
                            tvGuideFace.setText(dataMyCard.getGuideFaceRegistration());
                        }
                    } else {
                        llGuideFace.setVisibility(View.GONE);
                    }
                } else {
                    llGuideFace.setVisibility(View.GONE);
                }
            } else {
                llGuideFace.setVisibility(View.GONE);
            }


            adapter.changeData(dataMyCard.getOwnCardDtos());
    }

    @Override
    public void setDataCardMyCode(MyCardDetailsWaitResponseDefault dataCardMyCode) {
        if (dataCardMyCode.getOwnCardDto().getOwnServiceDtos() == null) {
            cardMyCodeAdapter.clearData();
            llPackageDetails.setVisibility(View.GONE);
            hideLoading();
        } else {
            if (dataCardMyCode.getOwnCardDto().getOwnServiceDtos().size() == 0) {
                cardMyCodeAdapter.clearData();
                llPackageDetails.setVisibility(View.GONE);
                hideLoading();
            } else {
                cardMyCodeAdapter.setItems(dataCardMyCode.getOwnCardDto().getOwnServiceDtos());
                llPackageDetails.setVisibility(View.VISIBLE);
                hideLoading();
            }
        }
        if (dataCardMyCode.getOwnCardDto().getRootService() != null) {
            if (dataCardMyCode.getOwnCardDto().getRootService().getOwnAccountDtos().size() != 0) {
                if (StringUtils.isEmpty(dataCardMyCode.getOwnCardDto().getRootService().getUsageDescription())) {
                    tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(dataCardMyCode.getOwnCardDto().getRootService().getOwnAccountDtos().get(0).getAllocatedUnit())));
                } else {
                    tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(dataCardMyCode.getOwnCardDto().getRootService().getUsageDescription())));
                }
                tvLimitRemaining.setText(StringUtils.convertHTMLToString("<b>" + dataCardMyCode.getOwnCardDto().getRootService().getOwnAccountDtos().get(0).getAvailableUnit() + "</b>", mActivity));
            } else {
                tvUseLimit.setText("");
                tvLimitRemaining.setText("");
            }
            if (StringUtils.isEmpty(dataCardMyCode.getOwnCardDto().getRootService().getExpirationDate())) {
                tvExpirationDate.setText("");
                tvLabelExpiration.setVisibility(View.GONE);
                tvExpirationDate.setVisibility(View.GONE);
            } else {
                tvLabelExpiration.setVisibility(View.VISIBLE);
                tvExpirationDate.setVisibility(View.VISIBLE);
                tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + dataCardMyCode.getOwnCardDto().getRootService().getExpirationDate() + "</b>", mActivity));
            }
            cardCode = dataCardMyCode.getOwnCardDto().getRootService().getCode();
            serviceName = dataCardMyCode.getOwnCardDto().getRootService().getNamDefService();
            CardNo = dataCardMyCode.getOwnCardDto().getCardNo();
            llCardInfor.setVisibility(View.VISIBLE);
            hideLoading();
        } else {
            llCardInfor.setVisibility(View.GONE);
            hideLoading();
        }
    }

    private void setCallAPINotify(boolean b) {
        isCallAPINotify = b;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (changeStatusFaceVerify != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(changeStatusFaceVerify);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        ;
        setCallAPINotify(false);
    }

    private class CallAPINotify implements Runnable {

        @Override
        public void run() {
            while (true) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isCallAPINotify) {

                        }
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
