package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter.CardPromotionAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet.MyCodeQr;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.GuideFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information.CardInforWaitFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information.adapter.CardInforWaitAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.infopakage.CardDetailsWaitInforFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.LCPaymentGuideFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.paymentguide.LCPaymentGuideActivity;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

import java.util.LinkedHashMap;

public class CardDetailsWaitFragment extends BaseDataFragment<CardDetailsWaitContract.Presenter> implements CardDetailsWaitContract.View {

    private TabLayout tabLayout;
    private ViewPager vpContainer;
    private ImageView imgBack;
    private LinearLayout llSeeQR;
    private Button btnBuyCard;
    private TextView textSpan;
    private RelativeLayout rlToolbar;
    private ImageView imgBack2;
    private AppBarLayout appBarLayout;
    private ImageView imgBackground;
    private TextView tvPromotion;
    private LinearLayout llCardInfor, llDescription, llCardInforWait, llTabLayout;
    private TextView tvUseLimit, tvLimitRemaining, tvExpirationDate;
    private TextView tvPriceNew;
    private TextView tvPriceOrigin;
    private TextView tvDescription, tvLabelExpiration;
    private LinearLayout llExpire, llStatus;
    private TextView btnTest;
    private RecyclerView rvPromotion;
    private TextView cardId;
    private ConstraintLayout clUserNumber;
    private ImageView imgUserNumber;
    private TextView codeoder, tvTitleToolbar, tvGuide;
    private CoordinatorLayout llCardDetailWait;

    private RecyclerView rvFreeService;
    private View viewSpaceCardInfor;
    private View viewSpaceDesc;
    private AppBarLayout.LayoutParams params1, params2, params3, params4, params5;
    private MyCardDetailsWaitResponseDefault waitResponseDefault;
    private String CodeCard;
    private boolean isFirstSelected = true;
    private String getCardNo = "";
    private String mainParentClass;

    private CardInforWaitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_cardwait;
    }

    @Override
    protected void initView() {
        tabLayout = view.findViewById(R.id.tabLayout);
        vpContainer = view.findViewById(R.id.vpContainer);
        imgBack = view.findViewById(R.id.imgBack);
        llSeeQR = view.findViewById(R.id.llSeeQR);
        btnBuyCard = view.findViewById(R.id.btnBuyCard);
        textSpan = view.findViewById(R.id.textSpan);
        rlToolbar = view.findViewById(R.id.rlToolbar);
        clUserNumber = view.findViewById(R.id.clUserNumber);
        imgUserNumber = view.findViewById(R.id.imgUserNumber);
        imgUserNumber.setColorFilter(ContextCompat.getColor(mActivity, R.color.lifecardsdk_gray1), android.graphics.PorterDuff.Mode.SRC_IN);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        imgBackground = view.findViewById(R.id.imgBackground);
        tvPromotion = view.findViewById(R.id.tvPromotion);
        llCardInfor = view.findViewById(R.id.llCardInfor);
        tvPriceNew = view.findViewById(R.id.tvPriceNew);
        tvPriceOrigin = view.findViewById(R.id.tvPriceOrigin);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        llSeeQR = view.findViewById(R.id.llSeeQR);
        llExpire = view.findViewById(R.id.llExpire);
        rvPromotion = view.findViewById(R.id.rvPromotion);
        llDescription = view.findViewById(R.id.llDescription);
        btnTest = view.findViewById(R.id.btnTest);
        cardId = view.findViewById(R.id.tvProductCode);
        codeoder = view.findViewById(R.id.codeoder);
        llCardDetailWait = view.findViewById(R.id.llCardDetailWait);
        llCardInforWait = view.findViewById(R.id.llCardInforWait);
        llTabLayout = view.findViewById(R.id.llTabLayout);
        tvUseLimit = view.findViewById(R.id.tvUseLimit);
        viewSpaceCardInfor = view.findViewById(R.id.viewSpaceCardInfor);
        viewSpaceDesc = view.findViewById(R.id.viewSpaceDesc);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        llStatus = view.findViewById(R.id.llStatus);
        tvGuide = view.findViewById(R.id.tvGuide);
        rvFreeService = view.findViewById(R.id.rvFreeService);

        adapter = new CardInforWaitAdapter(mActivity);
        rvFreeService.setLayoutManager(new LinearLayoutManager(mActivity));
        rvFreeService.setNestedScrollingEnabled(false);
        rvFreeService.setAdapter(adapter);

        params1 = (AppBarLayout.LayoutParams) llCardInforWait.getLayoutParams();
        params2 = (AppBarLayout.LayoutParams) viewSpaceCardInfor.getLayoutParams();
        params3 = (AppBarLayout.LayoutParams) llDescription.getLayoutParams();
        params4 = (AppBarLayout.LayoutParams) viewSpaceDesc.getLayoutParams();
        params5 = (AppBarLayout.LayoutParams) llStatus.getLayoutParams();

        initFragments();
        SpannableString ss = new SpannableString(getString(R.string.lifecardsdk_payment_guide));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setText(ss);
    }


    private void initFragments() {

    }

    @Override
    protected void initData() {
        mPresenter = new CardDetailsWaitPresenter(mActivity, this);
        getCardNo = getArguments().getString(Constants.BundleConstants.MY_CARD_INFORMATION);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            mPresenter.getCardDeatils(LCConfig.getPhoneNumber(), getCardNo);
            showLoading(true);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
        SpannableString ss = new SpannableString(getString(R.string.lifecardsdk_payment_guide));
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

    private void initFragments(MyCardDetailsWaitResponseDefault data, String mainParentClass) {

        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

        switch (getContentType(data)) {
            case Constants.TypeCardDetails.ALL:

                CardInforWaitFragment cardInforWaitFragment = new CardInforWaitFragment();
                Bundle bundleAll = new Bundle();
                bundleAll.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_INFOR_WAIT, data);
                cardInforWaitFragment.setArguments(bundleAll);
                lstFragments.put(cardInforWaitFragment, getString(R.string.lifecardsdk_info_pakage));

                com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment guideFragment = new com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment();
                Bundle bundleGuide1 = new Bundle();
                bundleGuide1.putString(Constants.BundleConstants.TERMS, data.getOwnCardDto().getUsageTerms());
                bundleGuide1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                bundleGuide1.putString(Constants.BundleConstants.CARD_NO, data.getOwnCardDto().getCardNo());
                bundleGuide1.putString(Constants.BundleConstants.TYPE, Constants.TypeCardDetails.ALL);
                guideFragment.setArguments(bundleGuide1);
                lstFragments.put(guideFragment, getString(R.string.lifecardsdk_common_guide));

                break;
            case Constants.TypeCardDetails.GUIDE:
                llTabLayout.setVisibility(View.GONE);
                com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment guideFragment2 = new com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.guide.GuideFragment();
                Bundle bundleGuide2 = new Bundle();
                bundleGuide2.putString(Constants.BundleConstants.TERMS, data.getOwnCardDto().getUsageTerms());
                bundleGuide2.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                bundleGuide2.putString(Constants.BundleConstants.CARD_NO, data.getOwnCardDto().getCardNo());
                bundleGuide2.putString(Constants.BundleConstants.TYPE, Constants.TypeCardDetails.GUIDE);
                guideFragment2.setArguments(bundleGuide2);
                lstFragments.put(guideFragment2, getString(R.string.lifecardsdk_common_guide));
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
//    private void addTabs(MyCardDetailsWaitResponseDefault model) {
//        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();
//
//        switch (getContentType(model)) {
//            case Constants.TypeCardDetails.ALL:
//                CardInforWaitFragment cardInforWaitFragment = new CardInforWaitFragment();
//                Bundle bundleAll = new Bundle();
//                bundleAll.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_INFOR_WAIT, model);
//                cardInforWaitFragment.setArguments(bundleAll);
//                lstFragments.put(cardInforWaitFragment, getString(R.string.lifecardsdk_info_pakage));
//
//                TermsFragment1 termsFragment = new TermsFragment1();
//                Bundle bundle2 = new Bundle();
//                bundle2.putString(Constants.BundleConstants.TERMS, model.getOwnCardDto().getUsageTerms());
//                termsFragment.setArguments(bundle2);
//                lstFragments.put(termsFragment, getString(R.string.lifecardsdk_buycard_card_terms));
//
//                break;
//            case Constants.TypeCardDetails.LIST_SERVICE:
//                CardInforWaitFragment cardWaitFragment = new CardInforWaitFragment();
//                Bundle bundleListService = new Bundle();
//                bundleListService.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_INFOR_WAIT, model);
//                cardWaitFragment.setArguments(bundleListService);
//                lstFragments.put(cardWaitFragment, getString(R.string.lifecardsdk_info_pakage));
//
//                llTabLayout.setVisibility(View.GONE);
//                llPackageDetails.setVisibility(View.VISIBLE);
//                break;
//            case Constants.TypeCardDetails.TERMS:
//                llTabLayout.setVisibility(View.GONE);
//                llUsageTerms.setVisibility(View.VISIBLE);
//                tvUsageTerms.setText(StringUtils.convertHTMLToString(model.getOwnCardDto().getUsageTerms(), mActivity));
//                lstFragments.put(new WhiteFragment(), "");
//                break;
//            case Constants.TypeCardDetails.NONE:
//                params1.setScrollFlags(0);
//                params2.setScrollFlags(0);
//                params3.setScrollFlags(0);
//                params4.setScrollFlags(0);
//                params5.setScrollFlags(0);
//                llTabLayout.setVisibility(View.GONE);
//                lstFragments.put(new WhiteFragment(), "");
//                break;
//        }
//
//        ViewPagerWithTabAdapter orderStatusAdapter = new ViewPagerWithTabAdapter(getChildFragmentManager());
//        orderStatusAdapter.setItems(lstFragments);
//        vpContainer.setAdapter(orderStatusAdapter);
//        vpContainer.setOffscreenPageLimit(lstFragments.size());
//        tabLayout.setupWithViewPager(vpContainer);
//
//    }


//    private String getContentType(MyCardDetailsWaitResponseDefault data) {
//        if (data == null) return Constants.TypeCardDetails.NONE;
//        if (data.getOwnCardDto() == null) return Constants.TypeCardDetails.NONE;
//        if (data.getOwnCardDto().getOwnServiceDtos() == null || data.getOwnCardDto().getOwnServiceDtos().size() == 0) {
//            if (StringUtils.isEmpty(data.getOwnCardDto().getUsageTerms()))
//                return Constants.TypeCardDetails.NONE;
//            else return Constants.TypeCardDetails.TERMS;
//        } else {
//            if (StringUtils.isEmpty(data.getOwnCardDto().getUsageTerms()))
//                return Constants.TypeCardDetails.LIST_SERVICE;
//            else return Constants.TypeCardDetails.ALL;
//        }
//    }

    @Override
    protected void initAction() {
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
        adapter.setOnClickListener(new CardInforWaitAdapter.OnClickListener() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.OwnServiceDto item) {

                CardDetailsWaitInforFragment cardDetailsWaitFragment = new CardDetailsWaitInforFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, item.getCode());
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TYPE_DETAILS.PACKAGE);
                cardDetailsWaitFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsWaitFragment, CardDetailsWaitFragment.this);
            }
        });
        adapter.setOnClickLogoListener(new CardInforWaitAdapter.OnClickLogo() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                providerFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(providerFragment, CardDetailsWaitFragment.this);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        llCardInforWait.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                CardDetailsWaitInforFragment cardDetailsWaitFragment = new CardDetailsWaitInforFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TYPE_DETAILS.CARD);
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, CodeCard);
                cardDetailsWaitFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsWaitFragment, CardDetailsWaitFragment.this);
            }
        });
        tvGuide.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                LCPaymentGuideFragment guideFragment = new LCPaymentGuideFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BundleConstants.ORDER_NO, waitResponseDefault.getOwnCardDto().getTransactionCode());
                guideFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(guideFragment, CardDetailsWaitFragment.this);

//                Intent intent = new Intent(mActivity, LCPaymentGuideActivity.class);
//                intent.putExtra(Constants.BundleConstants.ORDER_NO, waitResponseDefault.getOwnCardDto().getTransactionCode());
//                startActivity(intent);
            }
        });
        btnBuyCard.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
            }
        });
        clUserNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exception.handleMessageRequestFailure(mActivity, getString(R.string.lifecardsdk_user_manager_cant_add_user_case_wait_payment), "");
            }
        });
    }

    @Override
    public void getDataCard(final MyCardDetailsWaitResponseDefault model) {
        clUserNumber.setVisibility(View.VISIBLE);
        waitResponseDefault = model;
        GlideUtils.loadImageUrl(imgBackground, model.getOwnCardDto().getImage(), mActivity, Constants.PlaceHolderType.BACKGROUND_CARD);
        cardId.setText(mActivity.getString(R.string.lifecardsdk_buycard_card_product_code) + model.getOwnCardDto().getDefCardCode());
        tvPriceNew.setText(model.getOwnCardDto().getPrice().toString());
        tvPriceOrigin.setText(model.getOwnCardDto().getListedPrice());
        tvPriceOrigin.setPaintFlags(tvPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (Config.SHOW_LISTED_PRICE) {
            tvPriceOrigin.setVisibility(View.VISIBLE);
        } else {
            tvPriceOrigin.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(model.getOwnCardDto().getDescBrief())) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setText(model.getOwnCardDto().getDescBrief());
        }


        if (model.getOwnCardDto().getPromotionDtos() == null) {
            llDescription.setVisibility(View.GONE);
            viewSpaceDesc.setVisibility(View.GONE);
        } else {
            if (model.getOwnCardDto().getPromotionDtos().size() == 0) {
                llDescription.setVisibility(View.GONE);
                viewSpaceDesc.setVisibility(View.GONE);
            } else {
                llDescription.setVisibility(View.VISIBLE);
                viewSpaceDesc.setVisibility(View.VISIBLE);
                CardPromotionAdapter promotionAdapter = new CardPromotionAdapter(mActivity, model.getOwnCardDto().getPromotionDtos());
                rvPromotion.setAdapter(promotionAdapter);
                rvPromotion.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
            }

        }
        if (StringUtils.isEmpty(model.getOwnCardDto().getPaymentExpirationDate())) {
            btnTest.setText("");
        } else {
            btnTest.setText(model.getOwnCardDto().getPaymentExpirationDate());
        }
        if (model.getOwnCardDto().getRootService() != null) {
            if (model.getOwnCardDto().getRootService().getOwnAccountDtos().size() != 0) {
                if (StringUtils.isEmpty(model.getOwnCardDto().getRootService().getUsageDescription())) {
                    tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(model.getOwnCardDto().getRootService().getOwnAccountDtos().get(0).getAllocatedUnit())));
                } else {
                    tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(model.getOwnCardDto().getRootService().getUsageDescription())));
                }
            } else {
                tvUseLimit.setText("");
            }
            if (StringUtils.isEmpty(model.getOwnCardDto().getRootService().getExpiryDate())) {
                tvExpirationDate.setText("");
                tvExpirationDate.setVisibility(View.GONE);
                tvLabelExpiration.setVisibility(View.GONE);
            } else {
                tvExpirationDate.setVisibility(View.VISIBLE);
                tvLabelExpiration.setVisibility(View.VISIBLE);
                tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + model.getOwnCardDto().getRootService().getExpiryDate() + "</b>", mActivity));

            }
            CodeCard = model.getOwnCardDto().getRootService().getCode();
            llCardInforWait.setVisibility(View.VISIBLE);
            viewSpaceCardInfor.setVisibility(View.VISIBLE);
        }

        codeoder.setText(model.getOwnCardDto().getTransactionCode());
        tvTitleToolbar.setText(model.getOwnCardDto().getName());
        initFragments(model, mainParentClass);
        if (model.getOwnCardDto() != null && model.getOwnCardDto().getFreeServices() != null && model.getOwnCardDto().getFreeServices().size() > 0) {
            adapter.setItems(model.getOwnCardDto().getFreeServices());
        } else {
            rvFreeService.setVisibility(View.GONE);
        }
//        addTabs(model);
        Log.e("setview", "onResponse: " + model.getOwnCardDto().toString());
        llSeeQR.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                MyCodeQr qrCodeFragment = new MyCodeQr();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.SEE_QR_CODE_W, model);
                qrCodeFragment.setArguments(bundle1);
                qrCodeFragment.show(mActivity.getSupportFragmentManager(), qrCodeFragment.getTag());

            }
        });
        tvPromotion.setText(model.getOwnCardDto().getDiscount());
        getCardNo = model.getOwnCardDto().getCardNo();
        llCardDetailWait.setVisibility(View.VISIBLE);

        hideLoading();
    }
}


