package com.lpb.lifecardsdk.ui.payment.case2.payment.adapter;

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
import com.lpb.lifecardsdk.data.model.response.default_.InitPaymentResponse;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

/**
 * Created by vannh.lvt on 28/05/2020
 */
public class ProviderPageAdapter extends PagerAdapter {
    private List<InitPaymentResponse.ProviderDTO> providerDTOS;
    private LayoutInflater inflater;
    private Context context;
    private OnClickListener onClickListener;


    public ProviderPageAdapter(List<InitPaymentResponse.ProviderDTO> providerDTOS, Context context) {
        this.providerDTOS = providerDTOS;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
        View imageLayout = inflater.inflate(R.layout.lifecardsdk_item_page_provider, view, false);

        final InitPaymentResponse.ProviderDTO providerDTO = providerDTOS.get(position);
        ImageView imgLogo;
        TextView tvProviderName;
        TextView tvAddress;
        TextView tvPhoneNumber;
        TextView tvEmail;
        LinearLayout llPhoneNumber, llEmail;

        imgLogo = imageLayout.findViewById(R.id.imgLogo);
        tvProviderName = imageLayout.findViewById(R.id.tvProviderName);
        tvAddress = imageLayout.findViewById(R.id.tvAddress);
        tvPhoneNumber = imageLayout.findViewById(R.id.tvPhoneNumber);
        tvEmail = imageLayout.findViewById(R.id.tvEmail);
        llPhoneNumber = imageLayout.findViewById(R.id.llPhoneNumber);
        llEmail = imageLayout.findViewById(R.id.llEmail);

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


        tvProviderName.setText(providerDTO.getName());

        GlideUtils.loadImageUrl(imgLogo, providerDTO.getLogo(), context, Constants.PlaceHolderType.LOGO_PROVIDER);

        view.addView(imageLayout, 0);
        return imageLayout;
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

    public void changeData(List<InitPaymentResponse.ProviderDTO> providerDTOS) {
        this.providerDTOS = providerDTOS;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClickPhone(String phoneNumber);
        void onClickEmail(String email);
    }

}
