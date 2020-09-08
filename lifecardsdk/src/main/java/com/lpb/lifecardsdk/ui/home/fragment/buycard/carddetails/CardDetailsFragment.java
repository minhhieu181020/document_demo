//package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.TextPaint;
//import android.text.method.LinkMovementMethod;
//import android.text.style.ClickableSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.lpb.lifecardsdk.R;
//import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
//import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
//import com.lpb.lifecardsdk.constant.Config;
//import com.lpb.lifecardsdk.constant.Constants;
//import com.lpb.lifecardsdk.constant.LCConfig;
//import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
//import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
//import com.lpb.lifecardsdk.ui.base.BaseFragment;
//import com.lpb.lifecardsdk.ui.bottomsheet.BottomSheetPayMethodAPI;
//import com.lpb.lifecardsdk.ui.bottomsheet.SeeQrCodeFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.card_information.CardInforFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.terms.TermsFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.white.WhiteFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.buycard.package_details.PackageDetailsFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.GuideFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
//import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
//import com.lpb.lifecardsdk.ui.payment.case1.confirm.LCConfirmActivity;
//import com.lpb.lifecardsdk.ui.payment.case1.vipay.LCVipayActivity;
//import com.lpb.lifecardsdk.ui.payment.case2.payment.LCPaymentActivity;
//import com.lpb.lifecardsdk.util.GlideUtils;
//import com.lpb.lifecardsdk.util.PresenterUtils;
//import com.lpb.lifecardsdk.util.StringUtils;
//
//import java.util.LinkedHashMap;
//
//public class CardDetailsFragment extends BaseDataFragment<CardDetailsPresenter> implements CardDetailsContract.View, BottomSheetPayMethodAPI.BottomSheetListener {
//    private ImageView imgBack;
//    private ImageView imgBackground;
//    private TextView tvPromotion;
//    private TextView tvProductCode;
//    private TextView tvPriceNew;
//    private TextView tvPriceOrigin, tvTitleToolbar;
//    private LinearLayout llSeeQR;
//    private TabLayout tabLayout;
//    private ViewPager vpContainer;
//    private View viewSpaceDesc, viewSpaceInforCard;
//    private Button btnBuyCard;
//    private CoordinatorLayout clContainer;
//    private AppBarLayout appBarLayout;
//    private RecyclerView rvPromotion;
//    private TextView tvUseLimit,tvLabelExpiration;
//    private TextView tvExpirationDate, tvUsageTerms, tvDescription, tvGuide;
//    private LinearLayout llPromotion, llDesc, llInforCard, llTabLayout, llUsageTerms, llPackageDetails;
//    private PackageDetailResponse.DefCardDto packageDto;
//    private String productCode, productName, productPrice;
//    private String mainParentClass;
//    private boolean isClicked, isFirstSelected = true;
//    private AppBarLayout.LayoutParams params1, params2, params3, params4;
//    private String getDefCardCode = "";
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.lifecardsdk_fragment_buycard_card_details;
//    }
//
//    @Override
//    protected void initView() {
//
//        clContainer = view.findViewById(R.id.clContainer);
//        imgBack = view.findViewById(R.id.imgBack);
//        imgBackground = view.findViewById(R.id.imgBackground);
//        tvPromotion = view.findViewById(R.id.tvPromotion);
//        tvProductCode = view.findViewById(R.id.tvProductCode);
//        tvPriceNew = view.findViewById(R.id.tvPriceNew);
//        tvPriceOrigin = view.findViewById(R.id.tvPriceOrigin);
//        llSeeQR = view.findViewById(R.id.llSeeQR);
//        tabLayout = view.findViewById(R.id.tabLayout);
//        vpContainer = view.findViewById(R.id.vpContainer);
//        btnBuyCard = view.findViewById(R.id.btnBuyCard);
//        llPromotion = view.findViewById(R.id.llPromotion);
//        llDesc = view.findViewById(R.id.llDesc);
//        llTabLayout = view.findViewById(R.id.llTabLayout);
//        rvPromotion = view.findViewById(R.id.rvPromotion);
//        viewSpaceDesc = view.findViewById(R.id.viewSpaceDesc);
//        llInforCard = view.findViewById(R.id.llInforCard);
//        llUsageTerms = view.findViewById(R.id.llUsageTerms);
//        viewSpaceInforCard = view.findViewById(R.id.viewSpaceInforCard);
//        appBarLayout = view.findViewById(R.id.appBarLayout);
//        tvUseLimit = view.findViewById(R.id.tvUseLimit);
//        llPackageDetails = view.findViewById(R.id.llPackageDetails);
//        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
//        tvDescription = view.findViewById(R.id.tvDescription);
//        tvUsageTerms = view.findViewById(R.id.tvUsageTerms);
//        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
//        tvGuide = view.findViewById(R.id.tvGuide);
//
//        params1 = (AppBarLayout.LayoutParams) llInforCard.getLayoutParams();
//        params2 = (AppBarLayout.LayoutParams) viewSpaceInforCard.getLayoutParams();
//        params3 = (AppBarLayout.LayoutParams) llDesc.getLayoutParams();
//        params4 = (AppBarLayout.LayoutParams) viewSpaceDesc.getLayoutParams();
//
//        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
//    }
//
//    private void initFragments(PackageDetailResponse data, String mainParentClass) {
//
//        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();
//
//        switch (getContentType(data)) {
//            case Constants.TypeCardDetails.ALL:
//
//                CardInforFragment cardInforFragment = new CardInforFragment();
//                Bundle bundleCardInfor = new Bundle();
//                bundleCardInfor.putSerializable(Constants.BundleConstants.CARD_DETAILS, data);
//                bundleCardInfor.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
//                cardInforFragment.setArguments(bundleCardInfor);
//                lstFragments.put(cardInforFragment, getString(R.string.lifecardsdk_buy_card_package_details));
//
//                TermsFragment termsFragment = new TermsFragment();
//                Bundle bundleTerms = new Bundle();
//                bundleTerms.putString(Constants.BundleConstants.TERMS, data.getDefCardDto().getUsageTerms());
//                termsFragment.setArguments(bundleTerms);
//                lstFragments.put(termsFragment, getString(R.string.lifecardsdk_buycard_card_terms));
//
//                break;
//            case Constants.TypeCardDetails.LIST_SERVICE:
//
//                CardInforFragment inforFragment = new CardInforFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constants.BundleConstants.CARD_DETAILS, data);
//                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
//                inforFragment.setArguments(bundle);
//                lstFragments.put(inforFragment, getString(R.string.lifecardsdk_buycard_card_infomation));
//                llTabLayout.setVisibility(View.GONE);
//                llPackageDetails.setVisibility(View.VISIBLE);
//
//                break;
//            case Constants.TypeCardDetails.TERMS:
//
//                llTabLayout.setVisibility(View.GONE);
//                llUsageTerms.setVisibility(View.VISIBLE);
//                tvUsageTerms.setText(StringUtils.convertHTMLToString(data.getDefCardDto().getUsageTerms(), mActivity));
//                lstFragments.put(new WhiteFragment(), "");
//
//                break;
//            case Constants.TypeCardDetails.NONE:
//
//                params1.setScrollFlags(0);
//                params2.setScrollFlags(0);
//                params3.setScrollFlags(0);
//                params4.setScrollFlags(0);
//                llTabLayout.setVisibility(View.GONE);
//                lstFragments.put(new WhiteFragment(), "");
//
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
//
//    private String getContentType(PackageDetailResponse data) {
//        if (data == null) return Constants.TypeCardDetails.NONE;
//        if (data.getDefCardDto() == null) return Constants.TypeCardDetails.NONE;
//        if (data.getDefCardDto().getDefServiceDtos() == null || data.getDefCardDto().getDefServiceDtos().size() == 0) {
//            if (StringUtils.isEmpty(data.getDefCardDto().getUsageTerms()))
//                return Constants.TypeCardDetails.NONE;
//            else return Constants.TypeCardDetails.TERMS;
//        } else {
//            if (StringUtils.isEmpty(data.getDefCardDto().getUsageTerms()))
//                return Constants.TypeCardDetails.LIST_SERVICE;
//            else return Constants.TypeCardDetails.ALL;
//        }
//    }
//
//    @Override
//    protected void initData() {
//        Bundle bundle = this.getArguments();
//        assert bundle != null;
//        productCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);
//        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
//        if (StringUtils.isEmpty(mainParentClass) || StringUtils.isEmpty(productCode)) {
//            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
//            return;
//        }
//        mPresenter = new CardDetailsPresenter(mActivity, this);
//        if (PresenterUtils.isNetworkConnected(mActivity)) {
//            showLoading(true);
//            mPresenter.getDataCardInformation(productCode);
//        } else {
//            showError(mActivity.getString(R.string.lifecardsdk_cant_connect_internet), "");
//        }
//        SpannableString ss = new SpannableString(getString(R.string.lifecardsdk_common_guide));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(@NonNull View textView) {
//
//            }
//
//            @Override
//            public void updateDrawState(@NonNull TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setFakeBoldText(true);
//                ds.setUnderlineText(true);
//            }
//        };
//        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        tvGuide.setText(ss);
//        tvGuide.setMovementMethod(LinkMovementMethod.getInstance());
//        tvGuide.setHighlightColor(Color.TRANSPARENT);
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void showContentAndInitFragment(PackageDetailResponse data) {
//        initFragments(data, mainParentClass);
//
//        packageDto = data.getDefCardDto();
//
//        productName = packageDto.getName();
//        productCode = packageDto.getCode();
//        productPrice = packageDto.getPrice();
//
//        clContainer.setVisibility(View.VISIBLE);
//        btnBuyCard.setVisibility(View.VISIBLE);
//        tvTitleToolbar.setText(productName);
//
//        tvPriceNew.setText(packageDto.getPrice());
//        if (packageDto.getPriceNumber().equals(packageDto.getListedPriceNumber())) {
//            tvPriceOrigin.setText("");
//        } else {
//            tvPriceOrigin.setText(packageDto.getListedPrice());
//            tvPriceOrigin.setPaintFlags(tvPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//
//        if (packageDto.getPriceNumber() == 0 && packageDto.getListedPriceNumber() == 0) {
//            tvPriceNew.setVisibility(View.GONE);
//            tvPriceOrigin.setVisibility(View.GONE);
//        } else {
//            tvPriceNew.setVisibility(View.VISIBLE);
//            tvPriceOrigin.setVisibility(View.VISIBLE);
//        }
//
//        tvProductCode.setText(mActivity.getString(R.string.lifecardsdk_buycard_card_product_code) + productCode);
//
//        if (StringUtils.isEmpty(data.getDefCardDto().getDescBrief())) {
//            tvDescription.setVisibility(View.GONE);
//        } else {
//            tvDescription.setText(data.getDefCardDto().getDescBrief());
//        }
//
//        if (packageDto.getRootService() == null) {
//            llInforCard.setVisibility(View.GONE);
//            viewSpaceInforCard.setVisibility(View.GONE);
//        } else {
//            llInforCard.setVisibility(View.VISIBLE);
//            viewSpaceInforCard.setVisibility(View.VISIBLE);
//            if (StringUtils.isEmpty(packageDto.getRootService().getExpiryDate())){
//                tvLabelExpiration.setVisibility(View.GONE);
//                tvExpirationDate.setVisibility(View.GONE);
//            }else {
//                tvLabelExpiration.setVisibility(View.VISIBLE);
//                tvExpirationDate.setVisibility(View.VISIBLE);
//                tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + packageDto.getRootService().getExpiryDate() + "</b>", mActivity));
//            }
//
//            if (!StringUtils.isEmpty(packageDto.getRootService().getUsageDesciption())) {
//                tvUseLimit.setText(StringUtils.convertHTMLToString(packageDto.getRootService().getUsageDesciption(), mActivity));
//            } else {
//                tvUseLimit.setText(StringUtils.convertHTMLToString("<b>" + packageDto.getRootService().getDefServiceUnitDtos().get(0).getDisplayLimitUnit() + "</b>", mActivity));
//            }
//
//            llInforCard.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickCardInfor(packageDto);
//                }
//            });
//        }
//        if (packageDto.getPromotionDtos() == null) {
//            llDesc.setVisibility(View.GONE);
//            viewSpaceDesc.setVisibility(View.GONE);
//        } else {
//            if (packageDto.getPromotionDtos().size() == 0) {
//                llDesc.setVisibility(View.GONE);
//                viewSpaceDesc.setVisibility(View.GONE);
//            } else {
//                llDesc.setVisibility(View.VISIBLE);
//                viewSpaceDesc.setVisibility(View.VISIBLE);
//                PromotionAdapter promotionAdapter = new
//                        PromotionAdapter(mActivity, packageDto.getPromotionDtos());
//                rvPromotion.setAdapter(promotionAdapter);
//                rvPromotion.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
//            }
//        }
//        getDefCardCode = data.getDefCardDto().getCode();
//        GlideUtils.loadImageUrl(imgBackground, packageDto.getImage(), mActivity, Constants.PlaceHolderType.BACKGROUND_CARD);
//
//        if (packageDto.getDiscountValueNumber() == 0) {
//            llPromotion.setVisibility(View.GONE);
//        } else {
//            tvPromotion.setText(packageDto.getDiscountValue());
//            llPromotion.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void onClickCardInfor(PackageDetailResponse.DefCardDto data) {
//        if (!isClicked) {
//            isClicked = true;
//            PackageDetailsFragment packageDetailsFragment = new PackageDetailsFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.CARD);
//            bundle.putSerializable(Constants.BundleConstants.CARD_DETAIL_, data);
//            packageDetailsFragment.setArguments(bundle);
//            switch (mainParentClass) {
//                case Constants.ParentClass.MainBuyCardFragment:
//                    MainBuyCardFragment.getInstance().addFragment(packageDetailsFragment, CardDetailsFragment.this);
//                    break;
//                case Constants.ParentClass.MainMyCardFragment:
//                    MainMyCardsFragment.getInstance().addFragment(packageDetailsFragment, CardDetailsFragment.this);
//                    break;
//                case Constants.ParentClass.MainMyCodeFragment:
//                    MainMyCodeFragment.getInstance().addFragment(packageDetailsFragment, CardDetailsFragment.this);
//                    break;
//                case Constants.ParentClass.MainScanQrCodeFragment:
//                    MainScanQrCodeFragment.getInstance().addFragment(packageDetailsFragment, CardDetailsFragment.this);
//                    break;
//                default:
//                    showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
//                    break;
//            }
//
//            startTimer();
//        }
//    }
//
//    @Override
//    protected void initAction() {
////        if (mainParentClass.equalsIgnoreCase(Constants.ParentClass.MainScanQrCodeFragment)) {
////            MainScanQrCodeFragment.getInstance().addFragment(new ScanQRFragment(), CardDetailsFragment.this);
////            mActivity.onBackPressed();
////        }
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mActivity.onBackPressed();
//            }
//        });
//
//        llSeeQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isClicked) {
//                    isClicked = true;
//                    SeeQrCodeFragment qrCodeFragment = new SeeQrCodeFragment();
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putSerializable(Constants.BundleConstants.SEE_QR_CODE, packageDto);
//                    qrCodeFragment.setArguments(bundle1);
//                    qrCodeFragment.show(getChildFragmentManager(), qrCodeFragment.getTag());
//                    startTimer();
//                }
//            }
//        });
//        tvGuide.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                GuideFragment guideFragment = new GuideFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
//                bundle.putString(Constants.BundleConstants.DEFCARDCODE, getDefCardCode);
//                guideFragment.setArguments(bundle);
//                MainBuyCardFragment.getInstance().addFragment(guideFragment, CardDetailsFragment.this);
//            }
//        });
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (isFirstSelected) {
//                    isFirstSelected = false;
//                } else {
//                    appBarLayout.setActivated(true);
//                    appBarLayout.setExpanded(false, true);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                if (isFirstSelected) {
//                    isFirstSelected = false;
//                } else {
//                    appBarLayout.setActivated(true);
//                    appBarLayout.setExpanded(false, true);
//                }
//            }
//        });
//        llUsageTerms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appBarLayout.setActivated(true);
//                appBarLayout.setExpanded(false, true);
//            }
//        });
//        llPackageDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appBarLayout.setActivated(true);
//                appBarLayout.setExpanded(false, true);
//            }
//        });
//        btnBuyCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isClicked) {
//                    isClicked = true;
//                    startTimer();
//
//                    //tích hợp agara
////                    if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
////                        if (PresenterUtils.isNetworkConnected(mActivity)) {
////                            BottomSheetPayMethodAPI bottomSheetPayMethod = new BottomSheetPayMethodAPI();
////                            bottomSheetPayMethod.setmListener(CardDetailsFragment.this);
////                            bottomSheetPayMethod.show(getChildFragmentManager(), bottomSheetPayMethod.getTag());
////                        } else {
////                            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
////                        }
////
////                    } else {
////
////                        //todo
////                        // dừng phần không có thông tin khách hàng
////
////                        showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
////
////
//////                        Intent intent = new Intent(mActivity, CustomerInforActivity.class);
//////
//////                        Bundle bundle = new Bundle();
//////                        bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//////                        bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//////                        bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//////                        intent.putExtras(bundle);
//////
//////                        startActivity(intent);
////                    }
//
//
//                    // tích hợp ví việt
////                    if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
////                        Intent intent = new Intent(mActivity, LCConfirmActivity.class);
////
////                        Bundle bundle = new Bundle();
////                        bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
////                        intent.putExtras(bundle);
////
////                        startActivity(intent);
////
////                    } else {
////                        //todo
////                        // dừng phần không có thông tin khách hàng
////
////
////                        showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
////
//////                            Intent intent = new Intent(mActivity, LCCustomerInforActivity.class);
//////
//////                            Bundle bundle = new Bundle();
//////                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//////                            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//////                            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//////                            intent.putExtras(bundle);
//////
//////                            startActivity(intent);
////                    }
//
////                    // test
//                    if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
//
//                        if (LCConfig.getChannelCode().equalsIgnoreCase("VIVIET_APP")) {
//                            Intent intent = new Intent(mActivity, LCConfirmActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        } else if (LCConfig.getChannelCode().equalsIgnoreCase("MLTX_APP")) {
//                            Intent intent = new Intent(mActivity, LCConfirmActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        } else if (LCConfig.getChannelCode().equalsIgnoreCase("HL_APP")) {
//                            Intent intent = new Intent(mActivity, LCConfirmActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        } else if (LCConfig.getChannelCode().equalsIgnoreCase("CARGO_APP")) {
//                            if (PresenterUtils.isNetworkConnected(mActivity)) {
//                                BottomSheetPayMethodAPI bottomSheetPayMethod = new BottomSheetPayMethodAPI();
//                                bottomSheetPayMethod.setmListener(CardDetailsFragment.this);
//                                bottomSheetPayMethod.show(getChildFragmentManager(), bottomSheetPayMethod.getTag());
//                            } else {
//                                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
//                            }
//                        }
//
//                    } else {
//                        if (LCConfig.getChannelCode().equalsIgnoreCase("VIVIET_APP")) {
//
//                            //todo
//                            // dừng phần không có thông tin khách hàng
//
//
//                            showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
//
////                            Intent intent = new Intent(mActivity, LCCustomerInforActivity.class);
////
////                            Bundle bundle = new Bundle();
////                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
////                            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
////                            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
////                            intent.putExtras(bundle);
////
////                            startActivity(intent);
//                        } else if (LCConfig.getChannelCode().equalsIgnoreCase("CARGO_APP")) {
//
//                            //todo
//                            // dừng phần không có thông tin khách hàng
//
//
//                            showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
//
////                            Intent intent = new Intent(mActivity, CustomerInforActivity.class);
////
////                            Bundle bundle = new Bundle();
////                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
////                            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
////                            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
////                            intent.putExtras(bundle);
////
////                            startActivity(intent);
//                        }
//                        else if (LCConfig.getChannelCode().equalsIgnoreCase("MLTX_APP")) {
//                            showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
//                        }
//                        else if (LCConfig.getChannelCode().equalsIgnoreCase("HL_APP")) {
//                            showError(getString(R.string.lifecardsdk_cant_fint_cust_infor), "");
//                        }
//                    }
//
//
//                }
//            }
//        });
//    }
//
//    private void startTimer() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                isClicked = false;
//            }
//        }, Config.DELAY_CLICK);
//
//    }
//
//    @Override
//    public void getMethodPayment(String s) {
////        TIền Mặt
//        if (s.equalsIgnoreCase(Constants.TypePaymentMethod.TIENMAT)) {
//            Intent intent = new Intent(mActivity, LCPaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//            intent.putExtras(bundle);
//            startActivity(intent);
////            ViPay
//        } else if (s.equalsIgnoreCase(Constants.TypePaymentMethod.VIPAY)) {
//            Intent intent = new Intent(mActivity, LCVipayActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//            intent.putExtras(bundle);
//            startActivity(intent);
////            Sacombank
//        } else if (s.equalsIgnoreCase(Constants.TypePaymentMethod.SACOMBANK)) {
//            Intent intent = new Intent(mActivity, LCPaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//            intent.putExtras(bundle);
//            startActivity(intent);
////            THE QUOC TE
//        } else if (s.equalsIgnoreCase(Constants.TypePaymentMethod.THEQUOCTE)) {
//            Intent intent = new Intent(mActivity, LCPaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//            intent.putExtras(bundle);
//            startActivity(intent);
////            THE NOI DIA
//        } else if (s.equalsIgnoreCase(Constants.TypePaymentMethod.THENOIDIA)) {
//            Intent intent = new Intent(mActivity, LCPaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
//            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
//            bundle.putString(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//    }
//}
