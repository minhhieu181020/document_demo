package com.lpb.lifecardsdk.ui.home.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lpb.lifecardsdk.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> lstFragments = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> items) {
        super(fm);
        lstFragments = items;
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragments.get(position);
    }

    @Override
    public int getCount() {
        return lstFragments != null ? lstFragments.size() : 0;
    }

    public void setItems(List<BaseFragment> items) {
        lstFragments = items;
    }
}
