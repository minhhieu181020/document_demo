package com.lpb.lifecardsdk.ui.provider.fragment.introduce;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderResponse;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.StringUtils;

public class IntroduceFragment extends BaseDataFragment<IntroducePresenter> implements IntroduceContract.View {
    private TextView tvEmail;
    private TextView tvWebsite;
    private TextView tvFacebook;
    private TextView tvIntroduction;
    private NestedScrollView nsvContainer;
    private LinearLayout llEmail;
    private LinearLayout llWebsite;
    private LinearLayout llFacebook;
    private LinearLayout llIntroduce;

    private String email;
    private String facebook;
    private String website;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_provider_introduce;
    }

    @Override
    protected void initView() {
        tvEmail = view.findViewById(R.id.tvEmail);
        tvWebsite = view.findViewById(R.id.tvWebsite);
        tvFacebook = view.findViewById(R.id.tvFacebook);
        tvIntroduction = view.findViewById(R.id.tvIntroduction);
        nsvContainer = view.findViewById(R.id.nsvContainer);
        llEmail = view.findViewById(R.id.llEmail);
        llWebsite = view.findViewById(R.id.llWebsite);
        llFacebook = view.findViewById(R.id.llFacebook);
        llIntroduce = view.findViewById(R.id.llIntroduce);
    }

    @Override
    protected void initData() {
        mPresenter = new IntroducePresenter(mActivity, this);
        ProviderResponse data = (ProviderResponse) getArguments().getSerializable(Constants.BundleConstants.PROVIDER);
        setDataIntroduce(data);
    }

    @Override
    protected void initAction() {
        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(PresenterUtils.openFacebookApp(mActivity.getPackageManager(),facebook));
                }catch (Exception ignored){

                }

            }
        });
        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(PresenterUtils.openLinkUrl(website));
                }catch (Exception ignored){

                }
            }
        });
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(PresenterUtils.openEmailApp(email));
                }catch (Exception ignored){

                }
            }
        });
    }

    @Override
    public void setDataIntroduce(final ProviderResponse data) {
        nsvContainer.setVisibility(View.VISIBLE);

         email=data.getList().get(0).getEmail();
         facebook=data.getList().get(0).getFacebook();
         website=data.getList().get(0).getWebsite();
        String introduce = data.getList().get(0).getIntroduction();

        tvEmail.setText(email);
        tvFacebook.setText(facebook);
        tvWebsite.setText(website);
        tvIntroduction.setText(StringUtils.convertHTMLToString(introduce,mActivity));

        if (StringUtils.isEmpty(email))
            llEmail.setVisibility(View.GONE);
        if (StringUtils.isEmpty(facebook))
            llFacebook.setVisibility(View.GONE);
        if (StringUtils.isEmpty(website))
            llWebsite.setVisibility(View.GONE);
        if (StringUtils.isEmpty(introduce))
            llIntroduce.setVisibility(View.GONE);
    }
}
