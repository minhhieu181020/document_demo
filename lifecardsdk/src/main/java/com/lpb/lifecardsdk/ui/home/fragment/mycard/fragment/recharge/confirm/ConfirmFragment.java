package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.confirm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.PaymentWaitRechargeRequest;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentWaitRechargeResponse;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderDTO;
import com.lpb.lifecardsdk.data.model.response.default_.RechargeResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.adapter.ProviderPageAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 24/07/2020
 */
public class ConfirmFragment extends BaseDataFragment<ConfirmPresenter> implements ConfirmContract.View {

    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private ImageView imgSpace;
    private LinearLayout llProvider;
    private CirclePageIndicator cpiViewPager;
    private ScrollView svContainer;
    private ViewPager vpContent;
    private TextView tvCardName;
    private TextView tvCardNumber;
    private TextView tvListedPrice;
    private TextView tvPricePayment;
    private TextView tvPaymentMethod;
    private Button btnConfirm;

    private ProviderPageAdapter providerPageAdapter;
    private List<ProviderDTO> providerDTOS;
    private Long amountDeposit, amountPayment, defRechargeID;
    private String cardNo, accountNo;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_recharge_confirm;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        imgSpace = view.findViewById(R.id.imgSpace);
        llProvider = view.findViewById(R.id.llProvider);
        cpiViewPager = view.findViewById(R.id.cpiViewPager);
        vpContent = view.findViewById(R.id.vpContent);
        tvCardName = view.findViewById(R.id.tvCardName);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        tvListedPrice = view.findViewById(R.id.tvListedPrice);
        tvPricePayment = view.findViewById(R.id.tvPricePayment);
        svContainer = view.findViewById(R.id.svContainer);
        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_recharge_confirm_payment));

        providerDTOS = new ArrayList<>();
        providerPageAdapter = new ProviderPageAdapter(providerDTOS, mActivity, R.layout.lifecardsdk_item_page_provider_3);
        cpiViewPager.setRadius(ScreenUtils.sdpToPixel(mActivity, 4, 4));
        vpContent.setAdapter(providerPageAdapter);
        cpiViewPager.setViewPager(vpContent);

    }

    @Override
    protected void initData() {
        mPresenter = new ConfirmPresenter(mActivity, this);
        try {
            Bundle bundle = getArguments();
            RechargeResponse rechargeData = (RechargeResponse) bundle.getSerializable(Constants.BundleConstants.RECHARGE_DATA);

            String priceDisplay = bundle.getString("priceDisplay");
            String listedPriceDisplay = bundle.getString("listedPriceDisplay");
            accountNo = bundle.getString("accountNo");

            amountDeposit = bundle.getLong("amountDeposit", -1);
            amountPayment = bundle.getLong("amountPayment", -1);
            defRechargeID = bundle.getLong("defRechargeID", -1);

            if (rechargeData == null || rechargeData.getOwnCardDto() == null || StringUtils.isEmpty(rechargeData.getOwnCardDto().getCardNo()) || amountDeposit == -1 || defRechargeID == -1 || amountPayment == -1 || StringUtils.isEmpty(listedPriceDisplay) || StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(priceDisplay)) {
                showError(getString(R.string.lifecardsdk_realtime_error), "");
                return;
            }

            cardNo = rechargeData.getOwnCardDto().getCardNo();

            if (rechargeData.getProviderDtos().size() > 0) {
                if (rechargeData.getProviderDtos().size() > 1) {
                    cpiViewPager.setVisibility(View.VISIBLE);
                } else {
                    cpiViewPager.setVisibility(View.GONE);
                }
                providerPageAdapter.changeData(rechargeData.getProviderDtos());
                llProvider.setVisibility(View.VISIBLE);
            } else {
                llProvider.setVisibility(View.GONE);
            }

            tvCardName.setText(rechargeData.getOwnCardDto().getName());
            tvCardNumber.setText(StringUtils.isEmpty(rechargeData.getOwnCardDto().getCardNoDisplay()) ? rechargeData.getOwnCardDto().getCardNo() : rechargeData.getOwnCardDto().getCardNoDisplay());
            tvListedPrice.setText(StringUtils.convertHTMLToString(StringUtils.boldString(listedPriceDisplay), mActivity));
            tvPricePayment.setText(priceDisplay);
            tvPaymentMethod.setText(getString(R.string.lifecardsdk_buy_card_payment_gateways_money));

            svContainer.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);

        } catch (java.lang.Exception e) {
            svContainer.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initAction() {
        btnConfirm.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (amountDeposit == -1 || amountPayment == -1 || StringUtils.isEmpty(cardNo)) {
                    showError(getString(R.string.lifecardsdk_realtime_error), "");
                    return;
                }
                PaymentWaitRechargeRequest request = new PaymentWaitRechargeRequest(amountDeposit, amountPayment, cardNo, accountNo, defRechargeID);
                mPresenter.getDataPaymentWait(StringUtils.convertObjectToBase64(request));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });


        providerPageAdapter.setOnClickLogoListener(new ProviderPageAdapter.OnClickListener() {
            @Override
            public void onClickPhone(String phoneNumber) {
                PresenterUtils.callPhoneNumber(mActivity, phoneNumber);
            }

            @Override
            public void onClickEmail(String email) {
                try {
                    startActivity(PresenterUtils.openEmailApp(email));
                } catch (java.lang.Exception ignored) {

                }
            }

            @Override
            public void onClickLogoProvider(Integer providerID) {

            }
        });
    }

    @Override
    public void setDataPaymentWait(PaymentWaitRechargeResponse data) {
        if (data == null) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        Intent intent = new Intent(mActivity, LCNotifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaymentWaitRecharge", data);
        bundle.putString("cardNo", cardNo);
        bundle.putString("contentType", Constants.ContentNotify.WAIT);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
