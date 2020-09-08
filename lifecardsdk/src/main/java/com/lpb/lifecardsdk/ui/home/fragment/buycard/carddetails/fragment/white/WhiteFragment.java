package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.white;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;

public class WhiteFragment extends BaseDataFragment<WhitePresenter> implements WhiteContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_white_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new WhitePresenter(mActivity,this);
    }

    @Override
    protected void initAction() {

    }
}
