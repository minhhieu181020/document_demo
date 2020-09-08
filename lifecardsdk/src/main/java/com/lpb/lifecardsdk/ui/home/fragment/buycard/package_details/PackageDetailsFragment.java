package com.lpb.lifecardsdk.ui.home.fragment.buycard.package_details;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PackageDetailResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageDetailsFragment extends BaseDataFragment<PackageDetailsPresenter> implements PackageDetailsContract.View {
    private ImageView imgBack;
    private TextView tvPricePerUse;
    //private TextView tvUseLimit, tvLabelUseLimit;
    private TextView tvListedPrice;
    private TextView tvPrice;
    private TextView tvQuotaUsed;
    private TextView tvExpirationDate;
    private View viewVertical;
    private TextView tvDescription,tvLabelExpiration;
    private TextView tvLabelPricePerUse;
    private TextView tvTitleToolbar, tvSaving;
    private TextView tvLabelListedPrice, tvLabelPrice, tvLabelSaving;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_buycard_package_details;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        tvPricePerUse = view.findViewById(R.id.tvPricePerUse);
        tvListedPrice = view.findViewById(R.id.tvListedPrice);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvQuotaUsed = view.findViewById(R.id.tvQuotaUsed);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        viewVertical = view.findViewById(R.id.viewVertical);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        tvLabelPricePerUse = view.findViewById(R.id.tvLabelPricePerUse);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
//        tvUseLimit = view.findViewById(R.id.tvUseLimit);
//        tvLabelUseLimit = view.findViewById(R.id.tvLabelUseLimit);
        tvLabelListedPrice = view.findViewById(R.id.tvLabelListedPrice);
        tvLabelPrice = view.findViewById(R.id.tvLabelPrice);
        tvSaving = view.findViewById(R.id.tvSavingPrice);
        tvLabelSaving = view.findViewById(R.id.tvLabelSavingPrice);

    }

    @Override
    protected void initData() {
        mPresenter = new PackageDetailsPresenter(mActivity, this);
        Bundle bundle = getArguments();
        PackageDetailResponse.DefServiceDto dataService = (PackageDetailResponse.DefServiceDto) bundle.getSerializable(Constants.BundleConstants.PACKAGE_DETAILS);
        PackageDetailResponse.DefCardDto dataCard = (PackageDetailResponse.DefCardDto) bundle.getSerializable(Constants.BundleConstants.CARD_DETAIL_);
        String type = bundle.getString(Constants.BundleConstants.TYPE);

        assert type != null;
        if (type.equals(Constants.TypeDetails.CARD)) {
            assert dataCard != null;
            setViewCard(dataCard);

        }
        if (type.equals(Constants.TypeDetails.SERVICE)) {
            assert dataService != null;
            setViewPackage(dataService);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setViewCard(PackageDetailResponse.DefCardDto data) {
        tvTitleToolbar.setText(data.getRootService().getName());
        if (StringUtils.isEmpty(data.getRootService().getExpiryDate())){
            tvLabelExpiration.setVisibility(View.GONE);
            tvExpirationDate.setVisibility(View.GONE);
        }else {
            tvLabelExpiration.setVisibility(View.VISIBLE);
            tvExpirationDate.setVisibility(View.VISIBLE);
            tvExpirationDate.setText(data.getRootService().getExpiryDate());
        }


        tvListedPrice.setText(data.getRootService().getListedPrice());


        tvDescription.setText(StringUtils.convertHTMLToString(data.getRootService().getDescription(), mActivity));
        tvPrice.setText(data.getRootService().getPrice());
        //tvUseLimit.setText(data.getRootService().getDefServiceUnitDtos().get(0).getDisplayLimitUnit());
        tvPricePerUse.setText(data.getRootService().getUnitPrice());

        if ((data.getRootService().getListedPriceNumber() - data.getRootService().getPriceNumber()) <= 0) {
            tvLabelSaving.setVisibility(View.GONE);
            tvSaving.setVisibility(View.GONE);
        } else {
            tvLabelSaving.setVisibility(View.VISIBLE);
            tvSaving.setVisibility(View.VISIBLE);
            tvSaving.setText(StringUtils.formatPrice(data.getRootService().getListedPriceNumber() - data.getRootService().getPriceNumber()) + " đ");
        }


//        int count = 0;
//        Pattern p = Pattern.compile("<li>");
//        Matcher m = p.matcher(data.getRootService().getLimitDetailDescription());
//        while (m.find()) {
//            count++;
//        }
//        if (count > 1)
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString("<ul>" + data.getRootService().getLimitDetailDescription() + "</ul>", mActivity));
//        else
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString(data.getRootService().getLimitDetailDescription(), mActivity));

        tvQuotaUsed.setText(data.getRootService().getDefServiceUnitDtos().get(0).getDisplayLimitUnit());


        tvLabelPricePerUse.setVisibility(View.GONE);
//        tvLabelUseLimit.setVisibility(View.GONE);
//        tvUseLimit.setVisibility(View.GONE);
        tvPricePerUse.setVisibility(View.GONE);


        /**
         trường hợp giá bằng nhau đồng thời = 0
         */
//        if (data.getRootService().getPriceNumber().equals(data.getRootService().getListedPriceNumber())) {
//            tvListedPrice.setVisibility(View.GONE);
//            tvLabelListedPrice.setVisibility(View.GONE);
//            if (data.getRootService().getPriceNumber() == 0 && data.getRootService().getListedPriceNumber() == 0) {
//                tvPrice.setVisibility(View.GONE);
//                tvLabelPrice.setVisibility(View.GONE);
//            }
//        }

        if (!data.getRootService().getPriceNumber().equals(data.getRootService().getListedPriceNumber())) {
            tvListedPrice.setPaintFlags(tvListedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (data.getRootService().getPriceNumber() == 0 && data.getRootService().getListedPriceNumber() == 0) {
            tvPrice.setVisibility(View.GONE);
            tvLabelPrice.setVisibility(View.GONE);
            tvListedPrice.setVisibility(View.GONE);
            tvLabelListedPrice.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(data.getRootService().getDescription())) {
            viewVertical.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("SetTextI18n")
    public void setViewPackage(PackageDetailResponse.DefServiceDto data) {
        tvTitleToolbar.setText(data.getName());

        if (StringUtils.isEmpty(data.getExpiryDate())){
            tvLabelExpiration.setVisibility(View.GONE);
            tvExpirationDate.setVisibility(View.GONE);
        }else {
            tvLabelExpiration.setVisibility(View.VISIBLE);
            tvExpirationDate.setVisibility(View.VISIBLE);
            tvExpirationDate.setText( data.getExpiryDate());
        }
        tvListedPrice.setText(data.getListedPrice());


        tvDescription.setText(StringUtils.convertHTMLToString(data.getDescription(), mActivity));
        tvPrice.setText(data.getPrice());
        //tvUseLimit.setText(data.getDefServiceUnitDtos().get(0).getDisplayLimitUnit());
        tvPricePerUse.setText(data.getUnitPrice());

        if ((data.getListedPriceNumber() - data.getPriceNumber()) <= 0) {
            tvLabelSaving.setVisibility(View.GONE);
            tvSaving.setVisibility(View.GONE);
        } else {
            tvLabelSaving.setVisibility(View.VISIBLE);
            tvSaving.setVisibility(View.VISIBLE);
            tvSaving.setText(StringUtils.formatPrice(data.getListedPriceNumber() - data.getPriceNumber()) + " đ");
        }

//        int count = 0;
//        Pattern p = Pattern.compile("<li>");
//        Matcher m = p.matcher(data.getLimitDetailDescription());
//        while (m.find()) {
//            count++;
//        }
//        if (count > 1)
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString("<ul>" + data.getLimitDetailDescription() + "</ul>", mActivity));
//        else
//            tvQuotaUsed.setText(StringUtils.convertHTMLToString(data.getLimitDetailDescription(), mActivity));

        tvQuotaUsed.setText(data.getDefServiceUnitDtos().get(0).getDisplayLimitUnit());

        tvLabelPricePerUse.setVisibility(View.VISIBLE);
//        tvLabelUseLimit.setVisibility(View.VISIBLE);
//        tvUseLimit.setVisibility(View.VISIBLE);
        tvPricePerUse.setVisibility(View.VISIBLE);


        /**
         trường hợp giá bằng nhau đồng thời = 0
         */
//        if (data.getPriceNumber().equals(data.getListedPriceNumber())) {
//            tvListedPrice.setVisibility(View.GONE);
//            tvLabelListedPrice.setVisibility(View.GONE);
//            if (data.getPriceNumber() == 0 && data.getListedPriceNumber() == 0) {
//                tvPrice.setVisibility(View.GONE);
//                tvLabelPrice.setVisibility(View.GONE);
//            }
//        }

        if (!data.getPriceNumber().equals(data.getListedPriceNumber())) {
            tvListedPrice.setPaintFlags(tvListedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (data.getPriceNumber() == 0 && data.getListedPriceNumber() == 0) {
            tvPrice.setVisibility(View.GONE);
            tvLabelPrice.setVisibility(View.GONE);
            tvListedPrice.setVisibility(View.GONE);
            tvLabelListedPrice.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(data.getDescription())) {
            viewVertical.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }
}
