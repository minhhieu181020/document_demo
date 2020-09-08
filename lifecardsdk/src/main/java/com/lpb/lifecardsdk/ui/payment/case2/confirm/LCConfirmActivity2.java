package com.lpb.lifecardsdk.ui.payment.case2.confirm;

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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.bottomsheet.TermsFragment;
import com.lpb.lifecardsdk.ui.payment.case2.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

public class LCConfirmActivity2 extends BaseDataActivity<ConfirmPresenter> implements ConfirmContract.View {
    private ScrollView scContainer;
    private RelativeLayout rlToolbar;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private LinearLayout llContainer;
    private TextView tvOrderID;
    private TextView tvCustomerName;
    private TextView tvPhoneNumber;
    private TextView tvProduceName;
    private TextView tvProductCode;
    private TextView tvPaymentGateways;
    private TextView tvProductPrice;
    private TextView tvCheckbox;
    private Button btnContinue;

    private boolean isClicked;
    private String orderNo;
    private String productName;
    private String productCode;
    private String productTerms;
    private Integer productPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_confirm_case2;
    }

    @Override
    protected void initView() {
        rlToolbar = findViewById(R.id.rlToolbar);
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        llContainer = findViewById(R.id.llContainer);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvProduceName = findViewById(R.id.tvProduceName);
        tvProductCode = findViewById(R.id.tvProductCode);
        tvPaymentGateways = findViewById(R.id.tvPaymentGateways);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvCheckbox = findViewById(R.id.tvCheckbox);
        btnContinue = findViewById(R.id.btnContinue);
        scContainer = findViewById(R.id.scContainer);

        tvTitleToolbar.setText(getString(R.string.lifecardsdk_confirm));
    }

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


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    isClicked = true;
                    if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productName) || productPrice == Config.DEAULT_VALUE_INT || StringUtils.isEmpty(productCode)) {
                        showError(getString(R.string.lifecardsdk_realtime_error), "");
                    }else {
                        Intent intent = new Intent(LCConfirmActivity2.this, LCNotifyActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
                        bundle.putInt(Constants.BundleConstants.PRODUCT_PRICE, productPrice);
                        bundle.putString(Constants.BundleConstants.PRODUCT_CODE, productCode);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                    startTimer();
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

            tvOrderID.setText(orderNo);
            tvCustomerName.setText(customerName);
            tvProduceName.setText(productName);
            tvProductCode.setText(getString(R.string.lifecardsdk_buycard_card_product_code) + productCode);
            tvProductPrice.setText(data.getAmountVnd());
            tvPhoneNumber.setText(phoneNumber);

            //todo
            tvPaymentGateways.setText(getString(R.string.lifecardsdk_payment_direct_tranfer));
            scContainer.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.VISIBLE);
        } else {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
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
}
