package com.lpb.lifecardsdk.ui.payment.case1.vipay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.ResultVipayResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.payment.case1.vipay.notification.NotifacationActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

public class LCVipayActivity extends BaseDataActivity<VipayPresenter> implements VipayContract.View {
    private WebView wvViPay;
    String url = "";
    Integer a = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_vipay;
    }

    @Override
    protected void initView() {
        wvViPay = findViewById(R.id.wvViPay);
    }

    @Override
    protected void initData() {
        mPresenter = new VipayPresenter(this, this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String defCardCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);
        if (StringUtils.isEmpty(defCardCode)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else if (!StringUtils.isEmpty(LCConfig.getCustomerName()) && !StringUtils.isEmpty(LCConfig.getPhoneNumber())) {
            if (PresenterUtils.isNetworkConnected(this)) {
                showLoading(true);
                mPresenter.getUrlViPay(LCConfig.getCustomerName(), LCConfig.getPhoneNumber(), defCardCode);
            } else {
                showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
            }

        } else {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            finish();
        }

    }

    @Override
    protected void initAction() {

        wvViPay.setWebViewClient(new
                                         WebViewClient() {
                                             @Override
                                             public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                                 super.onReceivedError(view, request, error);
                                                 a++;
                                                 if (a < 5) {
                                                     wvViPay.loadUrl(url);
                                                 } else {
                                                     showError(getString(R.string.lifecardsdk_realtime_error), "");
                                                     finish();
                                                 }
                                             }

                                             @Override
                                             public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                                 super.onPageStarted(view, url, favicon);
                                                 Log.e("URL", "initAction: '" + url);
//                                                 Toast.makeText(LCVipayActivity.this, url, Toast.LENGTH_SHORT).show();
                                                 if (url.startsWith("http://lifecard.lvt.vn/return?merchant") || url.startsWith("http://lifecard.lvt.vn/cancel?")) {
                                                     mPresenter.getResultUrlViPay(url);
                                                     finish();
                                                 }
                                                 if (url.startsWith("https://sandbox.viviet.vn/vipay_v2/expired")) {
                                                     finish();
                                                 }
                                             }
                                         });

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void LoadUrlViPay(String url) {
        a = 0;
        this.url = url;
        wvViPay.getSettings().setJavaScriptEnabled(true);
        wvViPay.getSettings().setUseWideViewPort(true);
        wvViPay.loadUrl(url);
    }

    @Override
    public void getStatus(ResultVipayResponse response, boolean a) {
        if (a) {
            Intent intent = new Intent(LCVipayActivity.this, NotifacationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, response.getOrderStatus());
            bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_ODERNO, response.getOrderNo());
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LCVipayActivity.this, NotifacationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS, response.getOrderStatus());
            bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_ODERNO, response.getOrderNo());
            bundle.putString(Constants.BundleConstants.NOTIFACATION_VIPAY_PAYMENT, Constants.BundleConstants.NOTIFACATION_VIPAY_CODE);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wvViPay.canGoBack()) {
                        wvViPay.goBack();
                    } else {
//                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
    }
}

