package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.fragment.direct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;


public class DirectFragment extends BaseDataFragment<DirectPresenter> implements DirectContract.View {
    private TextView tvContent;
    private String paymentGuideCash;
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_payment_direct;
    }

    @Override
    protected void initView() {
        tvContent = view.findViewById(R.id.tvContent);
    }

    @Override
    protected void initData() {
        mPresenter = new DirectPresenter(mActivity, this);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        paymentGuideCash = bundle.getString("paymentGuideCash");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initAction() {
        tvContent.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(paymentGuideCash).setImageGetter(new HtmlResImageGetter(mActivity.getApplicationContext()))));
        tvContent.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                if (url.startsWith("https://providerid.")) {
                    int providerID = Integer.parseInt(url.substring(19));
                    backToHome(0,providerID);
                }else if (url.startsWith("https://providernumber.")) {
                    String phoneNumber = url.substring(23);
                    PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                } else if (url.startsWith("https://phonenumber.")) {
                    String phoneNumber = url.substring(20);
                    PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                }
            }
        });
    }

    public void backToHome(int tabPosition,int providerID) {
        Intent intent = new Intent(mActivity, LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION,tabPosition);
        intent.putExtra(Constants.BundleConstants.PROVIDER_ID,providerID);
        startActivity(intent);
    }
}
