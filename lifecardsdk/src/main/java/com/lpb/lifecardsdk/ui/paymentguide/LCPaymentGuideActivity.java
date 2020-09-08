package com.lpb.lifecardsdk.ui.paymentguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentGuideResponse2;
import com.lpb.lifecardsdk.ui.base.BaseDataActivity;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.bottomsheet.BranchFragment;
import com.lpb.lifecardsdk.ui.payment.case2.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.ui.paymentguide.fragment.direct.DirectFragment;
import com.lpb.lifecardsdk.ui.paymentguide.fragment.transfer.TransferFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;

import java.util.LinkedHashMap;

public class LCPaymentGuideActivity extends BaseDataActivity<PaymentGuidePresenter> implements PaymentGuideContract.View {
    private TabLayout tabLayout;
    private ViewPager vpContainer;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private String orderNo;
    private LinearLayout llTabLayout;

    private TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_activity_payment_guide;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        vpContainer = findViewById(R.id.vpContainer);
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        llTabLayout = findViewById(R.id.llTabLayout);
        tvContent = findViewById(R.id.tvContent);

        tvTitleToolbar.setText(getString(R.string.lifecardsdk_payment_guide));
    }

    private void initFragments(PaymentGuideResponse data) {
        llTabLayout.setVisibility(View.VISIBLE);
        vpContainer.setVisibility(View.VISIBLE);
        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

        DirectFragment directFragment = new DirectFragment();
        Bundle bundleDirect = new Bundle();
        bundleDirect.putString("paymentGuideCash", data.getPaymentGuideCash());
        directFragment.setArguments(bundleDirect);
        lstFragments.put(directFragment, getString(R.string.lifecardsdk_payment_guide_direct));

//        TransferFragment transferFragment = new TransferFragment();
//
//        Bundle bundleTransfer = new Bundle();
//        bundleTransfer.putString("noteTransfer", data.getNoteTransfer());
//        bundleTransfer.putString("message", data.getMessage());
//        bundleTransfer.putString("transferInstruction", data.getTransferInstruction());
//        transferFragment.setArguments(bundleTransfer);
//
//        lstFragments.put(transferFragment, getString(R.string.lifecardsdk_payment_guide_transfer));
        llTabLayout.setVisibility(View.GONE);


        ViewPagerWithTabAdapter paymentGuideAdapter = new ViewPagerWithTabAdapter(getSupportFragmentManager());
        paymentGuideAdapter.setItems(lstFragments);
        vpContainer.setAdapter(paymentGuideAdapter);
        vpContainer.setOffscreenPageLimit(lstFragments.size());
        tabLayout.setupWithViewPager(vpContainer);
    }

    @Override
    protected void initData() {
        mPresenter = new PaymentGuidePresenter(this, this);
        orderNo = getIntent().getStringExtra(Constants.BundleConstants.ORDER_NO);
        if (StringUtils.isEmpty(orderNo)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
        } else {
            showLoading(true);
            mPresenter.getPaymentGuide2(orderNo);
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setPaymentGuide(PaymentGuideResponse data) {
        initFragments(data);
    }

    @Override
    public void setPaymentGuide2(final PaymentGuideResponse2 data) {
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
                    PresenterUtils.callPhoneNumber(LCPaymentGuideActivity.this,phoneNumber);
                } else if (url.startsWith("https://phonenumber.")) {
                    String phoneNumber = url.substring(20);
                    PresenterUtils.callPhoneNumber(LCPaymentGuideActivity.this,phoneNumber);
                }
            }
        });
    }
}
