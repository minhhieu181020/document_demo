package com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.card_information;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.MainBuyCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.carddetails.fragment.card_information.adapter.CardInforAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.buycard.package_details.PackageDetailsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.MainMyCodeFragment;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

public class CardInforFragment extends BaseDataFragment<CardInforPresenter> implements CardInforContract.View {
    private CardInforAdapter adapter;
    private RecyclerView rvContent;
    private PackageDetailResponse data;
    private boolean isClicked;
    private String mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_buycard_information;
    }

    @Override
    protected void initView() {
        adapter = new CardInforAdapter(mActivity);
        rvContent = view.findViewById(R.id.rvContent);
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        if (StringUtils.isEmpty(mainParentClass)) {
            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }
        mPresenter = new CardInforPresenter(mActivity, this);
        data = (PackageDetailResponse) getArguments().getSerializable(Constants.BundleConstants.CARD_DETAILS);
        setDataPackage(data.getDefCardDto().getDefServiceDtos());
    }

    @Override
    protected void initAction() {
        adapter.setOnClickListener(new CardInforAdapter.OnClickListener() {
            @Override
            public void onClickItem(PackageDetailResponse.DefServiceDto item) {
                if (!isClicked) {
                    isClicked = true;
                    PackageDetailsFragment packageDetailsFragment = new PackageDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BundleConstants.PACKAGE_DETAILS, item);
                    bundle.putString(Constants.BundleConstants.TYPE, Constants.TypeDetails.SERVICE);
                    packageDetailsFragment.setArguments(bundle);
                    switch (mainParentClass) {
                        case Constants.ParentClass.MainBuyCardFragment:
                            MainBuyCardFragment.getInstance().addFragment(packageDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCardFragment:
                            MainMyCardsFragment.getInstance().addFragment(packageDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainMyCodeFragment:
                            MainMyCodeFragment.getInstance().addFragment(packageDetailsFragment, getParentFragment());
                            break;
                        case Constants.ParentClass.MainScanQrCodeFragment:
                            MainScanQrCodeFragment.getInstance().addFragment(packageDetailsFragment, getParentFragment());
                            break;
                        default:
                            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                            break;
                    }

                    startTimer();
                }

            }
        });
    }

    private void startTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isClicked = false;
            }
        }, Config.DELAY_CLICK);

    }

    @Override
    public void setDataPackage(List<PackageDetailResponse.DefServiceDto> items) {
        if (items == null)
            return;
        adapter.setItems(items);
    }

}
