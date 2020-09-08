package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse2;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentMethodResponse;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.BranchFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.fragment.direct.DirectFragment;
import com.lpb.lifecardsdk.ui.payment.case1.vipay.LCVipayActivity;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;

import java.util.LinkedHashMap;
import java.util.List;

public class LCPaymentGuideFragment extends BaseDataFragment<PaymentGuidePresenter> implements PaymentGuideContract.View {

    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private String orderNo;
    private ImageView imgBackgroundCard;
    private AutofitTextView tvNumberCard;
    private TextView tvTransCode;
    private TextView tvAmountPaymentDisplay;
    private Spinner SpPaymentMethod;
    private LinearLayout llTransferInstruction, llBtnPayment;
    private TextView tvContentTransferInstruction;
    private PaymentMethodResponse paymentMethodResponse;
    private Button btnPayment;
    private String message = "";
    private String productCode = "";
    private TextView tvContent;
    private TextView tvBankName;
    private TextView tvCreditAccount;
    private TextView tvBankBranch;
    private TextView tvAccountHolder;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_payment_guide_fragment;
    }

    @Override
    protected void initView() {

        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvContent = view.findViewById(R.id.tvContent);
        imgBackgroundCard = view.findViewById(R.id.imgBackgroundCard);
        tvNumberCard = view.findViewById(R.id.tvNumberCard);
        tvTransCode = view.findViewById(R.id.tvTransCode);
        tvAmountPaymentDisplay = view.findViewById(R.id.tvAmountPaymentDisplay);
        SpPaymentMethod = view.findViewById(R.id.SpPaymentMethod);
        llTransferInstruction = view.findViewById(R.id.llTransferInstruction);
        tvContentTransferInstruction = view.findViewById(R.id.tvContentTransferInstruction);
        llBtnPayment = view.findViewById(R.id.llBtnPayment);
        btnPayment = view.findViewById(R.id.btnPayment);
        tvBankName = view.findViewById(R.id.tvBankName);
        tvCreditAccount = view.findViewById(R.id.tvCreditAccount);
        tvBankBranch = view.findViewById(R.id.tvBankBranch);
        tvAccountHolder = view.findViewById(R.id.tvAccountHolder);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_guide));
    }

    @Override
    protected void initData() {
        mPresenter = new PaymentGuidePresenter(mActivity, this);
        orderNo = getArguments().getString(Constants.BundleConstants.ORDER_NO);
        if (StringUtils.isEmpty(orderNo)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else {
            showLoading(true);
            mPresenter.getPaymentGuide2(orderNo);
            mPresenter.callAPIMethod();
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        tvCreditAccount.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (!StringUtils.isEmpty(message)){
                    ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("content", message);
                    assert clipboard != null;
                    clipboard.setPrimaryClip(clip);
                    Exception.handleMessageSuccess(getActivity(), getString(R.string.lifecardsdk_payment_guide_has_copied));
                }

            }
        });
        SpPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PaymentMethodResponse.Datum data = (PaymentMethodResponse.Datum) parent.getSelectedItem();
                if (data.getCode().equalsIgnoreCase("TRANSFER")) {
                    if (data.getPaymentAccountDto()==null){
                        llTransferInstruction.setVisibility(View.GONE);
                        llBtnPayment.setVisibility(View.GONE);
                    }else {
                        message=data.getPaymentAccountDto().getCreditAccount();
                        tvBankName.setText(data.getPaymentAccountDto().getBankName());
                        tvCreditAccount.setText(data.getPaymentAccountDto().getCreditAccount());
                        tvBankBranch.setText(data.getPaymentAccountDto().getBankBranch());
                        tvAccountHolder.setText(data.getPaymentAccountDto().getAccountHolder());
                        llTransferInstruction.setVisibility(View.VISIBLE);
                        llBtnPayment.setVisibility(View.GONE);
                    }

                } else if (data.getCode().equalsIgnoreCase("VIPAY")) {
                    llBtnPayment.setVisibility(View.VISIBLE);
                    llTransferInstruction.setVisibility(View.GONE);
                } else {
                    llBtnPayment.setVisibility(View.GONE);
                    llTransferInstruction.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnPayment.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (StringUtils.isEmpty(orderNo)) {
                    showError(getString(R.string.lifecardsdk_realtime_error), "");
                } else {
                    Intent intent = new Intent(mActivity, LCVipayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BundleConstants.PRODUCT_CODE, orderNo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void setPaymentGuide2(final PaymentGuideResponse2 data) {
        GlideUtils.loadImageUrl(imgBackgroundCard, data.getOwnCardDto().getImage(), mActivity, Constants.PlaceHolderType.BACKGROUND_CARD);
        tvNumberCard.setText(data.getOwnCardDto().getCardNoDisplay());
        tvAmountPaymentDisplay.setText(data.getAmountPaymentDisplay());
        tvTransCode.setText(orderNo);
        tvContent.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<ul>" + data.getPaymentGuide2() + "</ul>").setImageGetter(new HtmlResImageGetter(mActivity))));

        if (data.getStyleDto() == null) {

        } else {
            TextViewUtils.setStyleTextView(tvNumberCard, mActivity, null, data.getStyleDto().getRgb());
        }
        tvNumberCard.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_32));
        tvNumberCard.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_16));
        tvContentTransferInstruction.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                if (url.startsWith("https://branches.")) {
                    ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("content", message);
                    assert clipboard != null;
                    clipboard.setPrimaryClip(clip);
                    Exception.handleMessageSuccess(getActivity(), getString(R.string.lifecardsdk_payment_guide_has_copied));
                } else {
                    Toast.makeText(mActivity, "Tiếp tục!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    branchFragment.show(getActivity().getSupportFragmentManager(), branchFragment.getTag());
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

    @Override
    public void setPaymentMethodResponse(final PaymentMethodResponse paymentMethodResponse) {
        List<PaymentMethodResponse.Datum> paymentMethodResponseData = paymentMethodResponse.getData();
        ArrayAdapter cardAdapter = new ArrayAdapter(mActivity, R.layout.lifecardsdk_item_spinner_payment_method, paymentMethodResponseData);
        SpPaymentMethod.setAdapter(cardAdapter);
        SpPaymentMethod.setSelection(0);
    }
}
