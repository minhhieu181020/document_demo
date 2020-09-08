package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.white.WhiteFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter.CardPromotionAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.CardDetailPackageFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet.MyCodeQr;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.adapter.CardDetailsInForAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.GuideFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.listservice.ListServiceFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.RechargeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.MainMyCodeV2Fragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.ui.usermanager.UserManagerFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

import java.util.LinkedHashMap;


public class CardDetailsFragment extends BaseDataFragment<CardDetailsPresenter> implements CardDetailsContract.View {
    private RecyclerView rvPromotion;
    private ImageView imgBack, imgUserNumber;
    private TextView tvEffectDate, tvCode, tvCardPackageNo, tvTitleToolbar, tvUseLimit, tvLimitRemaining, tvExpirationDate, tvCodeProduct;
    private LinearLayout llExpire, llStillValidated, llPromotion, llDetailsCard, llCardInfor, imgQRCode, llInforCard, llLook, llTabLayout, llLine,llContentCode,llLabelCode;
    private TextView tvTitleService, tvGuide, tvLabelExpiration;
    private View viewRechargeStatus,viewInforCard;
    private TextView tvAmount;
    private TextView tvExpireDate;
    private TextView tvGuidePayment;
    private LinearLayout llRecharge;
    private TabLayout tabLayout;
    private ViewPager vpContainer;
    private CoordinatorLayout clContent;
    private AppBarLayout appBarLayout;
    private ConstraintLayout clUserNumber;
    private TextView tvUserNumber;
    private RecyclerView rvFreeService;
    private View viewCardInfor;
    private View viewPromotion, viewTitleService;
    private String cardCode;
    private String serviceName;
    private String cardNo = "";
    private String cardName;
    private String cardNoDisplay;
    private CardDetailsInForAdapter adapter;
    private AppBarLayout.LayoutParams params1, params2, params3, params4, params5, params6;
    private String mainParentClass;
    private boolean isFirstSelected = true;
    private String mStatusShare;
    private String mStatusCard;
    private Integer mNumberUser;

    private BroadcastReceiver changeUserNumber = null;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_card_details_3;
    }

    @Override
    protected void initView() {
        llContentCode = view.findViewById(R.id.llContentCode);
        llLabelCode = view.findViewById(R.id.llLabelCode);
        viewInforCard = view.findViewById(R.id.viewInforCard);
        imgUserNumber = view.findViewById(R.id.imgUserNumber);
        imgBack = view.findViewById(R.id.imgBack);
        llExpire = view.findViewById(R.id.llExpire);
        llStillValidated = view.findViewById(R.id.llStillValidated);
        tvEffectDate = view.findViewById(R.id.tvEffectDate);
        llPromotion = view.findViewById(R.id.llPromotion);
        tvCode = view.findViewById(R.id.tvCode);
        clUserNumber = view.findViewById(R.id.clUserNumber);
        tvUserNumber = view.findViewById(R.id.tvUserNumber);
        rvPromotion = view.findViewById(R.id.rvPromotion);
        tvCardPackageNo = view.findViewById(R.id.tvCardPackageNo);
        llDetailsCard = view.findViewById(R.id.llDetailsCard);
        llLine = view.findViewById(R.id.llLine);
        tvUseLimit = view.findViewById(R.id.tvUseLimit);
        tvLimitRemaining = view.findViewById(R.id.tvLimitRemaining);
        imgQRCode = view.findViewById(R.id.imgQRCode);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        llCardInfor = view.findViewById(R.id.llCardInfor);
        tvCodeProduct = view.findViewById(R.id.tvCodeProduct);
        clContent = view.findViewById(R.id.clContent);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        viewCardInfor = view.findViewById(R.id.viewCardInfor);
        viewPromotion = view.findViewById(R.id.viewPromotion);
        viewTitleService = view.findViewById(R.id.viewTitleService);
        tvTitleService = view.findViewById(R.id.tvTitleService);
        vpContainer = view.findViewById(R.id.vpContainer);
        tabLayout = view.findViewById(R.id.tabLayout);
        llTabLayout = view.findViewById(R.id.llTabLayout);
        tvGuide = view.findViewById(R.id.tvGuide);
        llLook = view.findViewById(R.id.llLook);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        llInforCard = view.findViewById(R.id.llInforCard);
        rvFreeService = view.findViewById(R.id.rvFreeService);
        viewRechargeStatus = view.findViewById(R.id.viewRechargeStatus);
        tvAmount = view.findViewById(R.id.tvAmount);
        tvExpireDate = view.findViewById(R.id.tvExpireDate);
        tvGuidePayment = view.findViewById(R.id.tvGuidePayment);
        tvGuidePayment.setText(StringUtils.underlineText(getString(R.string.lifecardsdk_payment_guide)));
        llRecharge = view.findViewById(R.id.llRecharge);
        params1 = (AppBarLayout.LayoutParams) viewPromotion.getLayoutParams();
        params2 = (AppBarLayout.LayoutParams) llPromotion.getLayoutParams();
        params3 = (AppBarLayout.LayoutParams) llCardInfor.getLayoutParams();
        params4 = (AppBarLayout.LayoutParams) viewCardInfor.getLayoutParams();
        params5 = (AppBarLayout.LayoutParams) llInforCard.getLayoutParams();
        params6 = (AppBarLayout.LayoutParams) viewTitleService.getLayoutParams();

        adapter = new CardDetailsInForAdapter(mActivity);
        rvFreeService.setLayoutManager(new LinearLayoutManager(mActivity));
        rvFreeService.setNestedScrollingEnabled(false);
        rvFreeService.setAdapter(adapter);

        changeUserNumber = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int i = intent.getIntExtra(Constants.BundleConstants.USER_NUMBER, 0);
                if (i == 0) {
                    tvUserNumber.setText(getString(R.string.lifecardsdk_common_add));
                } else if (i < 100) {
                    tvUserNumber.setText(String.valueOf(i));
                } else {
                    tvUserNumber.setText(getString(R.string.lifecardsdk_common_more_than_99));
                }

            }
        };
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(changeUserNumber,
                new IntentFilter(Constants.Actions.CHANGE_USER_NUMBER));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (changeUserNumber != null) {
            try {
                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(changeUserNumber);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initFragments(MyCardDetailsWaitResponseDefault data, String mainParentClass) {

        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

        switch (getContentType(data)) {
            case Constants.TypeCardDetails.ALL:
                ListServiceFragment cardInforWaitFragment = new ListServiceFragment();
                Bundle bundleAll = new Bundle();
                bundleAll.putSerializable(Constants.BundleConstants.CARD_DETAILS, data);
                bundleAll.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                cardInforWaitFragment.setArguments(bundleAll);
                lstFragments.put(cardInforWaitFragment, getString(R.string.lifecardsdk_info_pakage));
                if (!data.getOwnCardDto().getStatus().equalsIgnoreCase(Constants.Status.L)) {
                    com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment guideFragment = new com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment();
                    Bundle bundleGuide1 = new Bundle();
                    bundleGuide1.putString(Constants.BundleConstants.TERMS, data.getOwnCardDto().getUsageTerms());
                    bundleGuide1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    bundleGuide1.putString(Constants.BundleConstants.CARD_NO, data.getOwnCardDto().getCardNo());
                    bundleGuide1.putString(Constants.BundleConstants.TYPE, Constants.TypeCardDetails.ALL);
                    guideFragment.setArguments(bundleGuide1);
                    lstFragments.put(guideFragment, getString(R.string.lifecardsdk_common_guide));
                    llTabLayout.setVisibility(View.VISIBLE);
                } else {
                    params6.setScrollFlags(0);
                    if (data.getOwnCardDto().getOwnServiceDtos() != null && data.getOwnCardDto().getOwnServiceDtos().size() != 0) {
                        tvTitleService.setVisibility(View.VISIBLE);
                        viewTitleService.setVisibility(View.VISIBLE);
                    } else {
                        tvTitleService.setVisibility(View.GONE);
                        viewTitleService.setVisibility(View.GONE);
                    }
                    llTabLayout.setVisibility(View.GONE);
                }
                break;
            case Constants.TypeCardDetails.GUIDE:
                if (!data.getOwnCardDto().getStatus().equalsIgnoreCase(Constants.Status.L)) {
                    llTabLayout.setVisibility(View.GONE);
                    com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment guideFragment2 = new com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment();
                    Bundle bundleGuide2 = new Bundle();
                    bundleGuide2.putString(Constants.BundleConstants.TERMS, data.getOwnCardDto().getUsageTerms());
                    bundleGuide2.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    bundleGuide2.putString(Constants.BundleConstants.CARD_NO, data.getOwnCardDto().getCardNo());
                    bundleGuide2.putString(Constants.BundleConstants.TYPE, Constants.TypeCardDetails.GUIDE);
                    guideFragment2.setArguments(bundleGuide2);
                    lstFragments.put(guideFragment2, getString(R.string.lifecardsdk_common_guide));
                    //                    params1.setScrollFlags(0);
                } else {
                    llTabLayout.setVisibility(View.GONE);
                    WhiteFragment whiteFragment = new WhiteFragment();
                    lstFragments.put(whiteFragment, "");
                }
                break;
        }

        ViewPagerWithTabAdapter orderStatusAdapter = new ViewPagerWithTabAdapter(getChildFragmentManager());
        orderStatusAdapter.setItems(lstFragments);
        vpContainer.setAdapter(orderStatusAdapter);
        vpContainer.setOffscreenPageLimit(lstFragments.size());
        tabLayout.setupWithViewPager(vpContainer);

    }

    private String getContentType(MyCardDetailsWaitResponseDefault data) {
        if (data == null) return Constants.TypeCardDetails.GUIDE;
        if (data.getOwnCardDto() == null) return Constants.TypeCardDetails.GUIDE;
        if (data.getOwnCardDto().getOwnServiceDtos() == null || data.getOwnCardDto().getOwnServiceDtos().size() == 0) {
            return Constants.TypeCardDetails.GUIDE;
        } else {
            return Constants.TypeCardDetails.ALL;
        }
    }

    @Override
    protected void initData() {

        mPresenter = new CardDetailsPresenter(mActivity, this);
        cardNo = getArguments().getString(Constants.BundleConstants.MY_CARD_INFORMATION);
        mainParentClass = getArguments().getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(mainParentClass)) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }        //        String tvCardPackageName = getArguments().getString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            mPresenter.getCardDetails(LCConfig.getPhoneNumber(), cardNo);
            showLoading(true);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

        SpannableString ss = new SpannableString(getString(R.string.lifecardsdk_common_guide));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvGuide.setText(ss);
        tvGuide.setMovementMethod(LinkMovementMethod.getInstance());
        tvGuide.setHighlightColor(Color.TRANSPARENT);


    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mActivity.onBackPressed();
            }
        });
        llRecharge.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                RechargeFragment rechargeFragment = new RechargeFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleConstants.CODE, cardCode);
                rechargeFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(rechargeFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(rechargeFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(rechargeFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(rechargeFragment, CardDetailsFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCardsFragment.getInstance().addFragment(rechargeFragment, CardDetailsFragment.this);
            }
        });
        tvGuidePayment.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });
        imgQRCode.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                MyCodeQr qrCodeFragment = new MyCodeQr();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.PRODUCT_NAME, serviceName);
                bundle1.putString(Constants.BundleConstants.CARD_NO, cardNo);
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
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                cardDetailPackageFragment.setArguments(bundle1);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
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
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                cardDetailPackageFragment.setArguments(bundle1);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
            }
        });

        adapter.setOnClickListener(new CardDetailsInForAdapter.OnClickListener() {
            @Override
            public void onClickItem(final MyCardDetailsWaitResponseDefault.OwnServiceDto item) {
                CardDetailPackageFragment cardDetailPackageFragment = new CardDetailPackageFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.SERVICE);
                bundle1.putString(Constants.BundleConstants.SERVICE_NAME, item.getNamDefService());
                bundle1.putString(Constants.BundleConstants.CODECARD, item.getCode());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                cardDetailPackageFragment.setArguments(bundle1);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, CardDetailsFragment.this);
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
        adapter.setOnClickLogoListener(new CardDetailsInForAdapter.OnClickLogo() {
            @Override
            public void OnClickLogo(MyCardDetailsWaitResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                providerFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(providerFragment, CardDetailsFragment.this);
            }
        });

        tvGuide.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                GuideFragment guideFragment = new GuideFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                bundle.putString(Constants.BundleConstants.CARD_NO, cardNo);
                guideFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(guideFragment, CardDetailsFragment.this);
            }
        });
        clUserNumber.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (StringUtils.isEmpty(mStatusShare))
                    return;

                if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.N)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.K)) {
                    Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_cant_share_2), "");
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.Y)) {

                    UserManagerFragment userManagerFragment = new UserManagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER, cardNo);
                    bundle.putString(Constants.BundleConstants.CARD_NAME, cardName);
                    bundle.putString(Constants.BundleConstants.CARD_NUMBER_DISPLAY, cardNoDisplay);
                    if (mNumberUser != null)
                        bundle.putInt(Constants.BundleConstants.USER_NUMBER, mNumberUser);
                    if (!StringUtils.isEmpty(mStatusCard)) {
                        if (mStatusCard.equalsIgnoreCase("E") || mStatusCard.equalsIgnoreCase("C")) {
                            if (mNumberUser == null || mNumberUser < 1) {
                                Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_card_expire), "");
                                return;
                            } else {
                                bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                            }
                        } else {
                            bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, true);
                        }
                    } else {
                        bundle.putBoolean(Constants.BundleConstants.CAN_SHARE_CARD, false);
                    }

                    userManagerFragment.setArguments(bundle);
                    MainMyCardsFragment.getInstance().addFragment(userManagerFragment, CardDetailsFragment.this);
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isFirstSelected) {
                    isFirstSelected = false;
                } else {
                    appBarLayout.setActivated(true);
                    appBarLayout.setExpanded(false, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (isFirstSelected) {
                    isFirstSelected = false;
                } else {
                    appBarLayout.setActivated(true);
                    appBarLayout.setExpanded(false, true);
                }
            }
        });
    }

    @Override
    public void showStatus(String s) {
        //        - nếu status = 'O' hoặc = 'A' thì hiển thị là đã thanh toán
        //        - nếu status = 'w' thì hiển thị là đang chờ thanh toán
        //        - nếu status = 'E' hoặc 'C' thì hiển thị là đã hết hạn
        //        - nếu status = 'L' thì hiển thị là đã khóa
        if (s.equalsIgnoreCase("A") || s.equalsIgnoreCase("O")) {
            llExpire.setVisibility(View.GONE);
            llStillValidated.setVisibility(View.VISIBLE);
            llLook.setVisibility(View.GONE);
        } else if (s.equalsIgnoreCase("E") || s.equalsIgnoreCase("C")) {
            llExpire.setVisibility(View.VISIBLE);
            llStillValidated.setVisibility(View.GONE);
            llLook.setVisibility(View.GONE);
        } else {
            llLine.setVisibility(View.VISIBLE);
            imgQRCode.setVisibility(View.INVISIBLE);
            llExpire.setVisibility(View.GONE);
            llStillValidated.setVisibility(View.GONE);
            llLook.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setView(final MyCardDetailsWaitResponseDefault model) {
        if (model != null && model.getOwnCardDto() != null) {
            mNumberUser = model.getOwnCardDto().getNumberOfShareCardUsers();
            mStatusShare = model.getOwnCardDto().getCardShare();
            mStatusCard = model.getOwnCardDto().getStatus();
            if (!StringUtils.isEmpty(mStatusShare)) {
                if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.N)) {
                    imgUserNumber.setColorFilter(ContextCompat.getColor(mActivity, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                    clUserNumber.setVisibility(View.VISIBLE);
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.K)) {
                    imgUserNumber.setColorFilter(ContextCompat.getColor(mActivity, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                    clUserNumber.setVisibility(View.VISIBLE);
                } else if (mStatusShare.equalsIgnoreCase(Constants.ShareStatus.Y)) {
                    clUserNumber.setVisibility(View.VISIBLE);
                    tvUserNumber.setVisibility(View.VISIBLE);
                    if (mNumberUser == null || mNumberUser < 1) {
                        tvUserNumber.setText(getString(R.string.lifecardsdk_common_add));
                    } else if (mNumberUser < 100) {
                        tvUserNumber.setText(String.valueOf(mNumberUser));
                    } else {
                        tvUserNumber.setText(getString(R.string.lifecardsdk_common_more_than_99));
                    }

                    if (!StringUtils.isEmpty(mStatusCard)) {
                        if (mStatusCard.equalsIgnoreCase("E") || mStatusCard.equalsIgnoreCase("C")) {
                            if (mNumberUser == null || mNumberUser < 1) {
                                tvUserNumber.setVisibility(View.GONE);
                                imgUserNumber.setColorFilter(ContextCompat.getColor(mActivity, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                }
            }
            if (StringUtils.isEmpty(model.getOwnCardDto().getGiftCard())) {
                viewInforCard.setVisibility(View.GONE);
                llLabelCode.setVisibility(View.GONE);
                llContentCode.setVisibility(View.GONE);
            } else {
                if (model.getOwnCardDto().getGiftCard().equalsIgnoreCase("N")) {
                    viewInforCard.setVisibility(View.VISIBLE);
                    llLabelCode.setVisibility(View.VISIBLE);
                    llContentCode.setVisibility(View.VISIBLE);
                } else if (model.getOwnCardDto().getGiftCard().equalsIgnoreCase("Y")) {
                    viewInforCard.setVisibility(View.GONE);
                    llLabelCode.setVisibility(View.GONE);
                    llContentCode.setVisibility(View.GONE);
                } else {
                    llLabelCode.setVisibility(View.GONE);
                    llContentCode.setVisibility(View.GONE);
                    viewInforCard.setVisibility(View.GONE);
                }
            }
            clContent.setVisibility(View.VISIBLE);
            tvCardPackageNo.setText(model.getOwnCardDto().getCardNoDisplay());
            cardNoDisplay = model.getOwnCardDto().getCardNoDisplay();
            //            changeType(model.getOwnCardDto());
            initFragments(model, mainParentClass);
            if (model.getOwnCardDto().getFreeServices() != null) {
                adapter.setItems(model.getOwnCardDto().getFreeServices());
            }
            if (model.getOwnCardDto().getPromotionDtos() == null) {
                llPromotion.setVisibility(View.GONE);
                viewPromotion.setVisibility(View.GONE);
            } else {
                if (model.getOwnCardDto().getPromotionDtos().size() == 0) {
                    llPromotion.setVisibility(View.GONE);
                    viewPromotion.setVisibility(View.GONE);
                } else {
                    llPromotion.setVisibility(View.VISIBLE);
                    viewPromotion.setVisibility(View.VISIBLE);
                    CardPromotionAdapter promotionAdapter = new CardPromotionAdapter(mActivity, model.getOwnCardDto().getPromotionDtos());
                    rvPromotion.setAdapter(promotionAdapter);
                    rvPromotion.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
                }
            }

            tvCode.setText(model.getOwnCardDto().getTransactionCode());
            tvCodeProduct.setText(model.getOwnCardDto().getDefCardCode());
            tvTitleToolbar.setText(model.getOwnCardDto().getName());
            cardName = model.getOwnCardDto().getName();
            tvEffectDate.setText(model.getOwnCardDto().getPaymentTime());
            showStatus(model.getOwnCardDto().getStatus());
            if (model.getOwnCardDto().getRootService() != null) {
                if (model.getOwnCardDto().getRootService().getOwnAccountDtos().size() != 0) {
                    if (StringUtils.isEmpty(model.getOwnCardDto().getRootService().getUsageDescription())) {
                        tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(model.getOwnCardDto().getRootService().getOwnAccountDtos().get(0).getAllocatedUnit())));
                    } else {
                        tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(model.getOwnCardDto().getRootService().getUsageDescription())));
                    }
                    tvLimitRemaining.setText(StringUtils.convertHTMLToString("<b>" + model.getOwnCardDto().getRootService().getOwnAccountDtos().get(0).getAvailableUnit() + "</b>", mActivity));
                } else {
                    tvUseLimit.setText("");
                    tvLimitRemaining.setText("");
                }
                if (StringUtils.isEmpty(model.getOwnCardDto().getRootService().getExpirationDate())) {
                    tvLabelExpiration.setVisibility(View.GONE);
                    tvExpirationDate.setVisibility(View.GONE);
                } else {
                    tvLabelExpiration.setVisibility(View.VISIBLE);
                    tvExpirationDate.setVisibility(View.VISIBLE);
                    tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + model.getOwnCardDto().getRootService().getExpirationDate() + "</b>", mActivity));
                }
                cardCode = model.getOwnCardDto().getRootService().getCode();
                serviceName = model.getOwnCardDto().getRootService().getNamDefService();
                llCardInfor.setVisibility(View.VISIBLE);
                viewCardInfor.setVisibility(View.VISIBLE);
            } else {
                llCardInfor.setVisibility(View.GONE);
                viewCardInfor.setVisibility(View.GONE);
            }

        }
    }

    //    private void changeType(MyCardDetailsWaitResponseDefault.OwnCardDto ownCardDto) {
    //        if ((ownCardDto.getPromotionDtos() == null || ownCardDto.getPromotionDtos().size() == 0)
    //                && ownCardDto.getRootService() == null
    //                && (ownCardDto.getOwnServiceDtos() == null || ownCardDto.getOwnServiceDtos().size() == 0)) {
    //            params5.setScrollFlags(0);
    //        } else {
    //            if (ownCardDto.getOwnServiceDtos() == null || ownCardDto.getOwnServiceDtos().size() == 0) {
    //                if (ownCardDto.getPromotionDtos() == null || ownCardDto.getPromotionDtos().size() == 0) {
    //                    params4.setScrollFlags(0);
    //                } else {
    //                    params1.setScrollFlags(0);
    //                    params2.setScrollFlags(0);
    //                }
    //            }
    //        }
    //    }
}
