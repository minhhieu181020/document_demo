package com.lpb.lifecardsdk.common.adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lpb.lifecardsdk.ui.base.BaseFragment;

import java.util.LinkedHashMap;

public class ViewPagerWithTabAdapter extends FragmentPagerAdapter {
    private LinkedHashMap<BaseFragment, String> lstFragments = new LinkedHashMap<>();

    public ViewPagerWithTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        int counter = 0;

        for (BaseFragment fragment : lstFragments.keySet()) {

            if (counter == position)
                return fragment;

            counter++;
        }

        return null;
    }

    @Override
    public int getCount() {
        return lstFragments != null ? lstFragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int counter = 0;

        for (BaseFragment fragment : lstFragments.keySet()) {

            if (counter == position)
                return lstFragments.get(fragment);

            counter++;
        }

        return null;
    }

    public void setItems(LinkedHashMap<BaseFragment, String> items) {
        lstFragments = items;
    }
}
