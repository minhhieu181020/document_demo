package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodedeatailpayment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.ServicePaymentQrBillRequest;
import com.lpb.lifecardsdk.data.model.response.default_.ServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.adapter.DetailCardServiceAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard.InfoCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;

public class QrCodePaymentDetailFragment extends BaseDataFragment<QrCodePaymentDetailPresenter> implements QrCodePaymentDetailContract.View {
    private RelativeLayout rlTransacstiuonDetail;
    private RelativeLayout rlToolbar;
    private TextView tvTitleToolbar;
    private NestedScrollView scrollView;
    private TextView tvtracstioninfomation;
    private LinearLayout lltransactioninfomation;
    private TextView tvBranch;
    private TextView tvStaff;
    private TextView tvTransCode;
    private LinearLayout llvat;
    private TextView tvVatDisplay;
    private TextView tvCustomerName;
    private TextView tvMobilePhone;
    private TextView tvBillCode;
    private TextView tvTransTime;
    private TextView tvTransStatus;
    private LinearLayout llListtransaction;
    private RecyclerView rcListCardService;
    private Button btnClosed;
    private ServicePaymentQrBillRequest servicePaymentQrBillRequest;
    private String mainParentClass;
    private DetailCardServiceAdapter detailCardServiceAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_qrcode_payment_detail;
    }

    @Override
    protected void initView() {
        rlTransacstiuonDetail = view.findViewById(R.id.rlTransacstiuonDetail);
        rlToolbar = view.findViewById(R.id.rlToolbar);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        scrollView = view.findViewById(R.id.scrollView);
        tvtracstioninfomation = view.findViewById(R.id.tvtracstioninfomation);
        lltransactioninfomation = view.findViewById(R.id.lltransactioninfomation);
        tvBranch = view.findViewById(R.id.tvBranch);
        tvStaff = view.findViewById(R.id.tvStaff);
        tvTransCode = view.findViewById(R.id.tvTransCode);
        llvat = view.findViewById(R.id.llvat);
        tvVatDisplay = view.findViewById(R.id.tvVatDisplay);
        tvCustomerName = view.findViewById(R.id.tvCustomerName);
        tvMobilePhone = view.findViewById(R.id.tvMobilePhone);
        tvBillCode = view.findViewById(R.id.tvBillCode);
        tvTransTime = view.findViewById(R.id.tvTransTime);
        tvTransStatus = view.findViewById(R.id.tvTransStatus);
        llListtransaction = view.findViewById(R.id.llListtransaction);
        btnClosed = view.findViewById(R.id.btnClosed);
        tvTitleToolbar.setText("Kết quả giao dịch");
        rcListCardService = view.findViewById(R.id.rcListCardService);
        scrollView = view.findViewById(R.id.scrollView);
        rcListCardService.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        detailCardServiceAdapter = new DetailCardServiceAdapter(mActivity);
        mPresenter = new QrCodePaymentDetailPresenter(getContext(), this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        servicePaymentQrBillRequest = (ServicePaymentQrBillRequest) bundle.getSerializable("listservicePaymentQrBillResponse");
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
        mPresenter.callApiConFirm(servicePaymentQrBillRequest);
    }

    @Override
    protected void initAction() {
        btnClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();

            }
        });
        detailCardServiceAdapter.setOnClickListener(new DetailCardServiceAdapter.OnClickListener() {
            @Override
            public void onClick(ServicePaymentQrBillResponse.ServiceDetailData item) {
                InfoCardFragment infoCardFragment = new InfoCardFragment();
                Bundle bundle = new Bundle();
                bundle.putString("getCardNo", item.getCardNo());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                infoCardFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(infoCardFragment, QrCodePaymentDetailFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(infoCardFragment, QrCodePaymentDetailFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
            }
        });
    }

    @Override
    public void setdata(ServicePaymentQrBillResponse data) {
        detailCardServiceAdapter.addItems(data.getServiceDetailDatas());
        tvBranch.setText(data.getBranchDto().getName() + "\n" + data.getBranchDto().getFullAddress());
        tvStaff.setText(data.getStaffName());
        tvTransCode.setText(data.getBillCode());
        tvCustomerName.setText(data.getCustomerName());
        tvMobilePhone.setText(data.getMobilePhone());
        tvBillCode.setText(data.getBillCode());
        tvTransTime.setText(data.getTransTime());
        if (data.getTransStatus().equalsIgnoreCase("A")) {
            tvTransStatus.setText("Thành công");
            tvTransStatus.setTextColor(Color.parseColor("#4daf50"));
            detailCardServiceAdapter.setStatus(true);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            rcListCardService.setAdapter(detailCardServiceAdapter);
            rcListCardService.setLayoutManager(linearLayoutManager);
        } else {
            tvTransStatus.setText("Thất bại");
            tvTransStatus.setTextColor(Color.parseColor("#FF0000"));
            detailCardServiceAdapter.setStatus(false);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            rcListCardService.setAdapter(detailCardServiceAdapter);
            rcListCardService.setLayoutManager(linearLayoutManager);
        }
    }
}
