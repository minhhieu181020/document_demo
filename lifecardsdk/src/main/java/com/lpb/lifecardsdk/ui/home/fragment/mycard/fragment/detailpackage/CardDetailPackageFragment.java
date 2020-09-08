package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.adapter.ViewPagerWithTabAdapter;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.BaseFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice.HistoryServiceFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.infopakage.InfoPakageFragment;

import java.util.LinkedHashMap;

public class CardDetailPackageFragment extends BaseDataFragment<CardDetailPackageContract.Presenter> implements CardDetailPackageContract.View {
    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private String cardNo, serviceName, type,mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_card_detail_package;
    }

    @Override
    protected void initView() {
        viewPager = view.findViewById(R.id.vpContainer1);
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);

        ((TabLayout) view.findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);

    }

    private void addTabs(ViewPager viewPager) {
        //        Xem chi tiết gói
        InfoPakageFragment infoPakageFragment = new InfoPakageFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.BundleConstants.CODECARD, cardNo);
        bundle2.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);

        infoPakageFragment.setArguments(bundle2);

        //        Lịch sử giao dịch
        HistoryServiceFragment historyServiceFragment = new HistoryServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstants.CODECARD, cardNo);
        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCodeFragment);
        historyServiceFragment.setArguments(bundle);

        LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();
        if (type.equals(Constants.TypeDetails.CARD)) {
            lstFragments.put(infoPakageFragment, getString(R.string.lifecardsdk_info_card));
            lstFragments.put(historyServiceFragment, getString(R.string.lifecardsdk_history_card));
        } else if (type.equals(Constants.TypeDetails.SERVICE)) {
            lstFragments.put(infoPakageFragment, getString(R.string.lifecardsdk_info_pakage));
            lstFragments.put(historyServiceFragment, getString(R.string.lifecardsdk_history_service));
        }
        ViewPagerWithTabAdapter orderStatusAdapter = new ViewPagerWithTabAdapter(getChildFragmentManager());
        orderStatusAdapter.setItems(lstFragments);
        viewPager.setAdapter(orderStatusAdapter);
        viewPager.setOffscreenPageLimit(lstFragments.size());
    }

    @Override
    protected void initData() {
        mPresenter = new CardDetailPackagePresenter(mActivity, this);;
        Bundle bundle = getArguments();
        cardNo = bundle.getString(Constants.BundleConstants.CODECARD);
        serviceName =  bundle.getString(Constants.BundleConstants.SERVICE_NAME);
        mainParentClass = getArguments().getString(Constants.BundleConstants.MAIN_PARENT_CLASS);

        type = bundle.getString(Constants.BundleConstants.TYPE);
        tvTitleToolbar.setText(serviceName);
        addTabs(viewPager);
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }
}
