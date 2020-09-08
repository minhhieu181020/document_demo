package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.terms;

import android.os.Bundle;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.StringUtils;

public class TermsFragment extends BaseDataFragment<TermsPresenter> implements TermsContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_buycard_terms;
    }

    private TextView tvTerms;

    @Override
    protected void initView() {
        tvTerms = view.findViewById(R.id.tvTerms);
    }

    @Override
    protected void initData() {
        mPresenter = new TermsPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        String terms = bundle.getString(Constants.BundleConstants.TERMS);
        tvTerms.setText(StringUtils.convertHTMLToString(terms,mActivity.getApplicationContext()));
    }

    @Override
    protected void initAction() {

    }
}
