package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.infopakage;

import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.CardPackageServiceDetailResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardDetailsWaitInforFragment extends BaseDataFragment<CardDetailsWaitInforPresenter> implements CardDetailsWaitInforContract.View {

    private ImageView imgBack;

    private LinearLayout ll_card_Still_validated;
    private LinearLayout ll_card_Exprie;
    private LinearLayout llWaitForPayMent;
    //private TextView tvUseLimit,tvLabelUseLimit;
    private TextView tvExpirationDate;
    private TextView tvTitleToolbar;
    private RelativeLayout rlToolbar;
    private LinearLayout llCardStillValidated;
    private LinearLayout llCardExprie;
    private TextView tvLabelPricePerUse,tvLabelExpiration;
    private TextView tvPricePerUse;
    private TextView tvLabelListedPrice;
    private TextView tvListedPrice;
    private TextView tvLabelPrice;
    private TextView tvPrice;
    private TextView tvQuotaUsed,tvSaving,tvLabelSaving;
    private View viewVertical;
    private TextView tvDescription;
    private CardView cvContent;
    private String type;
    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_info_card_wait_package;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);

        ll_card_Still_validated = view.findViewById(R.id.ll_card_Still_validated);
        ll_card_Exprie = view.findViewById(R.id.ll_card_Exprie);
        llWaitForPayMent = view.findViewById(R.id.llWaitForPayMent);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);

        rlToolbar = view.findViewById(R.id.rlToolbar);
        llCardStillValidated = view.findViewById(R.id.ll_card_Still_validated);
        llCardExprie = view.findViewById(R.id.ll_card_Exprie);
        tvLabelPricePerUse = view.findViewById(R.id.tvLabelPricePerUse);
        tvPricePerUse = view.findViewById(R.id.tvPricePerUse);
//        tvLabelUseLimit = view.findViewById(R.id.tvLabelUseLimit);
//        tvUseLimit = view.findViewById(R.id.tvUseLimit);
        tvLabelListedPrice = view.findViewById(R.id.tvLabelListedPrice);
        tvListedPrice = view.findViewById(R.id.tvListedPrice);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        tvLabelPrice = view.findViewById(R.id.tvLabelPrice);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvQuotaUsed = view.findViewById(R.id.tvQuotaUsed);
        viewVertical = view.findViewById(R.id.viewVertical);
        tvDescription = view.findViewById(R.id.tvDescription);
        cvContent = view.findViewById(R.id.cvContent);

        tvSaving = view.findViewById(R.id.tvSavingPrice);
        tvLabelSaving = view.findViewById(R.id.tvLabelSavingPrice);
    }

    @Override
    protected void initData() {
        mPresenter = new CardDetailsWaitInforPresenter(mActivity, this);
        String data = getArguments().getString(Constants.BundleConstants.MY_CARD_INFORMATION);
        type = getArguments().getString(Constants.BundleConstants.TYPE, "");
        if (StringUtils.isEmpty(type)) {
            showError(getString(R.string.lifecardsdk_realtime_error), "");
            return;
        }

        if (PresenterUtils.isNetworkConnected(mActivity)) {
            showLoading(true);
            mPresenter.getDataInforCard(LCConfig.getPhoneNumber(), data);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mActivity.onBackPressed();
            }
        });

    }

    @Override
    public void setData(CardPackageServiceDetailResponseDefault data) {
        cvContent.setVisibility(View.VISIBLE);
        CardPackageServiceDetailResponseDefault.OwnServiceDto ownServiceDto = data.getOwnServiceDto();

        tvTitleToolbar.setText(ownServiceDto.getNamDefService());

        tvListedPrice.setText(ownServiceDto.getListedPrice());

        tvDescription.setText(StringUtils.convertHTMLToString(ownServiceDto.getDescription(), mActivity));
        tvPrice.setText(ownServiceDto.getPrice());
        //tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(ownServiceDto.getOwnAccountDtos().get(0).getAllocatedUnit())));

        tvQuotaUsed.setText(ownServiceDto.getOwnAccountDtos().get(0).getAllocatedUnit());


        tvPricePerUse.setText(ownServiceDto.getUnitPrice());


//        int count = 0;
//        Pattern p = Pattern.compile("<li>");
//        Matcher m = p.matcher(ownServiceDto.getLimitDetailDescription());
//        while (m.find()) {
//            count++;
//        }
//        if (count > 1)
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString("<ul>" + ownServiceDto.getLimitDetailDescription() + "</ul>", mActivity));
//        else
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString(ownServiceDto.getLimitDetailDescription(), mActivity));


        if ((ownServiceDto.getListedPriceNumber() - ownServiceDto.getPriceNumber()) <= 0) {
            tvLabelSaving.setVisibility(View.GONE);
            tvSaving.setVisibility(View.GONE);
        } else {
            tvLabelSaving.setVisibility(View.VISIBLE);
            tvSaving.setVisibility(View.VISIBLE);
            tvSaving.setText(StringUtils.formatPrice(ownServiceDto.getListedPriceNumber() - ownServiceDto.getPriceNumber()) + " đ");
        }


        if (type.equals(Constants.TYPE_DETAILS.CARD)){
            tvLabelPricePerUse.setVisibility(View.GONE);
//            tvLabelUseLimit.setVisibility(View.GONE);
//            tvUseLimit.setVisibility(View.GONE);
            tvPricePerUse.setVisibility(View.GONE);
        }else {
            tvLabelPricePerUse.setVisibility(View.VISIBLE);
//            tvLabelUseLimit.setVisibility(View.VISIBLE);
//            tvUseLimit.setVisibility(View.VISIBLE);
            tvPricePerUse.setVisibility(View.VISIBLE);
        }



        if (!ownServiceDto.getPriceNumber().equals(ownServiceDto.getListedPriceNumber())) {
            tvListedPrice.setPaintFlags(tvListedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (ownServiceDto.getPriceNumber() == 0 && ownServiceDto.getListedPriceNumber() == 0) {
            tvPrice.setVisibility(View.GONE);
            tvLabelPrice.setVisibility(View.GONE);
            tvListedPrice.setVisibility(View.GONE);
            tvLabelListedPrice.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(ownServiceDto.getDescription())) {
            viewVertical.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }
        showStatus(ownServiceDto);
    }

    public void showStatus(CardPackageServiceDetailResponseDefault.OwnServiceDto data) {
//        - nếu status = 'O' hoặc = 'A' thì hiển thị là đã thanh toán
//        - nếu status = 'w' thì hiển thị là đang chờ thanh toán
//        - nếu status = 'E' hoặc 'C' thì hiển thị là đã hết hạn
        String s = data.getStatus();
        if (s.equals("A") || s.equals("O")) {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.VISIBLE);
            llWaitForPayMent.setVisibility(view.GONE);
            if (StringUtils.isEmpty(data.getExpirationDate())){
                tvLabelExpiration.setVisibility(View.GONE);
                tvExpirationDate.setVisibility(View.GONE);
                tvExpirationDate.setText("");
            }else {
                tvLabelExpiration.setVisibility(View.VISIBLE);
                tvExpirationDate.setVisibility(View.VISIBLE);
                tvExpirationDate.setText(data.getExpirationDate());
            }

        } else if (s.equals("E") || s.equals("C")) {
            ll_card_Exprie.setVisibility(View.VISIBLE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llWaitForPayMent.setVisibility(view.GONE);
            if (StringUtils.isEmpty(data.getExpirationDate())){
                tvLabelExpiration.setVisibility(View.GONE);
                tvExpirationDate.setVisibility(View.GONE);
                tvExpirationDate.setText("");
            }else {
                tvLabelExpiration.setVisibility(View.VISIBLE);
                tvExpirationDate.setVisibility(View.VISIBLE);
                tvExpirationDate.setText(data.getExpirationDate());
            }
        } else {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llWaitForPayMent.setVisibility(view.VISIBLE);

            if (StringUtils.isEmpty(data.getExpiryDate())){
                tvLabelExpiration.setVisibility(View.GONE);
                tvExpirationDate.setVisibility(View.GONE);
                tvExpirationDate.setText("");
            }else {
                tvLabelExpiration.setVisibility(View.VISIBLE);
                tvExpirationDate.setVisibility(View.VISIBLE);
                tvExpirationDate.setText(data.getExpiryDate());
            }
        }
    }


}
