package com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.CardDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.CardDetailsWaitFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment.MyCardFragment;

public class MainMyCardsFragment extends BaseDataFragment<MainMyCardsPresenter> implements MainMyCardsContract.View {

    private static MainMyCardsFragment instance = null;
    private String status = "";
    private String cardNo = "";

    public static MainMyCardsFragment getInstance() {
        return instance;
    }

    @Override
    protected int getLayoutId() {
        instance = this;
        return R.layout.lifecardsdk_fragment_main_mycards;
    }

    @Override
    protected void initView() {

        Fragment fragment = new MyCardFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flContainer1, fragment).commit();


        Bundle bundle = this.getArguments();
        assert bundle != null;
        status = bundle.getString(Constants.BundleConstants.STATUS, "");
        cardNo = bundle.getString(Constants.BundleConstants.CARD_NO, "");
        if (!status.isEmpty() && !cardNo.isEmpty()) {
            if (status.equalsIgnoreCase(Constants.Status.A)) {
                CardDetailsFragment cardInforFragment = new CardDetailsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, cardNo);
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                cardInforFragment.setArguments(bundle1);
                addFragment(cardInforFragment, fragment);
            }else {
                CardDetailsWaitFragment cardInforFragment = new CardDetailsWaitFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, cardNo);
                cardInforFragment.setArguments(bundle1);
                addFragment(cardInforFragment, fragment);
            }
        } else {

        }
        Log.e("MY_CARD_INFORMATION", "initView: "+  status + " " + cardNo );
    }


    public void addFragment(Fragment newFragment, Fragment oldFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainer1, newFragment);
        fragmentTransaction.hide(oldFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainer1, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean backPressed() {
        boolean status = false;
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.flContainer1);
        if (fragment instanceof MyCardFragment) {
            status = true;
        }
        return status;
    }


    @Override
    protected void initData() {
        mPresenter = new MainMyCardsPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {

    }

}
