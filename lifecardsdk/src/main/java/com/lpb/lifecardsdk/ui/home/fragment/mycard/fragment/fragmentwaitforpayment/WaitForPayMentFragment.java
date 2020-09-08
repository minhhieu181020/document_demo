package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.CardDetailsWaitFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment.adapter.WaitForPayMentAdapter;

import java.util.List;

public class WaitForPayMentFragment extends BaseDataFragment<WaitForPayMentPresenter> implements WaitForPayMentContract.View {
    private RecyclerView rvWaitForPayment;
    private WaitForPayMentAdapter waitForPayMentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_wait_for_payment;
    }

    @Override
    protected void initView() {
        rvWaitForPayment = view.findViewById(R.id.rvWaitForPayment);
        waitForPayMentAdapter = new WaitForPayMentAdapter(mActivity);
        rvWaitForPayment.setAdapter(waitForPayMentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rvWaitForPayment.setLayoutManager(linearLayoutManager);
        waitForPayMentAdapter.setOnClickListener(new WaitForPayMentAdapter.OnClickListener() {
            @Override
            public void onClick(CardWaitForPayMent item) {
                Fragment fragment = new CardDetailsWaitFragment();
                FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer1, fragment).commit();
                mPresenter.onClickCardItem(item);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new WaitForPayMentPresenter(mActivity, this);

    }

    @Override
    protected void initAction() {

    }

    @Override
    public void setDataMyCard(List<CardWaitForPayMent> dataMyCard) {
        waitForPayMentAdapter.setItems(dataMyCard);
    }
}
