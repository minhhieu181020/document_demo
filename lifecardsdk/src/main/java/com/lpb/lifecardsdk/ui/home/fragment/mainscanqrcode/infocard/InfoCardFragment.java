package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListServiceInfoResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard.adapter.CardServiceAdapter;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;

import java.util.List;

public class InfoCardFragment extends BaseDataFragment<InfoCardPresenter> implements InfoCardContract.View {
    private ImageView imgBackgroundCard;
    private TextView tvNumberCard;
    private TextView tvCustomerName;
    private RecyclerView rvContent;
    private CardServiceAdapter cardServiceAdapter;
    private List<ListServiceInfoResponse.OwnServiceDto> serviceItemList;
    private ImageView imgBack;
    private LinearLayout llconten1;
    private TextView tvTitleToolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_inforcard;
    }

    @Override
    protected void initView() {
        imgBackgroundCard = view.findViewById(R.id.imgBackgroundCard);
        tvNumberCard = view.findViewById(R.id.tvNumberCard);
        tvCustomerName = view.findViewById(R.id.tvCustomerName);
        rvContent = view.findViewById(R.id.rvContent);
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        llconten1 = view.findViewById(R.id.llconten1);

        cardServiceAdapter = new CardServiceAdapter(mActivity);
        rvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        rvContent.setAdapter(cardServiceAdapter);
        rvContent.setNestedScrollingEnabled(false);
        tvTitleToolbar.setText(R.string.lifecardsdk_info_card);
    }

    @Override
    protected void initData() {
        mPresenter = new InfoCardPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        showLoading(true);
        String getCardNo = bundle.getString("getCardNo");
//        Log.e("ListServiceInfoResponse", "onResponse: " + bundle.getString("getCardNo"));
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            mPresenter.getdata(getCardNo);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setDataCard(List<ListServiceInfoResponse.OwnServiceDto> data) {
        cardServiceAdapter.setItems(data);
    }

    @Override
    public void setDataCard(ListServiceInfoResponse data) {
        GlideUtils.loadImageUrl(imgBackgroundCard,data.getOwnCardDto().getImage(),mActivity, Constants.PlaceHolderType.BACKGROUND_CARD);
        Log.e("ListServiceInfoResponse", "onResponse: " + data.getOwnCardDto().getImage());
        tvNumberCard.setText(data.getOwnCardDto().getCardNoDisplay());
        try {
            tvNumberCard.setTextColor(Color.parseColor(data.getOwnCardDto().getStyleDto().getRgb()));
        } catch (Exception e) {
            Log.e("Exception", "setTextColor: "+e );
        }
        tvCustomerName.setText(data.getOwnCardDto().getName());
        llconten1.setVisibility(View.VISIBLE);
    }
}

