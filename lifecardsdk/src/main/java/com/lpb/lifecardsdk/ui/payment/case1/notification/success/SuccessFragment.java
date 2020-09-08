package com.lpb.lifecardsdk.ui.payment.case1.notification.success;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
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


public class SuccessFragment extends BaseDataFragment<SuccessPresenter> implements SuccessContract.View {
    private ImageView imgClose;
    private TextView tvTitleToolbar;
    private TextView tvTime;
    private TextView tvTotalMoney;
    private TextView tvOrderID;
    private TextView tvDescription;
    private Button btnContinue;
    private Button btnMyCard;

    private String orderNo;
    private String cardNo = "";
    private String NOTIFACATION_VIPAY_STATUS = "";

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_notification_success;
    }


    @Override
    protected void initView() {
        imgClose = view.findViewById(R.id.imgClose);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTime = view.findViewById(R.id.tvTime);
        tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
        tvOrderID = view.findViewById(R.id.tvOrderID);
        tvDescription = view.findViewById(R.id.tvDescription);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnMyCard = view.findViewById(R.id.btnMyCard);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_notify));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter = new SuccessPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        orderNo = bundle.getString(Constants.BundleConstants.ORDER_NO);
        String totalAmount = bundle.getString(Constants.BundleConstants.TOTAL_AMOUNT);
        NOTIFACATION_VIPAY_STATUS = bundle.getString(Constants.BundleConstants.NOTIFACATION_VIPAY_STATUS);

        tvOrderID.setText(mActivity.getString(R.string.lifecardsdk_buy_card_code_orders) + ": " + orderNo);
        tvTotalMoney.setText(totalAmount);
        showLoading(true);
        mPresenter.getPaymentResult(LCConfig.getPhoneNumber(), orderNo, "0");
    }

    @Override
    protected void initAction() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                    ((NotifacationActivity) getActivity()).backToHome(0, "","");
                } else {
                    ((LCConfirmActivity) getActivity()).backToHome(0,"","");
                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                    ((NotifacationActivity) getActivity()).backToHome(0, "","");
                } else {
                    ((LCConfirmActivity) getActivity()).backToHome(0,"","");
                }
            }
        });
        btnMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                    ((NotifacationActivity) getActivity()).backToHome(1, cardNo,"A");
                } else {
                    ((LCConfirmActivity) getActivity()).backToHome(1,cardNo,"A");
                }
            }
        });
    }

    @Override
    public void setPaymentResult(PaymentResultResponse result) {
        if (!StringUtils.isEmpty(result.getEntryTimeDetail())) {
            tvTime.setText(result.getEntryTimeDetail());
        }
        tvTotalMoney.setText(result.getAmount());
        cardNo = result.getCardNo();
//        cardNo = "210000038252";
        if (!StringUtils.isEmpty(result.getTemplatePromotion())) {
            String resultDesc = "<ul>" + result.getTemplatePromotion() + "</ul>";
            tvDescription.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(resultDesc)
                    .setImageGetter(new HtmlResImageGetter(mActivity.getApplicationContext()))));
            tvDescription.setMovementMethod(new TextViewLinkHandler() {
                @Override
                public void onLinkClick(String url) {
                    if (url.startsWith("https://providerid.")) {
                        int providerID = Integer.parseInt(url.substring(19));
                        if (NOTIFACATION_VIPAY_STATUS.equalsIgnoreCase("NOTIFACATION_VIPAY_STATUS")) {
                            ((NotifacationActivity) getActivity()).backToHome(0, providerID);
                        } else {
                            ((LCConfirmActivity) getActivity()).backToHome(0, providerID);
                        }
                    } else if (url.startsWith("https://providernumber.")) {
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
}
