package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information.adapter.CardInforWaitAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.infopakage.CardDetailsWaitInforFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;

import java.util.List;

public class CardInforWaitFragment extends BaseDataFragment<CardInforWaitPresenter> implements CardInforWaitContact.View {
    private CardInforWaitAdapter adapter;
    private RecyclerView rvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_card_information;
    }

    @Override
    protected void initView() {
        adapter = new CardInforWaitAdapter(mActivity);
        rvContent = view.findViewById(R.id.rvContent);
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initData() {
        mPresenter = new CardInforWaitPresenter(mActivity, this);
        MyCardDetailsWaitResponseDefault data = (MyCardDetailsWaitResponseDefault) getArguments().getSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_INFOR_WAIT);
        data.getOwnCardDto().getId().toString();
        Log.e("CardInforWaitPresenter", "ID: " + data.getOwnCardDto().getId().toString());
        setDataPackage(data.getOwnCardDto().getOwnServiceDtos());
//        mPresenter.getCardDeatils(LCConfig.getPhoneNumber(), data.getId());

    }

    @Override
    protected void initAction() {
        adapter.setOnClickListener(new CardInforWaitAdapter.OnClickListener() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.OwnServiceDto item) {

                CardDetailsWaitInforFragment cardDetailsWaitFragment = new CardDetailsWaitInforFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.MY_CARD_INFORMATION, item.getCode());
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TYPE_DETAILS.PACKAGE);
                cardDetailsWaitFragment.setArguments(bundle1);
                MainMyCardsFragment.getInstance().addFragment(cardDetailsWaitFragment, getParentFragment());
            }
        });
        adapter.setOnClickLogoListener(new CardInforWaitAdapter.OnClickLogo() {
            @Override
            public void onClickItem(MyCardDetailsWaitResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                providerFragment.setArguments(bundle);
                MainMyCardsFragment.getInstance().addFragment(providerFragment, getParentFragment());
            }
        });
    }


    @Override
    public void setDataPackage(List<MyCardDetailsWaitResponseDefault.OwnServiceDto> items) {
        adapter.setItems(items);
    }
}
