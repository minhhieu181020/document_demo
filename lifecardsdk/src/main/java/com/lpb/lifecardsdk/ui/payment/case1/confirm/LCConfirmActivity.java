package com.lpb.lifecardsdk.ui.payment.case1.confirm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.bottomsheet.TermsFragment;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.ui.payment.case1.notification.fail.FailFragment;
import com.lpb.lifecardsdk.ui.payment.case1.notification.success.SuccessFragment;
import com.lpb.lifecardsdk.ui.payment.case2.payment.adapter.ProviderPageAdapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.HeightWrappingViewPager;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class LCConfirmActivity extends BaseDataActivity<ConfirmPresenter> implements ConfirmContract.View {
    private ImageView imgBack;
    private TextView tvOrderNo;
    private TextView tvCustomerName;
    private TextView tvPhoneNumber;
    private TextView tvProduceName;
    private TextView tvProductPrice, tvProvider, tvCardNo;
    private Button btnContinue, btnCancel;
    private ScrollView scContainer;
    private RelativeLayout rlToolbar;
    private FrameLayout flContainer;
    private CirclePageIndicator cpiViewPager;
    private HeightWrappingViewPager vpContent;
    private LinearLayout llProvider;
    private boolean isClicked;
    private String orderNo;
    private String productName;
    private String productCode;
    private String productTerms;
    private Integer productPrice;
    private boolean isBackPressed = true;
    private ProviderPageAdapter providerAdapter;
    private LinearLayout llBottom;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_confirm_case1;
    }

    @Override
    protected void initView() {
        imgBack = findViewById(R.id.imgBack);
        TextView tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        tvOrderNo = findViewById(R.id.tvOrderNo);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvProduceName = findViewById(R.id.tvProduceName);
        tvProductPrice = findViewById(R.id.tvTotalMoney);
        TextView tvCheckbox = findViewById(R.id.tvCheckbox);
        btnContinue = findViewById(R.id.btnContinue);
        btnCancel = findViewById(R.id.btnCancel);
        llProvider = findViewById(R.id.llProvider);
        llBottom = findViewById(R.id.llBottom);
        scContainer = findViewById(R.id.scContainer);
        rlToolbar = findViewById(R.id.rlToolbar);
        flContainer = findViewById(R.id.flContainer);
        tvProvider = findViewById(R.id.tvProvider);
        cpiViewPager = findViewById(R.id.cpiViewPager);
        vpContent = findViewById(R.id.vpContent);
        tvCardNo = findViewById(R.id.tvProductCode);
        cpiViewPager.setRadius(ScreenUtils.sdpToPixel(this, 4, 4));

        providerAdapter = new ProviderPageAdapter(new ArrayList<InitPaymentResponse.ProviderDTO>(), this);
        vpContent.setAdapter(providerAdapter);
        cpiViewPager.setViewPager(vpContent);

        tvTitleToolbar.setText(getString(R.string.lifecardsdk_confirm));

        SpannableString ss = new SpannableString(getString(R.string.lifecardsdk_buy_card_terms_description));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                if (!isClicked) {
                    isClicked = true;
                    TermsFragment termsFragment = new TermsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.TERMS, productTerms);
                    termsFragment.setArguments(bundle);
                    termsFragment.show(getSupportFragmentManager(), termsFragment.getTag());
                    startTimer();
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 50, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvCheckbox.setText(ss);
        tvCheckbox.setMovementMethod(LinkMovementMethod.getInstance());
        tvCheckbox.setHighlightColor(Color.TRANSPARENT);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter = new ConfirmPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String defCardCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);
        if (StringUtils.isEmpty(defCardCode)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
            if (PresenterUtils.isNetworkConnected(this)) {
                showLoading(true);
                mPresenter.getBill(LCConfig.getPhoneNumber(), LCConfig.getCustomerName(), defCardCode);
            } else {
                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
            }

        } else showError(getString(R.string.lifecardsdk_realtime_error), "");

    }

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);

    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    isClicked = true;
                    if (PresenterUtils.isNetworkConnected(LCConfirmActivity.this)) {
                        if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productName) || productPrice == Config.DEAULT_VALUE_INT || StringUtils.isEmpty(productCode)) {
                            showError(getString(R.string.lifecardsdk_realtime_error), "");
                        } else {
                            Intent i = new Intent("lifecard-payment");
                            i.setPackage(getApplicationContext().getPackageName());
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
                            bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
                            bundle.putInt(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
                            bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
                            i.putExtras(bundle);
                            startActivityForResult(i, Config.REQUEST_CODE_TO_APP);
                        }
                    } else {
                        showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                    }
                    startTimer();
                }
            }
        });

        btnCancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });
        providerAdapter.setOnClickLogoListener(new ProviderPageAdapter.OnClickListener() {
            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(LCConfirmActivity.this, phoneNumber);
            }

            @Override
            public void onClickEmail(String email) {
                try {
                    startActivity(PresenterUtils.openEmailApp(email));
                } catch (Exception ignored) {

                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setBill(InitPaymentResponse data) {
        if (data != null) {
            if (StringUtils.isEmpty(data.getOrderNo())
                    || StringUtils.isEmpty(data.getCustomerName())
                    || StringUtils.isEmpty(data.getCustomerMobilePhone())
                    || StringUtils.isEmpty(data.getDefCardName())
                    || StringUtils.isEmpty(data.getCardNo())
                    || StringUtils.isEmpty(data.getAmountVnd())
                    || data.getAmount() == null) {
                showError(getString(R.string.lifecardsdk_realtime_error), "");
                return;
            }
            orderNo = data.getOrderNo();
            String customerName = data.getCustomerName();
            productName = data.getDefCardName();
            productCode = data.getCardNo();
            productPrice = data.getAmount();
            String phoneNumber = data.getCustomerMobilePhone();
            productTerms = data.getUsageTerms();

            tvOrderNo.setText(orderNo);
            tvCustomerName.setText(customerName);
            tvProduceName.setText(productName);
            tvProductPrice.setText(data.getAmountVnd());
            tvPhoneNumber.setText(phoneNumber);
            tvCardNo.setText(getString(R.string.lifecardsdk_buycard_card_product_code) + productCode);

            if (data.getProviderDTOs() != null) {
                if (data.getProviderDTOs().size() > 0) {
                    if (data.getProviderDTOs().size() > 1) {
                        cpiViewPager.setVisibility(View.VISIBLE);
                    } else {
                        cpiViewPager.setVisibility(View.GONE);
                    }
                    providerAdapter.changeData(data.getProviderDTOs());
                    llProvider.setVisibility(View.VISIBLE);
                } else {
                    llProvider.setVisibility(View.GONE);
                    tvProvider.setVisibility(View.GONE);
                }
            } else {
                llProvider.setVisibility(View.GONE);
                tvProvider.setVisibility(View.GONE);
            }
            scContainer.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
        } else {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        }

    }

    public void backToHome(int tabPosition,String cardNo,String status) {
        Intent intent = new Intent(getApplicationContext(), LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION, tabPosition);
        intent.putExtra(Constants.BundleConstants.CARD_NO, cardNo);
        intent.putExtra(Constants.BundleConstants.STATUS, status);
        startActivity(intent);
    }

    public void backToHome(int tabPosition, int providerID) {
        Intent intent = new Intent(this, LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION, tabPosition);
        intent.putExtra(Constants.BundleConstants.PROVIDER_ID, providerID);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_CODE_TO_APP) {
            if (resultCode == LCConfig.SUCCESS) {
                assert data != null;
                Bundle bundle = data.getExtras();
                assert bundle != null;
                String totalAmount = bundle.getString(Constants.BundleConstants.TOTAL_AMOUNT);
                paymentSuccess(orderNo, totalAmount);
            } else if (resultCode == LCConfig.FAIL) {
                assert data != null;
                Bundle bundle = data.getExtras();
                assert bundle != null;
                String resultDesc = bundle.getString(Constants.BundleConstants.RESULT_DESC);
                paymentFail(orderNo, resultDesc);
            } else if (resultCode == LCConfig.CANCEL) {
                paymentFail(orderNo, getString(R.string.lifecardsdk_notify_unpaid));
            } else if (resultCode == LCConfig.BACK_TO_HOME) {
                backToHome(0,"","");
            }
        }
    }

    public void paymentSuccess(String orderNo, String totalAmount) {
        isBackPressed = false;
        flContainer.setVisibility(View.VISIBLE);
        rlToolbar.setVisibility(View.GONE);
        scContainer.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        Fragment fragment = new SuccessFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
        bundle.putString(Constants.BundleConstants.TOTAL_AMOUNT, totalAmount);
        bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment).commit();
    }

    public void paymentFail(String orderNo, String resultDesc) {
        isBackPressed = false;
        flContainer.setVisibility(View.VISIBLE);
        rlToolbar.setVisibility(View.GONE);
        scContainer.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        Fragment fragment = new FailFragment();
        Bundle bundle = new Bundle();


        bundle.putString(Constants.BundleConstants.RESULT_DESC, resultDesc);
        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
        bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS);
        bundle.putString(Constants.BundleConstants.PRODUCT_NAME, productName);
        bundle.putInt(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
        bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) super.onBackPressed();
        else backToHome(0,"","");
    }
}
