package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentreceivegifts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.ui.base.BaseDataFragment;
import com.lpb.lifecardsdk.ui.home.fragment.mycard.mainmycard.MainMyCardsFragment;
import com.lpb.lifecardsdk.ui.payment.case2.notify.LCNotifyActivity;
import com.lpb.lifecardsdk.ui.provider.ProviderFragment;
import com.lpb.lifecardsdk.util.PresenterUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;

public class ReceiveGiftsFragment extends BaseDataFragment<ReceiveGiftsContract.Presenter> implements ReceiveGiftsContract.View {
    private ImageView imgBack;
    private TextView TVtemplatePromotion;
    private String TexttemplatePromotion;


    @Override
    protected int getLayoutId() {
        return R.layout.lifecardsdk_fragment_receive_gifts;
    }

    @Override
    protected void initView() {
        imgBack = view.findViewById(R.id.imgBack);
        TVtemplatePromotion = view.findViewById(R.id.TVtemplatePromotion);

    }

    @Override
    protected void initData() {
        mPresenter = new ReceiveGiftsPresenter(mActivity, this);
        final MyCardDetailsWaitResponseDefault data = (MyCardDetailsWaitResponseDefault) getArguments().getSerializable(Constants.BundleConstants.PROMOTIONDTOS);

//        StringBuilder text2 = new StringBuilder();

//        for (int i = 0; i < data.getOwnCardDto().getPromotionDtos().size(); i++) {
//            String t = "";
//            if (data.getOwnCardDto().getPromotionDtos().get(i).getProviderId() != null) {
//
//                t = data.getOwnCardDto().getPromotionDtos().get(i).getProviderName() + " " + data.getOwnCardDto().getPromotionDtos().get(i).getDescription();
//            }
//            text2.append(t);
//            if (i < data.getOwnCardDto().getPromotionDtos().size() - 1) {
//                text2.append(", ");
//            }
//        }
//        String texttemplateReceptPromotion = String.format(data.getOwnCardDto().getTemplateReceptPromotion(), text2);
//        Log.e("ReceiveGiftsPresenter2", "initData: " + StringUtils.convertHTMLToString(texttemplateReceptPromotion));
//        templateReceptPromotion.setText(StringUtils.convertHTMLToString(texttemplateReceptPromotion));

//        String texttemplateSupportPromotion = String.format(data.getOwnCardDto().getTemplateSupportPromotion(), data.getOwnCardDto().getHotline().toString());
//        templateSupportPromotion.setText(StringUtils.convertHTMLToString(texttemplateSupportPromotion));

//        TexttemplatePromotion = ("<html><head><title>Title of the document</title></head><body>" + data.getOwnCardDto().getTemplatePromotion() + "</body></html>");
        String s = ("<html><head><title>Title of the document</title></head><body><li>Bạn vui lòng tới bất kì chi nhánh nào của\n" +
                "\t<a href=\"https://provider.1\" style = \"text-decoration:underline;color:#ff5722\"> Goky Tea</a> (Tặng kèm 01 phiếu mua hàng tại Vingroup trị giá <b>500.000 đ</b>)\n" +
                "\n" +
                "\t<a href=\"https://provider.2\" style = \"text-decoration:underline;color:#ff5722\"> Goky Tea</a> (Tặng kèm 01 phiếu mua hàng tại Vingroup trị giá <b>500.000 đ</b>) hàng để nhận sản phẩm khuyến mãi đi kèm đơn.\n" +
                "</li>\n" +
                "\n" +
                "<li>Nếu có bất kỳ thắc mắc nào , vui lòng liên hệ tới hotline \n" +
                "<a href=\"https://phonenumber.0386138509\" style = \"text-decoration:underline;color:#ff5722\">0123456789</a> \n" +
                "để nhận được sự hỗ trợ cần thiết</li></body></html>");
//        TVtemplatePromotion.loadData(TexttemplatePromotion, "text/html; charset=utf-8", "utf-8");
//        tvTest.setWebViewClient(new MyWebViewClient(getContext()));
//        TVtemplatePromotion.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                if (url.startsWith("https://providerid.")) {
//                    Integer Id = Integer.valueOf(url.substring(19, url.length() - 1));
//                    ProviderFragment providerFragment = new ProviderFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(Constants.BundleConstants.PROVIDER_ID, Id);
//                    bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
//                    providerFragment.setArguments(bundle);
//                    MainMyCardsFragment.getInstance().addFragment(providerFragment, ReceiveGiftsFragment.this);
//                    return true;
//                } else if (url.startsWith("https://providernumber.")) {
//                    String phoneNumber = url.substring(24, url.length() - 1);
//                     PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
//                    return true;
//                }
//
//
//                return false;
//            }
//        });

        if (data.getOwnCardDto().getTemplatePromotion() != null) {
            TVtemplatePromotion.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml("<ul>"+data.getOwnCardDto().getTemplatePromotion()+"</ul>").setImageGetter(new HtmlResImageGetter(getContext()))));
            TVtemplatePromotion.setMovementMethod(new TextViewLinkHandler() {
                @Override
                public void onLinkClick(String url) {
                    if (url.startsWith("https://providerid.")) {
                        Integer Id = Integer.valueOf(url.substring(19));
                        ProviderFragment providerFragment = new ProviderFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.BundleConstants.PROVIDER_ID, Id);
                        bundle.putString(Constants.BundleConstants.MAIN_PARENT_CLASS, Constants.ParentClass.MainMyCardFragment);
                        providerFragment.setArguments(bundle);
                        MainMyCardsFragment.getInstance().addFragment(providerFragment, ReceiveGiftsFragment.this);
                    }else if (url.startsWith("https://providernumber.")) {
                        String phoneNumber = url.substring(23);
                        PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                    } else if (url.startsWith("https://phonenumber.")) {
                        String phoneNumber = url.substring(20);
                        PresenterUtils.callPhoneNumber(mActivity,phoneNumber);
                    }
                }
            });
        }
    }

    public void onClick(View arg0) {

    }

    @Override
    protected void initAction() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }
}
