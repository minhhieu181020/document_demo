package com.lpb.lifecardsdk.ui.payment.case1.vipay.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.ui.payment.case1.notification.fail.FailFragment;
import com.lpb.lifecardsdk.ui.payment.case1.notification.success.SuccessFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

public class NotifacationActivity extends BaseDataActivity<NotifacationPresenter> implements NotifacationContract.View {
    private static NotifacationActivity instance = null;
    private String statusPayment = "";

    public static NotifacationActivity getInstance() {
        return instance;
    }

    private FrameLayout flContainer;

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_activity_notifacation;
    }

    @Override
    protected void initView() {
        flContainer = findViewById(R.id.flContainer);
    }

    @Override
    protected void initData() {
        mPresenter = new NotifacationPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String status = bundle.getString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS);
        String orderNo = bundle.getString(Constants.BundleConstants.NOTIFACATION_VIPAY_ODERNO);
        statusPayment = bundle.getString(Constants.BundleConstants.NOTIFACATION_VIPAY_PAYMENT);
        try {
            if (statusPayment.equalsIgnoreCase(Constants.BundleConstants.NOTIFACATION_VIPAY_CODE)) {
                paymentFail(orderNo);
            }
        } catch (Exception e) {

        }
        if (StringUtils.isEmpty(status)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
            if (PresenterUtils.isNetworkConnected(this)) {
                if (status.equalsIgnoreCase("0")) {
                    paymentSuccess(orderNo);
                } else {
                    paymentFail(orderNo);
                }
            } else {
                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
            }

        } else showError(getString(R.string.lifecardsdk_realtime_error), "");

    }

    @Override
    protected void initAction() {

    }

    @Override
    public void paymentSuccess(String orderNo) {
        Fragment fragment = new SuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
        bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, "NOTIFACATION_VIPAY_STATUS");
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment).commit();
    }

    @Override
    public void paymentFail(String orderNo) {
        Fragment fragment = new FailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.ORDER_NO, orderNo);
        bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, "NOTIFACATION_VIPAY_STATUS");
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment).commit();
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
}

