package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.face_verify_infor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.base.DialogClickListener;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.StringUtils;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class FaceVerifyInforFragment extends BaseDataFragment<FaceVerfiryInforPresenter> implements FaceVerifyInforContract.View {
    private ImageView imgBack;
    private TextView tvTitleToolbar;
    private ImageView imgSpace;
    private TextView tvNotify;
    private TextView tvTime;
    private Button btnCancel;
    private ScrollView scDesc;
    private TextView tvDesc;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_face_verify_infor;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        tvTitleToolbar = view.findViewById(R.id.tvTitleToolbar);
        imgSpace = view.findViewById(R.id.imgSpace);
        tvNotify = view.findViewById(R.id.tvNotify);
        tvTime = view.findViewById(R.id.tvTime);
        btnCancel = view.findViewById(R.id.btnCancel);
        scDesc = view.findViewById(R.id.svDesc);
        tvDesc = view.findViewById(R.id.tvDesc);
        tvTitleToolbar.setText(getString(R.string.lifecardsdk_face_verify_infor));
    }

    @Override
    protected void initData() {
        mPresenter = new FaceVerfiryInforPresenter(mActivity, this);
        Bundle bundle = getArguments();
        String time = bundle.getString(Constants.BundleConstants.TIMES);
        String desc = bundle.getString(Constants.BundleConstants.DESC);
        String notify = bundle.getString(Constants.BundleConstants.NOTIFY);
        if (StringUtils.isEmpty(time))
            tvTime.setVisibility(View.GONE);
        else
            tvTime.setText(time);

        if (StringUtils.isEmpty(notify))
            tvNotify.setVisibility(View.GONE);
        else
            tvNotify.setText(notify);


        if (StringUtils.isEmpty(desc)) {
            scDesc.setVisibility(View.GONE);
        } else {
            scDesc.setVisibility(View.VISIBLE);
            tvDesc.setText(StringUtils.convertHTMLToString(desc,mActivity));
        }

    }

    public void backToHome(int tabPosition) {
        Intent intent = new Intent(mActivity, LCHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BundleConstants.TAB_POSITION, tabPosition);
        startActivity(intent);
    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mActivity.onBackPressed();
            }
        });
        btnCancel.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                showDialogConfirm(getString(R.string.lifecardsdk_face_cancel_verify), getString(R.string.lifecardsdk_face_cancel_verify_desc), getString(R.string.lifecardsdk_common_cancel), getString(R.string.lifecardsdk_common_accept), new DialogClickListener() {
                    @Override
                    public void close() {

                    }

                    @Override
                    public void allow() {
                        mPresenter.deleteFace();
                    }
                }, false, false);
            }
        });
    }
}
