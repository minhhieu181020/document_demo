package com.lpb.lifecardsdk.ui.payment.case2.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.bottomsheet.TermsFragment;
import com.lpb.lifecardsdk.ui.payment.case2.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.ui.payment.case2.payment.adapter.ProviderPageAdapter;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.HeightWrappingViewPager;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class LCPaymentActivity extends BaseDataActivity<PaymentPresenter> implements PaymentContract.View {
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private LinearLayout llProvider;
    private TextView tvCustomerName;
    private TextView tvPhoneNumber;
    private TextView tvProduceName;
    private TextView tvOrderNo;
    private TextView tvTotalMoney, tvCardNo;

    private ScrollView svContainer;
    private LinearLayout llBottom;
    private TextView tvCheckbox, tvProvider;
    private Button btnCancel;
    private Button btnContinue;
    private boolean isClicked;
    private CirclePageIndicator cpiViewPager;
    private HeightWrappingViewPager vpContent;
    private String productCode, productTerms, orderNo;
    private int productPrice;
    private ProviderPageAdapter providerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_payment3;
    }

    @Override
    protected void initView() {
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvProduceName = findViewById(R.id.tvProduceName);
        llProvider = findViewById(R.id.llProvider);
        tvOrderNo = findViewById(R.id.tvOrderNo);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvProvider = findViewById(R.id.tvProvider);
        tvCheckbox = findViewById(R.id.tvCheckbox);
        btnCancel = findViewById(R.id.btnCancel);
        btnContinue = findViewById(R.id.btnContinue);
        cpiViewPager = findViewById(R.id.cpiViewPager);
        vpContent = findViewById(R.id.vpContent);
        svContainer = findViewById(R.id.svContainer);
        llBottom = findViewById(R.id.llBottom);
        tvCardNo = findViewById(R.id.tvProductCode);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_buy_card_infor_payment));
        cpiViewPager.setRadius(ScreenUtils.sdpToPixel(this, 4, 4));

        providerAdapter = new ProviderPageAdapter(new ArrayList<InitPaymentResponse.ProviderDTO>(), this);
        vpContent.setAdapter(providerAdapter);
        cpiViewPager.setViewPager(vpContent);
    }

    @Override
    protected void initData() {
        mPresenter = new PaymentPresenter(this, this);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        productCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);

        if (StringUtils.isEmpty(productCode)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        if (PresenterUtils.isNetworkConnected(this)) {
            showLoading(true);
            mPresenter.getDataOrderInfo(productCode);
        } else {
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataOrderInfo(InitPaymentResponse data) {
        svContainer.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.VISIBLE);

        tvProduceName.setText(data.getDefCardName());
        tvOrderNo.setText(data.getOrderNo());
        tvTotalMoney.setText(data.getAmountVnd());
        tvCustomerName.setText(data.getCustomerName());
        tvPhoneNumber.setText(data.getCustomerMobilePhone());

        productTerms = data.getUsageTerms();
        orderNo = data.getOrderNo();
        productCode = data.getCardNo();
        productPrice = data.getAmount();

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


        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (PresenterUtils.isNetworkConnected(LCPaymentActivity.this)) {
                    if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productCode)) {
                        showError(getString(R.string.lifecardsdk_realtime_error), "");
                    } else {

                        Intent intent = new Intent(LCPaymentActivity.this, LCNotifyActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
                        bundle.putInt(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
                        bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
                        intent.putExtras(bundle);

                        startActivity(intent);

                    }
                } else {
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }

            }
        });


        providerAdapter.setOnClickLogoListener(new ProviderPageAdapter.OnClickListener() {
            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(LCPaymentActivity.this, phoneNumber);
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
}

