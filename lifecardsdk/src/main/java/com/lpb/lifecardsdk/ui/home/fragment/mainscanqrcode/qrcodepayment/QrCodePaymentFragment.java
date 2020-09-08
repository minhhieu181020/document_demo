package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodepayment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.request.default_.ServicePaymentQrBillRequest;
import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.MainScanQrCodeFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.adapter.ListCardServiceAdapter;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard.InfoCardFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.qrcodedeatailpayment.QrCodePaymentDetailFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;

import java.util.List;

public class QrCodePaymentFragment extends BaseDataFragment<QrCodePaymentPresenter> implements QrCodePaymentContract.View {
    private Button btnCansel, btnPayMent;
    private TextView tvTitleToolbar;
    private ImageView imgBack;
    private RecyclerView rcListCardService;
    private ListCardServiceAdapter listCardServiceAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NestedScrollView scrollView;
    private TextView tvBranch, tvStaff, tvTransCode, tvTransTime, tvBillCode, tvCustomerName, tvMobilePhone;
    private RelativeLayout rlTransacstiuonDetail;
    private LinearLayout llvat;
    private ListServicePaymentQrBillResponse listservicePaymentQrBillResponse;
    private String mainParentClass;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_qrcode_payment;
    }

    @Override
    protected void initView() {
        btnCansel = view.findViewById(R.id.btnCansel);
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvTitleToolbar.setText(R.string.lifecardsdk_confirm_transaction);

        rcListCardService = view.findViewById(R.id.rcListCardService);
        listCardServiceAdapter = new ListCardServiceAdapter(mActivity);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rcListCardService.setAdapter(listCardServiceAdapter);
        rcListCardService.setLayoutManager(linearLayoutManager);

        scrollView = view.findViewById(R.id.scrollView);
        rcListCardService.setNestedScrollingEnabled(false);
        tvBranch = view.findViewById(R.id.tvBranch);
        tvStaff = view.findViewById(R.id.tvStaff);
        tvTransCode = view.findViewById(R.id.tvTransCode);
        tvTransTime = view.findViewById(R.id.tvTransTime);
        tvBillCode = view.findViewById(R.id.tvBillCode);
        tvCustomerName = view.findViewById(R.id.tvCustomerName);
        tvMobilePhone = view.findViewById(R.id.tvMobilePhone);
        rlTransacstiuonDetail = view.findViewById(R.id.rlTransacstiuonDetail);
        btnPayMent = view.findViewById(R.id.btnPayMent);
        llvat = view.findViewById(R.id.llvat);
    }

    @Override
    protected void initData() {
        mPresenter = new QrCodePaymentPresenter(mActivity, this);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        listservicePaymentQrBillResponse = (ListServicePaymentQrBillResponse) bundle.getSerializable(Constants.BundleConstants.QRPAYMENT);
        setListCardService(listservicePaymentQrBillResponse.getServiceDetailDatas());
        getData(listservicePaymentQrBillResponse);
        mainParentClass = bundle.getString(Constants.BundleConstants.MAIN_PARENT_CLASS);
    }

    @Override
    protected void initAction() {


        btnCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        listCardServiceAdapter.setOnClickListener(new ListCardServiceAdapter.OnClickListener() {
            @Override
            public void onClick(ListServicePaymentQrBillResponse.ServiceDetailData item) {
                InfoCardFragment infoCardFragment = new InfoCardFragment();
                Bundle bundle = new Bundle();
                bundle.putString("getCardNo", item.getCardNo());
                bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                infoCardFragment.setArguments(bundle);
                switch (mainParentClass) {
                    case Constants.ParentClass.MainMyCardFragment:
                        MainMyCardsFragment.getInstance().addFragment(infoCardFragment, QrCodePaymentFragment.this);
                        break;
                    case Constants.ParentClass.MainScanQrCodeFragment:
                        MainScanQrCodeFragment.getInstance().addFragment(infoCardFragment, QrCodePaymentFragment.this);
                        break;
                    default:
                        showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                        break;
                }
            }

        });

        btnPayMent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PresenterUtils.isNetworkConnected(mActivity)) {
                    ServicePaymentQrBillRequest paymentQrBillRequest = new ServicePaymentQrBillRequest(listservicePaymentQrBillResponse.getBillCode(), listservicePaymentQrBillResponse.getBranchDTO().getCode(), listservicePaymentQrBillResponse.getStaffDto().getStaffNo(), listservicePaymentQrBillResponse.getCustomerName(), listservicePaymentQrBillResponse.getMobilePhone(), listservicePaymentQrBillResponse.getServiceDetailDatas());
                    QrCodePaymentDetailFragment qrCodePaymentDetailFragment = new QrCodePaymentDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listservicePaymentQrBillResponse", paymentQrBillRequest);
                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, mainParentClass);
                    qrCodePaymentDetailFragment.setArguments(bundle);
                    switch (mainParentClass) {
                        case Constants.ParentClass.MainMyCardFragment:
                            MainMyCardsFragment.getInstance().addFragment(qrCodePaymentDetailFragment, QrCodePaymentFragment.this);
                            break;
                        case Constants.ParentClass.MainScanQrCodeFragment:
                            MainScanQrCodeFragment.getInstance().addFragment(qrCodePaymentDetailFragment, QrCodePaymentFragment.this);
                            break;
                        default:
                            showError(mActivity.getString(R.string.lifecardsdk_realtime_error), "");
                            break;
                    }
                } else {
                    hideLoading();
                    showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
                }
            }
        });
    }

    @Override
    public void setListCardService(List<ListServicePaymentQrBillResponse.ServiceDetailData> listCardService) {
        listCardServiceAdapter.setItems(listCardService);
    }

    @Override
    public void getData(ListServicePaymentQrBillResponse data) {
        tvBranch.setText(data.getBranchDTO().getName() + "\n" + data.getBranchDTO().getFullAddress());
        tvStaff.setText(data.getStaffDto().getUsername());
        tvTransCode.setText(data.getBillCode());
//        tvTransTime.setText(data.getTransTime());
//        tvBillCode.setText(data.getBillCode());
        tvCustomerName.setText(data.getCustomerName());
        tvMobilePhone.setText(data.getMobilePhone());
//        tvTransLimitTimesDisplay.setText(data.getTransLimitTimesDisplay());
//        tvTransAmountDisplay.setText(data.getTransAmountDisplay());
//        tvTotalAdditionalAmountDisplay.setText(data.getTotalAdditionalAmountDisplay());
//        tvVatDisplay.setText(data.getVatDisplay());
//        tvKHTIMESlAmount.setText("0 lần");
//        rlTransacstiuonDetail.setVisibility(View.GONE);
        hideLoading();
    }

}

