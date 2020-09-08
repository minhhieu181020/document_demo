package com.lpb.lifecardsdk.ui.payment.case1.notification.fail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentResultResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.payment.case1.confirm.LCConfirmActivity;
import com.lpb.lifecardsdk.ui.payment.case1.vipay.notification.NotifacationActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;


public class FailFragment extends BaseDataFragment<FailPresenter> implements FailContract.View {
    private ImageView imgClose;
    private TextView tvTitleToolbar;
    private TextView tvOrderID;
    private TextView tvDescription;
    private Button btnContinue;

    private String orderNo, productName, productCode, resultDesc;
    private Integer productPrice;
    private String NOTIFACATION_VIPAY_STATUS = "";
    private boolean isClicked;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_notification_fail;
    }

    @Override
    protected void initView() {
        imgClose = view.findViewById(R.id.imgClose);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvOrderID = view.findViewById(R.id.tvOrderID);
        tvDescription = view.findViewById(R.id.tvDescription);
        btnContinue = view.findViewById(R.id.btnContinue);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_notify));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter = new FailPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        orderNo = bundle.getString(Constants.BundleConstants.ORDER_NO);
        NOTIFACATION_VIPAY_STATUS = bundle.getString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS);

        productName = bundle.getString(Constants.BundleConstants.PRODUCT_NAME);
        productCode = bundle.getString(Constants.BundleConstants.PRODUCT_CODE);
        productPrice = bundle.getInt(Constants.BundleConstants.PRODUCT_PRICE, Config.DEAULT_VALUE_INT);
        resultDesc = bundle.getString(Constants.BundleConstants.RESULT_DESC);

        tvOrderID.setText(mActivity.getString(R.string.lifecardsdk_buy_card_code_orders) + ": " + orderNo);

        showLoading(true);
        mPresenter.getPaymentResult(LCConfig.getPhoneNumber(), orderNo, "1");
        try {
            if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                btnContinue.setVisibility(View.GONE);
            } else {
                btnContinue.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void setPaymentResult(PaymentResultResponse result) {
        if (!StringUtils.isEmpty(result.getTemplatePromotion())) {
            String description = String.format(result.getTemplatePromotion(), StringUtils.isEmpty(resultDesc) ? "" : (getString(R.string.common_reson) + resultDesc));
            tvDescription.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<ul>" + description + "</ul>")
                    .setImageGetter(new HtmlResImageGetter(mActivity.getApplicationContext()))));
            tvDescription.setMovementMethod(new TextViewLinkHandler() {
                @Override
                public void onLinkClick(String url) {
                    if (url.startsWith("https://providernumber.")) {
                        String phoneNumber = url.substring(23);
                        PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
                    } else if (url.startsWith("https://phonenumber.")) {
                        String phoneNumber = url.substring(20);
                        PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
                    }
                }
            });
        }
    }

    @Override
    public void setResultDesc() {
        if (!StringUtils.isEmpty(resultDesc))
            tvDescription.setText(resultDesc);
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
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                    ((NotifacationActivity) getActivity()).backToHome(0, "", "");
                } else {
                    ((LCConfirmActivity) getActivity()).backToHome(0, "", "");
                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    isClicked = true;
                    if (PresenterUtils.isNetworkConnected(mActivity)) {
                        if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productName) || productPrice == Config.DEAULT_VALUE_INT || StringUtils.isEmpty(productCode)) {
                            showError(getString(R.string.lifecardsdk_realtime_error), "");
                        } else {
                            Intent i = new Intent("lifecard-payment");
                            i.setPackage(mActivity.getApplicationContext().getPackageName());

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUEST_CODE_TO_APP) {
            if (resultCode == LCConfig.SUCCESS) {
                assert data != null;
                Bundle bundle = data.getExtras();
                assert bundle != null;
                String totalAmount = bundle.getString(Constants.BundleConstants.TOTAL_AMOUNT);
                ((LCConfirmActivity) getActivity()).paymentSuccess(orderNo, totalAmount);
            } else if (resultCode == LCConfig.FAIL) {
                assert data != null;
                Bundle bundle = data.getExtras();
                assert bundle != null;
                String resultDesc = bundle.getString(Constants.BundleConstants.RESULT_DESC);
                ((LCConfirmActivity) getActivity()).paymentFail(orderNo, resultDesc);
            } else if (resultCode == LCConfig.CANCEL) {
                ((LCConfirmActivity) getActivity()).paymentFail(orderNo, getString(R.string.lifecardsdk_notify_unpaid));
            } else if (resultCode == LCConfig.BACK_TO_HOME) {
                ((LCConfirmActivity) getActivity()).backToHome(0, "", "");
            }
        }
    }


}
