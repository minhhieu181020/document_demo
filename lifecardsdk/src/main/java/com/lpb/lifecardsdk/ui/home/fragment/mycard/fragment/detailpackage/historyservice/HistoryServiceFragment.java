package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.TransactionHistoryResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.bottomsheet.SelectLookUpTime;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.dialog.DialogDetailTransaction1;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice.adapter.HistoryServiceC1R2Adapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice.adapter.HistoryServiceC2R3Adapter;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice.adapter.HistoryServiceC3R4Adapter;
import com.lpb.lifecardsdk.util.DateUtils;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HistoryServiceFragment extends BaseDataFragment<HistoryServicePresenter> implements HistoryServiceContract.View, SelectLookUpTime.BottomSheetListener {
    private RecyclerView rcHistoryService;
    private LinearLayoutManager linearLayoutManager;
    private HistoryServiceC1R2Adapter historyServiceC1R2Adapter;
    private HistoryServiceC2R3Adapter historyServiceC2R3Adapter;
    private HistoryServiceC3R4Adapter historyServiceC3R4Adapter;
    private LinearLayout llCase1R2, llCase2R3, llCase3R4, ll_look_up, imgDatePicker, imgToDatePicker, llContent, llnodata, llselecttimer, ll_card_Still_validated, ll_card_Exprie, llLook;
    private TextView tvDate, tvToDate, tvallocatedUnit, tvexpiryDate, tvavailableUnit, tvll_look_up, tvLabelExpiration;
    //    private int type = 3;
    private Button btnlookup;
    private SimpleDateFormat formatter;
    private int pageSize = 1000;
    private int pageIndex = 0;
    private String unitType = "";
    private String cardCode = "";
    private String type = "5TRAN";
    private String startTime = "", endTime = "";

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_history_service;
    }

    @Override
    protected void initView() {
        formatter = new SimpleDateFormat(Config.FORMAT_DATE_DMY);
        rcHistoryService = view.findViewById(R.id.rcHistoryService);
        llCase1R2 = view.findViewById(R.id.llCase1R2);
        llCase2R3 = view.findViewById(R.id.llCase2R3);
        llCase3R4 = view.findViewById(R.id.llCase3R4);
        ll_look_up = view.findViewById(R.id.ll_look_up);
        llnodata = view.findViewById(R.id.llnodata);
        btnlookup = view.findViewById(R.id.btnLookup);
        llselecttimer = view.findViewById(R.id.llselecttimer);
        imgDatePicker = view.findViewById(R.id.imgDatePicker);
        imgToDatePicker = view.findViewById(R.id.imgToDatePicker);
        tvDate = view.findViewById(R.id.tvDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        tvallocatedUnit = view.findViewById(R.id.tvallocatedUnit);
        tvexpiryDate = view.findViewById(R.id.tvexpiryDate);
        tvavailableUnit = view.findViewById(R.id.tvavailableUnit);
        ll_card_Still_validated = view.findViewById(R.id.ll_card_Still_validated);
        ll_card_Exprie = view.findViewById(R.id.ll_card_Exprie);
        tvll_look_up = view.findViewById(R.id.tvll_look_up);
        llLook = view.findViewById(R.id.llLook);
        llselecttimer.setVisibility(View.GONE);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        llContent = view.findViewById(R.id.llContent);
        tvDate.setText(DateUtils.getCurrentDate());
        tvToDate.setText(DateUtils.getCurrentDate());
    }


    @Override
    protected void initData() {
        mPresenter = new HistoryServicePresenter(mActivity, this);
        cardCode = getArguments().getString(Constants.BundleConstants.CODECARD);
        if (PresenterUtils.isNetworkConnected(mActivity)) {
            showLoading(false);
            mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, "5TRAN", pageIndex, pageSize, startTime, endTime);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }

    }

    @Override
    protected void initAction() {
        final SelectLookUpTime qrCodeFragment = new SelectLookUpTime();
        qrCodeFragment.setmListenner(this);
        ll_look_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                String select = tvll_look_up.getText().toString();
                bundle1.putSerializable(Constants.BundleConstants.SELECT_TIME, select);
                qrCodeFragment.setArguments(bundle1);
                qrCodeFragment.show(getChildFragmentManager(), qrCodeFragment.getTag());


            }
        });
        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvDate);
            }
        });

        imgToDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvToDate);
            }
        });

        btnlookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcHistoryService.setVisibility(View.VISIBLE);
                llnodata.setVisibility(View.GONE);
                showLoading(false);
                if (unitType.equalsIgnoreCase("DAY")) {
                    historyServiceC1R2Adapter.clearData();
                } else if (unitType.equalsIgnoreCase("LIMIT")) {
                    historyServiceC2R3Adapter.clearData();
                } else if (unitType.equalsIgnoreCase("TIME")) {
                    historyServiceC3R4Adapter.clearData();
                }
                if (type.equalsIgnoreCase("5TRAN")) {
                    startTime = "";
                    endTime = "";
                    mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, pageIndex, pageSize, startTime, endTime);
                } else if (type.equalsIgnoreCase("10TRAN")) {
                    startTime = "";
                    endTime = "";
                    mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, pageIndex, pageSize, startTime, endTime);
                } else if (type.equalsIgnoreCase("1W")) {
                    startTime = "";
                    endTime = "";
                    mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, pageIndex, pageSize, startTime, endTime);
                } else if (type.equalsIgnoreCase("1MONTH")) {
                    startTime = "";
                    endTime = "";
                    mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, pageIndex, pageSize, startTime, endTime);
                } else if (type.equalsIgnoreCase("RANGE_TIME")) {
                    llselecttimer.setVisibility(View.VISIBLE);
                    startTime = tvDate.getText().toString().trim();
                    endTime = tvToDate.getText().toString().trim();
                    mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, pageIndex, pageSize, startTime, endTime);

                }
            }
        });


    }

    @Override
    public void setviewnodata(boolean b) {
        if (b) {
            llnodata.setVisibility(View.VISIBLE);
            rcHistoryService.setVisibility(View.GONE);
            llCase1R2.setVisibility(View.GONE);
            llCase2R3.setVisibility(View.GONE);
            llCase3R4.setVisibility(View.GONE);

        } else {
            llnodata.setVisibility(View.GONE);
            rcHistoryService.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void SetView(String s) {
        if (s.equalsIgnoreCase("DAY")) {
//inti view
            unitType = "DAY";
            historyServiceC1R2Adapter = new HistoryServiceC1R2Adapter(mActivity);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            rcHistoryService.setLayoutManager(linearLayoutManager);
            rcHistoryService.setAdapter(historyServiceC1R2Adapter);
            llCase1R2.setVisibility(View.VISIBLE);
//inti data
            mPresenter.getCardHistoryServiceC1R2();
//setonclick
            historyServiceC1R2Adapter.setOnClickListener(new HistoryServiceC1R2Adapter.OnClickListener() {
                @Override
                public void onClick(TransactionHistoryResponseDefault.AccountEntryDto item) {
                    mPresenter.onClickC1R2(item);
                    DialogDetailTransaction1 exampleButtonSheetDialog = new DialogDetailTransaction1();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_HISTORY, item);
                    exampleButtonSheetDialog.setArguments(bundle);
                    exampleButtonSheetDialog.show(mActivity.getSupportFragmentManager(), "example");

                }
            });

        } else if (s.equalsIgnoreCase("LIMIT")) {
//inti view
            unitType = "LIMIT";
            historyServiceC2R3Adapter = new HistoryServiceC2R3Adapter(mActivity);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            rcHistoryService.setLayoutManager(linearLayoutManager);
            rcHistoryService.setAdapter(historyServiceC2R3Adapter);
            llCase2R3.setVisibility(View.VISIBLE);
//inti data
            mPresenter.getCardHistoryServiceC2R3();
//setonclick
            historyServiceC2R3Adapter.setOnClickListener(new HistoryServiceC2R3Adapter.OnClickListener() {
                @Override
                public void onClick(TransactionHistoryResponseDefault.AccountEntryDto item) {
                    mPresenter.onClickC2R3(item);
                    DialogDetailTransaction1 exampleButtonSheetDialog = new DialogDetailTransaction1();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_HISTORY, item);
                    exampleButtonSheetDialog.setArguments(bundle);
                    exampleButtonSheetDialog.show(mActivity.getSupportFragmentManager(), "example");
                }

            });

        } else if (s.equalsIgnoreCase("TIMES")) {
//inti view
            unitType = "TIMES";
            historyServiceC3R4Adapter = new HistoryServiceC3R4Adapter(mActivity);
            linearLayoutManager = new LinearLayoutManager(mActivity);
            rcHistoryService.setLayoutManager(linearLayoutManager);
            rcHistoryService.setAdapter(historyServiceC3R4Adapter);
            llCase3R4.setVisibility(View.VISIBLE);
//inti data
            mPresenter.getCardHistoryServiceC3R4();
//setonclick
            historyServiceC3R4Adapter.setOnClickListener(new HistoryServiceC3R4Adapter.OnClickListener() {
                @Override
                public void onClick(TransactionHistoryResponseDefault.AccountEntryDto item) {
                    mPresenter.onClickC3R4(item);
                    DialogDetailTransaction1 exampleButtonSheetDialog = new DialogDetailTransaction1();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BundleConstants.MY_CARD_DETAIL_PACKGE_HISTORY, item);
                    exampleButtonSheetDialog.setArguments(bundle);
                    exampleButtonSheetDialog.show(mActivity.getSupportFragmentManager(), "example");

                }
            });
        }

//        rcHistoryService.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                mPresenter.getDataCard(LCConfig.getPhoneNumber(), cardCode, type, page, pageSize, startTime, endTime);
//            }
//        });
    }

    @Override
    public void setData(TransactionHistoryResponseDefault data) {
        if (StringUtils.isEmpty(data.getUsageDescription())) {
            tvallocatedUnit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(data.getOwnAccountDtos().get(0).getAllocatedUnit())));
        } else {
            tvallocatedUnit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(data.getUsageDescription())));
        }
//        tvNamePackage.setText(data.getOwnServiceDto().getNamDefService());
        if (StringUtils.isEmpty(data.getExpirationDate())) {
            tvLabelExpiration.setVisibility(View.GONE);
            tvexpiryDate.setVisibility(View.GONE);
            tvexpiryDate.setText("");
        } else {
            tvexpiryDate.setText(StringUtils.convertHTMLToString("<b>" + data.getExpirationDate() + "</b>", mActivity));
            tvLabelExpiration.setVisibility(View.VISIBLE);
            tvexpiryDate.setVisibility(View.VISIBLE);
        }

        tvavailableUnit.setText(data.getOwnAccountDtos().get(0).getAvailableUnit());
        if (data.getAccountEntryDtos().size() == 0) {
            llnodata.setVisibility(View.VISIBLE);
        }
    }

    public void showStatus(String s) {
//        - nếu status = 'O' hoặc = 'A' thì hiển thị là đã thanh toán
//        - nếu status = 'w' thì hiển thị là đang chờ thanh toán
//        - nếu status = 'E' hoặc 'C' thì hiển thị là đã hết hạn
        if (s.equals("A") || s.equals("O")) {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.VISIBLE);
            llLook.setVisibility(View.GONE);
        } else if (s.equals("E") || s.equals("C")) {
            ll_card_Exprie.setVisibility(View.VISIBLE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llLook.setVisibility(View.GONE);
        } else {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llLook.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void getDataTime(final String type) {
//        5TRAN
//        10TRAN
//        1W
//        1MONTH

        this.type = type;

        if (type.equalsIgnoreCase("5TRAN")) {
            tvll_look_up.setText(R.string.lifecardsdk_card_Last_5_transactions);
            llselecttimer.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("10TRAN")) {
            tvll_look_up.setText(R.string.lifecardsdk_card_Last_10_transactions);
            llselecttimer.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("1W")) {
            tvll_look_up.setText(R.string.lifecardsdk_card_Last_1_week_transactions);
            llselecttimer.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("1MONTH")) {
            tvll_look_up.setText(R.string.lifecardsdk_card_Last_1_month_transactions);
            llselecttimer.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("RANGE_TIME")) {
            llselecttimer.setVisibility(View.VISIBLE);
            tvll_look_up.setText(R.string.lifecardsdk_card_Last_time_transactions);
        } else if (type.equalsIgnoreCase("00027")) {
            llnodata.setVisibility(View.VISIBLE);
        }
    }

    public void stopLoadMoreC1R2() {
        rcHistoryService.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });
        historyServiceC1R2Adapter.setOnLoadMore(false);
    }

    public void stopLoadMoreC2R3() {
        rcHistoryService.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });
        historyServiceC2R3Adapter.setOnLoadMore(false);
    }

    public void stopLoadMoreC3R4() {
        rcHistoryService.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        });
        historyServiceC3R4Adapter.setOnLoadMore(false);
    }

    @Override
    public void setDataHistoryServiceC1R2(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC1R2) {
        if (historyServiceC1R2.size() == 0) {
            stopLoadMoreC1R2();
        }
        historyServiceC1R2Adapter.setItems(historyServiceC1R2);
    }

    @Override
    public void addDataHistoryServiceC1R2(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC1R2) {
        if (historyServiceC1R2.size() == 0) {
            stopLoadMoreC1R2();
        }
        historyServiceC1R2Adapter.addItems(historyServiceC1R2);
    }

    @Override
    public void setDataHistoryServiceC2R3(List<TransactionHistoryResponseDefault.AccountEntryDto> dataHistoryServiceC2R3) {
        if (dataHistoryServiceC2R3.size() == 0) {
            stopLoadMoreC2R3();
        }
        historyServiceC2R3Adapter.setItems(dataHistoryServiceC2R3);

    }

    @Override
    public void addDataHistoryServiceC2R3(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC2R3) {
        if (historyServiceC2R3.size() == 0) {
            stopLoadMoreC2R3();
        }
        historyServiceC2R3Adapter.addItems(historyServiceC2R3);
    }


    @Override
    public void setDataHistoryServiceC3R4(List<TransactionHistoryResponseDefault.AccountEntryDto> dataHistoryServiceC3R4) {
        if (dataHistoryServiceC3R4.size() == 0) {
            stopLoadMoreC3R4();
        }

        historyServiceC3R4Adapter.setItems(dataHistoryServiceC3R4);

    }

    @Override
    public void addDataHistoryServiceC3R4(List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC3R4) {
        if (historyServiceC3R4.size() == 0) {
            stopLoadMoreC3R4();
        }
        historyServiceC3R4Adapter.addItems(historyServiceC3R4);
    }


    private void showDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                long date = calendar.getTimeInMillis();
                textView.setText(formatter.format(date));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

}
