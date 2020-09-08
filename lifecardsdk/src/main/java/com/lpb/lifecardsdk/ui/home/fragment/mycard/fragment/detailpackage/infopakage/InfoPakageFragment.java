package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.infopakage;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.constant.LCConfig;
import com.lpb.lifecardsdk.data.model.response.default_.CardPackageServiceDetailResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

public class InfoPakageFragment extends BaseDataFragment<InfoPakageContract.Presenter> implements InfoPakageContract.View {
    private LinearLayout ll_card_Still_validated;
    private LinearLayout ll_card_Exprie, llInforPakageFragment, llLook;
    private TextView tvUseLimit;
    private TextView tvExpirationDate, tvLabelExpiration;
    private TextView tvLimitRemaining;
    private TextView tvDesc;
    private String cardNo = "",mainParentClass;
    private View viewVertical;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_info_package;
    }

    @Override
    protected void initView() {

        tvUseLimit = view.findViewById(R.id.tvUseLimit);
        tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        tvLimitRemaining = view.findViewById(R.id.tvLimitRemaining);
        tvDesc = view.findViewById(R.id.description);
        tvLabelExpiration = view.findViewById(R.id.tvLabelExpiration);
        ll_card_Still_validated = view.findViewById(R.id.ll_card_Still_validated);
        ll_card_Exprie = view.findViewById(R.id.ll_card_Exprie);
        llLook = view.findViewById(R.id.llLook);
        viewVertical = view.findViewById(R.id.viewVertical);
        llInforPakageFragment = view.findViewById(R.id.llInforPakageFragment);
    }

    @Override
    protected void initData() {
        mPresenter = new InfoPakagePresenter(mActivity, this);
        cardNo = getArguments().getString(Constants.BundleConstants.CODECARD);
        mainParentClass = getArguments().getString(Constants.BundleConstants.MAIN_PARENT_CLASS);

        if (PresenterUtils.isNetworkConnected(mActivity)) {
            showLoading(false);
            mPresenter.getDataInforCard(LCConfig.getPhoneNumber(), cardNo);
        } else {
            hideLoading();
            showError(getString(R.string.lifecardsdk_cant_connect_internet), "");
        }
//        showStatus(data.getStatus());
    }

    @Override
    protected void initAction() {

    }

    @Override
    public void setData(CardPackageServiceDetailResponseDefault data) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            description.setText(Html.fromHtml(data.getCardPackageServiceDto().getDescription() + data.getCardPackageServiceDto().getDescription(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            description.setText(Html.fromHtml(data.getCardPackageServiceDto().getDescription() +data.getCardPackageServiceDto().getDescription()));
//        }
        if (StringUtils.isEmpty(data.getOwnServiceDto().getDescription())) {
            tvDesc.setVisibility(View.GONE);
            viewVertical.setVisibility(View.GONE);
        } else {
            tvDesc.setVisibility(View.VISIBLE);
            viewVertical.setVisibility(View.VISIBLE);
            tvDesc.setText(StringUtils.convertHTMLToString(data.getOwnServiceDto().getDescription(), mActivity));
        }
        if (StringUtils.isEmpty(data.getOwnServiceDto().getLimitDetailDescription())) {
            tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(data.getOwnServiceDto().getOwnAccountDtos().get(0).getAllocatedUnit())));
        } else {
            tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<ul>" + data.getOwnServiceDto().getLimitDetailDescription() + "</ul>")));
        }
        tvLimitRemaining.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<b>" + data.getOwnServiceDto().getOwnAccountDtos().get(0).getAvailableUnit() + "</b>")));
        if (StringUtils.isEmpty(data.getOwnServiceDto().getExpirationDate())) {
            tvExpirationDate.setText("");
            tvExpirationDate.setVisibility(View.GONE);
            tvLabelExpiration.setVisibility(View.GONE);
        } else {
            tvExpirationDate.setVisibility(View.VISIBLE);
            tvLabelExpiration.setVisibility(View.VISIBLE);
            tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + data.getOwnServiceDto().getExpirationDate() + "</b>", mActivity));
        }
        showStatus(data.getOwnServiceDto().getStatus());
        llInforPakageFragment.setVisibility(View.VISIBLE);
    }

    public void showStatus(String s) {
// - nếu status = 'O' hoặc = 'A' thì hiển thị là đã thanh toán
// - nếu status = 'w' thì hiển thị là đang chờ thanh toán
// - nếu status = 'E' hoặc 'C' thì hiển thị là đã hết hạn
        if (s.equalsIgnoreCase(Constants.Status.A) || s.equalsIgnoreCase(Constants.Status.O)) {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.VISIBLE);
            llLook.setVisibility(View.GONE);
        } else if (s.equalsIgnoreCase(Constants.Status.E) || s.equalsIgnoreCase(Constants.Status.C)) {
            ll_card_Exprie.setVisibility(View.VISIBLE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llLook.setVisibility(View.GONE);
        } else {
            ll_card_Exprie.setVisibility(View.GONE);
            ll_card_Still_validated.setVisibility(View.GONE);
            llLook.setVisibility(View.VISIBLE);
        }
    }
}