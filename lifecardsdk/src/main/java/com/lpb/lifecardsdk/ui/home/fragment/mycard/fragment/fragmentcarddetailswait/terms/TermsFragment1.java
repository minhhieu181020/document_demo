package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.terms;

import android.os.Bundle;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.StringUtils;

public class TermsFragment1 extends BaseDataFragment<TermsPresenter1> implements TermsContact1.View {
    private TextView tvTerms;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_terms;
    }

    @Override
    protected void initView() {
        tvTerms = view.findViewById(R.id.tvTerms);


    }

    @Override
    protected void initData() {
        Bundle bundle = this.getArguments();
        String terms = bundle.getString(Constants.BundleConstants.TERMS);
        tvTerms.setText(StringUtils.convertHTMLToString(terms,mActivity.getApplicationContext()));
    }

    @Override
    protected void initAction() {

    }
}
