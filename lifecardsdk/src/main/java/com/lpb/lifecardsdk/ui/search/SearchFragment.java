package com.lpb.lifecardsdk.ui.search;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.Card;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment.adapter.ViewAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment.adapter.ViewPagerAdapter;

import java.util.List;

public class SearchFragment extends BaseDataFragment<SearchPresenter> implements SearchContract.View {

    RecyclerView recyclerView;
    ViewPager viewPager;
    private ViewAdapter viewAdapter;
    List<Card> cardList;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_search;
    }

    @Override
    protected void initView() {

//      viewPager
        viewPager = view.findViewById(R.id.viewPager);

        addTabs(viewPager);
        ((TabLayout) view.findViewById(R.id.tabLayout)).setupWithViewPager(viewPager);
        viewAdapter = new ViewAdapter(mActivity);
        addFragment();

    }

//    private void initRecyclerview() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setAdapter(viewAdapter);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//        adapter.addFrag(new StillValidatedFragment(), getString(R.string.lifecardsdk_Still_validated));
//        adapter.addFrag(new ExpireFragment(), getString(R.string.lifecardsdk_Expire));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter = new SearchPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {


    }


    @Override
    public void addFragment() {
    }
}

