package com.lpb.lifecardsdk.ui.home.fragment.mycode;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.fragment.MyCodeFragment;

public class MainMyCodeFragment extends BaseDataFragment<MainMyCodePresenter> implements MainMyCodeContract.View {
    private static MainMyCodeFragment instance = null;

    public static MainMyCodeFragment getInstance() {
        return instance;
    }
    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_my_code;
    }

    @Override
    protected void initView() {
        Fragment fragment = new MyCodeFragment();
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
        if (fragment instanceof MyCodeFragment) {
                status = true;
        }
        return status;
    }
    @Override
    protected void initData() {
        mPresenter = new MainMyCodePresenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

}
