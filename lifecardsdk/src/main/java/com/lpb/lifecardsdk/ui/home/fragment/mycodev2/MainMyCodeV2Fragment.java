package com.lpb.lifecardsdk.ui.home.fragment.mycodev2;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.fragment.MyCodeV2Fragment;

public class MainMyCodeV2Fragment extends BaseDataFragment<MainMyCodeV2Presenter> implements MainMyCodeV2Contract.View {
    private static MainMyCodeV2Fragment instance = null;

    public static MainMyCodeV2Fragment getInstance() {
        return instance;
    }
    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_my_code_v2;
    }

    @Override
    protected void initView() {
        Fragment fragment = new MyCodeV2Fragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flContainermycode, fragment).commit();


    }
    public void addFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainermycode, newFragment);
        fragmentTransaction.hide(oldFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean backPressed() {
        boolean status = false;
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flContainermycode);
        if (fragment instanceof MyCodeV2Fragment) {
                status = true;
        }
        return status;
    }
    @Override
    protected void initData() {
        mPresenter = new MainMyCodeV2Presenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

}
