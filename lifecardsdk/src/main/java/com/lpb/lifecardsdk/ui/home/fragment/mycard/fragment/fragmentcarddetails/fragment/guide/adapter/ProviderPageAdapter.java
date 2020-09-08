package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.fragment.guide.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ProviderDTO;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.util.TextViewLinkHandler;

import java.util.List;

/**
 * Created by vannh.lvt on 18/06/2020
 */
public class ProviderPageAdapter extends PagerAdapter {
    private List<ProviderDTO> providerDTOS;
    private LayoutInflater inflater;
    private Context context;
    private OnClickListener onClickListener;
    private int layout;

    public ProviderPageAdapter(List<ProviderDTO> providerDTOS, Context context,int layout ) {
        this.providerDTOS = providerDTOS;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.layout = layout;
    }

    public void clearData() {
        providerDTOS.clear();
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnClickLogoListener(OnClickListener onClickLogo) {
        this.onClickListener = onClickLogo;
    }


    @Override
    public int getCount() {
        return providerDTOS.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View itemLayout = inflater.inflate(layout, view, false);

        final ProviderDTO providerDTO = providerDTOS.get(position);
        ImageView imgLogo;
        TextView tvProviderName;
        TextView tvAddress;
        TextView tvPhoneNumber;
        TextView tvEmail, tvGuide;
        LinearLayout llPhoneNumber, llEmail,llProvider;
        View viewLine;

        imgLogo = itemLayout.findViewById(R.id.imgLogo);
        tvProviderName = itemLayout.findViewById(R.id.tvProviderName);
        tvAddress = itemLayout.findViewById(R.id.tvAddress);
        tvPhoneNumber = itemLayout.findViewById(R.id.tvPhoneNumber);
        tvEmail = itemLayout.findViewById(R.id.tvEmail);
        llPhoneNumber = itemLayout.findViewById(R.id.llPhoneNumber);
        llEmail = itemLayout.findViewById(R.id.llEmail);
        tvGuide = itemLayout.findViewById(R.id.tvGuide);
        viewLine = itemLayout.findViewById(R.id.viewLine);
        llProvider = itemLayout.findViewById(R.id.llProvider);
        tvAddress.setText(providerDTO.getHeadquarterAddress());


        if (StringUtils.isEmpty(providerDTO.getHeadquarterPhone()))
            llPhoneNumber.setVisibility(View.GONE);
        else
            tvPhoneNumber.setText(StringUtils.underlineText(providerDTO.getHeadquarterPhone()));

        if (StringUtils.isEmpty(providerDTO.getEmail()))
            llEmail.setVisibility(View.GONE);
        else
            tvEmail.setText(StringUtils.underlineText(providerDTO.getEmail()));

        llEmail.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickEmail(providerDTO.getEmail());
            }
        });

        llPhoneNumber.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickPhone(providerDTO.getHeadquarterPhone());
            }
        });
        if (StringUtils.isEmpty(providerDTO.getUsageGuide())) {
            tvGuide.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            tvGuide.setText("");
        } else {
            tvGuide.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            tvGuide.setText(StringUtils.convertHTMLToString("<ul>" + providerDTO.getUsageGuide() + "</ul>", context));
        }
        llProvider.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickLogoProvider(providerDTO.getId());
            }
        });
        tvGuide.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                if (url.startsWith("https://providernumber.")) {
                    String phoneNumber = url.substring(23);
                    onClickListener.onClickPhone(phoneNumber);
                } else if (url.startsWith("https://phonenumber.")) {
                    String phoneNumber = url.substring(20);
                    onClickListener.onClickPhone(phoneNumber);
                }
            }
        });
        imgLogo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickLogoProvider(providerDTO.getId());
            }
        });
        tvProviderName.setText(providerDTO.getName());

        GlideUtils.loadImageUrl(imgLogo, providerDTO.getLogo(), context, Constants.PlaceHolderType.LOGO_PROVIDER);

        view.addView(itemLayout, 0);
        return itemLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void changeData(List<ProviderDTO> providerDTOS) {
        this.providerDTOS = providerDTOS;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClickPhone(String phoneNumber);

        void onClickEmail(String email);

        void onClickLogoProvider(Integer providerID);
    }

}
