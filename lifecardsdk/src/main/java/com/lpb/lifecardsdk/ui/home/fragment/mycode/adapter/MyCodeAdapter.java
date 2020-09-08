package com.lpb.lifecardsdk.ui.home.fragment.mycode.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

import java.util.List;

public class MyCodeAdapter extends PagerAdapter {
    private List<ListCardResponseDefault.OwnCardDto> expireCardList;
    private LayoutInflater inflater;
    private Context context;
    private OnClickLogo onClickLogo;


    public MyCodeAdapter(List<ListCardResponseDefault.OwnCardDto> expireCardList, Context context) {
        this.expireCardList = expireCardList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void clearData() {
        expireCardList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnClickLogoListener(OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }


    @Override
    public int getCount() {
        return expireCardList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.lifecardsdk_item_card_my_recycler, view, false);

        assert imageLayout != null;
        final ImageView imgBackgroud = (ImageView) imageLayout
                .findViewById(R.id.imgBackgroundCard);

        final AutofitTextView tvNumberCard = imageLayout.findViewById(R.id.tvNumberCard);
        final TextView tvNameCard = imageLayout.findViewById(R.id.tvNameCards);
        final TextView tvContent = imageLayout.findViewById(R.id.tvContent);
        RecyclerView rvLogoCard = imageLayout.findViewById(R.id.rvLogoCard);
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (expireCardList.get(position).getStatus().equalsIgnoreCase("E") || expireCardList.get(position).getStatus().equalsIgnoreCase("C")) {
            GlideUtils.loadImageUrl(imgBackgroud, expireCardList.get(position).getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);
            tvNumberCard.setText(expireCardList.get(position).getCardNoDisplay());
            tvNameCard.setText(expireCardList.get(position).getCustomerName());
            TextViewUtils.setStyleTextView(tvNumberCard,context,expireCardList.get(position).getStyleDto().getAppStyle(),expireCardList.get(position).getStyleDto().getRgb());
            tvContent.setText(expireCardList.get(position).getName());
//            LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, expireCardList.get(position).getProviderDtos(), true, null, onClickLogo);
//            rvLogoCard.setAdapter(logoCardAdapter);
//            rvLogoCard.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            view.addView(imageLayout, 0);
        } else if (expireCardList.get(position).getStatus().equalsIgnoreCase("A") || (expireCardList.get(position).getStatus().equalsIgnoreCase("O"))) {
            GlideUtils.loadImageUrl(imgBackgroud, expireCardList.get(position).getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);
            tvNumberCard.setText(expireCardList.get(position).getCardNoDisplay());
            tvNameCard.setText(expireCardList.get(position).getCustomerName());
            TextViewUtils.setStyleTextView(tvNumberCard,context,expireCardList.get(position).getStyleDto().getAppStyle(),expireCardList.get(position).getStyleDto().getRgb());
            tvContent.setText(expireCardList.get(position).getName());
//            LogoCardAdapter logoCardAdapter = new LogoCardAdapter(context, expireCardList.get(position).getProviderDtos(), true, null, onClickLogo);
//            rvLogoCard.setAdapter(logoCardAdapter);
//            rvLogoCard.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            view.addView(imageLayout, 0);
        }
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

    public void changeData(List<ListCardResponseDefault.OwnCardDto> expireCardList) {
        this.expireCardList = expireCardList;
        notifyDataSetChanged();
    }

    public interface OnClickLogo {
        void onClickItem(ListCardResponseDefault.ProviderDto item);
    }
}
