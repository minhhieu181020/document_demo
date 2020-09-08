package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.notify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitRechargeResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.StringUtils;

/**
 * Created by vannh.lvt on 10/08/2020
 */
public class LCNotifyActivity extends BaseDataActivity<NotifyPresenter> implements NotifyContract.View {
    private ImageView imgClose;
    private ImageView imgStatus;
    private TextView tvStatus;
    private TextView tvAmount;
    private TextView tvOrderID;
    private TextView tvDescription;
    private ScrollView svDesc;
    private Button btnMyCard;

    private String cardNo;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_recharge_notify;
    }

    @Override
    protected void initView() {
        imgClose = findViewById(R.id.imgClose);
        TextView tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_common_notify));
        imgStatus = findViewById(R.id.imgStatus);
        tvStatus = findViewById(R.id.tvStatus);
        svDesc = findViewById(R.id.svDesc);
        tvAmount = findViewById(R.id.tvAmount);
        tvOrderID = findViewById(R.id.tvOrderID);
        tvDescription = findViewById(R.id.tvDescription);
        btnMyCard = findViewById(R.id.btnMyCard);


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter = new NotifyPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        String paymentType = bundle.getString("contentType");
        cardNo = bundle.getString("cardNo");
        PaymentWaitRechargeResponse dataPaymentWait = (PaymentWaitRechargeResponse) bundle.getSerializable("dataPaymentWaitRecharge");
        if (dataPaymentWait == null || StringUtils.isEmpty(paymentType) || StringUtils.isEmpty(cardNo) || StringUtils.isEmpty(dataPaymentWait.getAmount()) || StringUtils.isEmpty(dataPaymentWait.getTransId())) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            finish();
            return;
        }

        imgStatus.setBackgroundResource(R.mipmap.lifecardsdk_ic_wait);
        tvOrderID.setText(getString(R.string.lifecardsdk_buy_card_code_orders) + ": " + dataPaymentWait.getTransId());
        if (StringUtils.isEmpty(dataPaymentWait.getPaymentGuide())) {
            svDesc.setVisibility(View.GONE);
        } else {
            tvDescription.setText(StringUtils.convertHTMLToString(StringUtils.dotsString(dataPaymentWait.getPaymentGuide()), LCNotifyActivity.this));
        }
        tvAmount.setText(dataPaymentWait.getAmount());
        tvStatus.setText(getString(R.string.lifecardsdk_notify_payment_wait));

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
        super.onBackPressed();
        backToHome(0, "", "");
    }

    @Override
    protected void initAction() {

        imgClose.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                backToHome(0, "", "");
            }
        });

        btnMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome(1, cardNo, "A");
            }
        });

    }
}
