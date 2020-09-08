package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.ListMyCardCodeResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.mycardcode.MyCardCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.qrpersonal.QrPersonalFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;

import java.util.LinkedHashMap;


public class MyCodeV2Fragment extends BaseDataFragment<MyCodeV2Presenter> implements MyCodeV2Contract.ViewModel {

    private RelativeLayout rlToolbar;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private ImageView imgSpace;
    private LinearLayout llTabLayout;
    private TabLayout tabLayout;
    private ViewPager vpContainer1;
    private ListMyCardCodeResponseDefault listMyCardCodeResponseDefault;
    private String status;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_mycode_v2;
    }

    @Override
    protected void initView() {
        rlToolbar = view.findViewById(R.id.rlToolbar);
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        imgSpace = view.findViewById(R.id.imgSpace);
        llTabLayout = view.findViewById(R.id.llTabLayout);
        tabLayout = view.findViewById(R.id.tabLayout);
        vpContainer1 = view.findViewById(R.id.vpContainer1);
        tvTitleToolbar.setText(R.string.lifecardsdk_common_my_code);
    }
//test

    private void initFragments(ListMyCardCodeResponseDefault listMyCardCodeResponseDefault) {

        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

        switch (getContentType(listMyCardCodeResponseDefault)) {
            case Constants.TypeCardDetails.ALL:
                QrPersonalFragment qrPersonalFragment = new QrPersonalFragment();
                lstFragments.put(qrPersonalFragment, getString(R.string.lifecardsdk_common_my_code_personal));

                MyCardCodeFragment cardCodeFragment = new MyCardCodeFragment();
                Bundle bundleGuide2 = new Bundle();
                bundleGuide2.putSerializable("listMyCardCodeResponseDefault", listMyCardCodeResponseDefault);
                ;
                cardCodeFragment.setArguments(bundleGuide2);
                lstFragments.put(cardCodeFragment, getString(R.string.lifecardsdk_common_my_code_card));
                break;
            case Constants.TypeCardDetails.LIST_SERVICE:
                llTabLayout.setVisibility(View.GONE);
                QrPersonalFragment qrPersonalFragment2 = new QrPersonalFragment();
                lstFragments.put(qrPersonalFragment2, getString(R.string.lifecardsdk_common_my_code_personal));
                break;
        }

        ViewPagerWithTabAdapter orderStatusAdapter = new ViewPagerWithTabAdapter(getChildFragmentManager());
        orderStatusAdapter.setItems(lstFragments);
        vpContainer1.setAdapter(orderStatusAdapter);
        vpContainer1.setOffscreenPageLimit(lstFragments.size());
        tabLayout.setupWithViewPager(vpContainer1);

    }


    @Override
    protected void initData() {
        mPresenter = new MyCodeV2Presenter(mActivity, this);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            mPresenter.getDataListCard(LCConfig.getPhoneNumber(), 0L, 100L);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    private String getContentType(ListMyCardCodeResponseDefault listMyCardCodeResponseDefault1) {
        if (listMyCardCodeResponseDefault1 == null || listMyCardCodeResponseDefault1.getListActiveCardDto().size() == 0) {
            return Constants.TypeCardDetails.LIST_SERVICE;
        } else {
            return Constants.TypeCardDetails.ALL;
        }
    }

    @Override
    public void setDataListCard(ListMyCardCodeResponseDefault listCard) {
        initFragments(listCard);
    }
}