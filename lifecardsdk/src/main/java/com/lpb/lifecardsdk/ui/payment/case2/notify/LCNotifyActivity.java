package com.lpb.lifecardsdk.ui.payment.case2.notify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.bottomsheet.BranchFragment;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;


public class LCNotifyActivity extends BaseDataActivity<NotifyPresenter> implements NotifyContract.View {
    private ImageView imgClose;
    private TextView tvTitleToolbar;
    private TextView tvTotalMoney;
    private TextView tvOrderID;
    private Button btnContinue;
    private Button btnMyCard;
    private TextView tvContent;
    private LinearLayout llContent;
    private TextView tvPaymentExpire;
    private String cardNo = "";

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_activity_notify;
    }

    @Override
    protected void initView() {
        imgClose = findViewById(R.id.imgClose);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvOrderID = findViewById(R.id.tvOrderID);
        btnContinue = findViewById(R.id.btnContinue);
        btnMyCard = findViewById(R.id.btnMyCard);
        tvContent = findViewById(R.id.tvContent);
        llContent = findViewById(R.id.llContent);
        tvPaymentExpire = findViewById(R.id.tvPaymentExpire);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_notify));
    }

    @Override
    protected void initData() {
        mPresenter = new NotifyPresenter(this, this);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        String orderNo = bundle.getString(LCConfig.ORDER_NO);
        String productCode = bundle.getString(LCConfig.PRODUCT_CODE);
        int productPrice = bundle.getInt(LCConfig.PRODUCT_PRICE, Config.DEAULT_VALUE_INT);

        if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productCode) || productPrice == Config.DEAULT_VALUE_INT) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else {
            showLoading(true);
            mPresenter.getDataPayment(productPrice, productCode, LCConfig.getPhoneNumber(), orderNo);
        }

    }

    @Override
    protected void initAction() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome(0, "", "");
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * phần này ẩn theo design mới
                 */
                backToHome(0, "", "");
            }
        });
        btnMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome(1, cardNo, "W");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showContent(final PaymentWaitResponse data) {
        btnContinue.setVisibility(View.GONE);

        llContent.setVisibility(View.VISIBLE);
        btnMyCard.setVisibility(View.VISIBLE);
        tvTotalMoney.setText(data.getAmount());
        tvOrderID.setText(getString(R.string.lifecardsdk_buy_card_code_orders) + ": " + data.getOrderNo());
        cardNo = data.getCardNo();
//        if (!StringUtils.isEmpty(data.getExpirationDate()) && !StringUtils.isEmpty(data.getExpirationTime())) {
//            tvPaymentExpire.setText(getString(R.string.lifecardsdk_payment_time_expire, data.getExpirationTime(), data.getExpirationDate()));
//        } else {
//            if (StringUtils.isEmpty(data.getExpirationDate()) && StringUtils.isEmpty(data.getExpirationTime())) {
//                tvPaymentExpire.setVisibility(View.GONE);
//            } else {
//                if (StringUtils.isEmpty(data.getExpirationTime()))
//                    tvPaymentExpire.setText(getString(R.string.lifecardsdk_payment_time_expire_date, data.getExpirationDate()));
//                if (StringUtils.isEmpty(data.getExpirationDate()))
//                    tvPaymentExpire.setText(getString(R.string.lifecardsdk_payment_time_expire_time, data.getExpirationTime()));
//            }
//        }


        tvPaymentExpire.setVisibility(View.GONE);
        tvContent.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<ul>" + data.getPaymentGuide() + "</ul>").setImageGetter(new HtmlResImageGetter(this))));
        tvContent.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                if (url.startsWith("https://branches.")) {
                    if (data.getProviderIds() == null) {
                        showError(getString(R.string.lifecardsdk_realtime_error), "");
                        return;
                    }
                    if (data.getProviderIds().size() == 0) {
                        showError(getString(R.string.lifecardsdk_realtime_error), "");
                        return;
                    }
                    BranchFragment branchFragment = new BranchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BundleConstants.PROVIDER_ID, data.getProviderIds().get(0));
                    branchFragment.setArguments(bundle);
                    branchFragment.show(getSupportFragmentManager(), branchFragment.getTag());
                } else if (url.startsWith("https://providernumber.")) {
                    String phoneNumber = url.substring(23);
                    PresenterUtils.callPhoneNumber(LCNotifyActivity.this, phoneNumber);
                } else if (url.startsWith("https://phonenumber.")) {
                    String phoneNumber = url.substring(20);
                    PresenterUtils.callPhoneNumber(LCNotifyActivity.this, phoneNumber);
                }
            }
        });
    }

    public void backToHome(int tabPosition, String cardNo, String status) {
        Intent intent = new Intent(getApplicationContext(), LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION, tabPosition);
        intent.putExtra(Constants.BundleConstants.CARD_NO, cardNo);
        intent.putExtra(Constants.BundleConstants.STATUS, status);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        backToHome(0, "", "");
    }
}
