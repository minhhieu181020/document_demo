package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.fragment.transfer;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.exception.Exception;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.LCHomeActivity;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;


public class TransferFragment extends BaseDataFragment<TransferPresenter> implements TransferContract.View {
    private TextView tvGuide;
    private TextView tvContent;
    private LinearLayout llCopy;
    private TextView tvNote;

    private String noteTransfer,message,transferInstruction;

    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_payment_transfer;
    }

    @Override
    protected void initView() {
        tvGuide = view.findViewById(R.id.tvGuide);
        tvContent = view.findViewById(R.id.tvContent);
        llCopy = view.findViewById(R.id.llCopy);
        tvNote = view.findViewById(R.id.tvNote);
    }

    @Override
    protected void initData() {
        mPresenter = new TransferPresenter(mActivity, this);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        noteTransfer = bundle.getString("noteTransfer");
        message = bundle.getString("message");
        transferInstruction = bundle.getString("transferInstruction");
        tvContent.setText(message);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initAction() {
        llCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("content", message);
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Exception.handleMessageSuccess(getActivity(), getString(R.string.lifecardsdk_payment_guide_has_copied));
            }
        });


        tvGuide.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(transferInstruction).setImageGetter(new HtmlResImageGetter(mActivity.getApplicationContext()))));

        tvNote.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(noteTransfer).setImageGetter(new HtmlResImageGetter(mActivity.getApplicationContext()))));
        tvNote.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                if (url.startsWith("https://mycard.")) {
                        Intent intent = new Intent(mActivity, LCHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constants.BundleConstants.TAB_POSITION, 1);
                        startActivity(intent);
                } else if (url.startsWith("https://providernumber.")) {
                    String phoneNumber = url.substring(23);
                    PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                }else if (url.startsWith("https://phonenumber.")) {
                    String phoneNumber = url.substring(20);
                    PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                }
            }
        });


    }
}
