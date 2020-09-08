package com.lpb.lifecardsdk.ui.carddetails;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.CardDetails;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.carddetails.adapter.CardDetailsAdapter;

import java.util.List;

public class TestCardDetailsFragment extends BaseDataFragment<CardDetailsPresenter> implements CardDetailsContract.View {
    private RecyclerView rcCarddital;
    private CardDetailsAdapter cardDetailsAdapter;
    TextView btnTest;
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_cardditail_test_moc;
    }

    @Override
    protected void initView() {
        rcCarddital = view.findViewById(R.id.rcCarddital);
        cardDetailsAdapter = new CardDetailsAdapter(mActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        rcCarddital.setAdapter(cardDetailsAdapter);
        rcCarddital.setLayoutManager(linearLayoutManager);
        btnTest = view.findViewById(R.id.btnTest);


    }

    @Override
    protected void initData() {
        mPresenter = new CardDetailsPresenter(mActivity, this);
    }

    @Override
    protected void initAction() {
        cardDetailsAdapter.setOnClickListener(new CardDetailsAdapter.OnClickListener() {
            @Override
            public void onClick(CardDetails item) {
                mPresenter.onClickCardItem(item);
                ExampleButtonSheetDialog exampleButtonSheetDialog = new ExampleButtonSheetDialog();
                exampleButtonSheetDialog.show(mActivity.getSupportFragmentManager(), "example");
            }

            @Override
            public void onClick2(int pos) {

            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleButtonSheetDialog exampleButtonSheetDialog = new ExampleButtonSheetDialog();
                exampleButtonSheetDialog.show(mActivity.getSupportFragmentManager(), "example");
            }
        });
    }

    @Override
    public void setDataCardDeatils(List<CardDetails> CardDetails) {
        cardDetailsAdapter.setItems(CardDetails);
    }

}
