package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.listservice;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.CardDetailPackageFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet.MyCodeQr;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.adapter.CardDetailsInForAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycodev2.MainMyCodeV2Fragment;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

/**
 * Created by vannh.lvt on 17/06/2020
 */
public class ListServiceFragment extends BaseDataFragment<ListServicePresenter> implements ListServiceContract.View {
    private CardDetailsInForAdapter adapter;
    private RecyclerView rvContent;
    private String mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_list_service;
    }

    @Override
    protected void initView() {
        adapter = new CardDetailsInForAdapter(mActivity);
        rvContent = view.findViewById(R.id.rvContent);
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initData() {
        mPresenter = new ListServicePresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(mainParentClass)) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        //        String tvCardPackageName = getArguments().getString(Constants.BundleConstants.MY_CARD_INFORMATION_DISPLAY);
        MyCardDetailsWaitResponseDefault data = (MyCardDetailsWaitResponseDefault) bundle.getSerializable(Constants.BundleConstants.CARD_DETAILS);
        setDataService(data.getOwnCardDto().getOwnServiceDtos());
    }

    private void setDataService(List<MyCardDetailsWaitResponseDefault.OwnServiceDto> items) {
        if (items == null)
            return;
        adapter.setItems(items);
    }

    @Override
    protected void initAction() {
        adapter.setOnClickListener(new CardDetailsInForAdapter.OnClickListener() {
            @Override
            public void onClickItem(final MyCardDetailsWaitResponseDefault.OwnServiceDto item) {
                CardDetailPackageFragment cardDetailPackageFragment = new CardDetailPackageFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.SERVICE);
                bundle1.putString(Constants.BundleConstants.CODECARD, item.getCode());
                bundle1.putString(Constants.BundleConstants.SERVICE_NAME, item.getNamDefService());
                bundle1.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                cardDetailPackageFragment.setArguments(bundle1);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(cardDetailPackageFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(cardDetailPackageFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(cardDetailPackageFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(cardDetailPackageFragment, getParentFragment());
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCodeV2Fragment.getInstance().addFragment(cardDetailPackageFragment, getParentFragment());
            }

            @Override
            public void onClickQrCode(MyCardDetailsWaitResponseDefault.OwnServiceDto item) {
                MyCodeQr qrCodeFragment = new MyCodeQr();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Constants.BundleConstants.SEE_QR_CODE, item);
                qrCodeFragment.setArguments(bundle1);
                qrCodeFragment.show(mActivity.getSupportFragmentManager(), qrCodeFragment.getTag());
            }


        });
        adapter.setOnClickLogoListener(new CardDetailsInForAdapter.OnClickLogo() {
            @Override
            public void OnClickLogo(MyCardDetailsWaitResponseDefault.ProviderDto item) {
                ProviderFragment providerFragment = new ProviderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleConstants.PROVIDER_ID, item.getId());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                providerFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainBuyCardFragment:
                        MainBuyCardFragment.getInstance().addFragment(providerFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(providerFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainMyCodeFragment:
                        MainMyCodeV2Fragment.getInstance().addFragment(providerFragment, getParentFragment());
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(providerFragment, getParentFragment());
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
//                MainMyCardsFragment.getInstance().addFragment(providerFragment, getParentFragment());

            }
        });
    }
}
