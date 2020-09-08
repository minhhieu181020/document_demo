package com.lpb.lifecardsdk.ui.home.fragment.buycard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.DefCardFragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;

public class  MainBuyCardFragment extends BaseDataFragment<MainBuyCardPresenter> implements MainBuyCardContract.View {
    private static MainBuyCardFragment instance = null;

    public static MainBuyCardFragment getInstance() {
        return instance;
    }

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_buycard;
    }

    @Override
    protected void initView() {
        Fragment fragment = new DefCardFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flContainer, fragment).commit();


        Bundle bundle = this.getArguments();
        assert bundle != null;
        boolean isShowScreenProvider  = bundle.getBoolean(Constants.BundleConstants.IS_SHOW_SCREEN_PROVIDER);
        if (isShowScreenProvider) {
            ProviderFragment providerFragment = new ProviderFragment();
            Bundle b = new Bundle();
            b.putInt(Constants.BundleConstants.PROVIDER_ID, bundle.getInt(Constants.BundleConstants.PRODUCT_ID));
            b.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainBuyCardFragment);
            providerFragment.setArguments(b);
            addFragment(providerFragment, fragment);
        }
    }

    @Override
    protected void initData() {
        mPresenter = new MainBuyCardPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

    public void addFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainer, newFragment);
        fragmentTransaction.hide(oldFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean backPressed(boolean animate) {
        boolean status = false;
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flContainer);
        if (fragment instanceof DefCardFragment) {
            if (((DefCardFragment) fragment).backPressed(animate))
                status = true;
        }
        return status;
    }
}
