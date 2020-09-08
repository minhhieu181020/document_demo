package com.lpb.lifecardsdk.ui.home.fragment.mycodev2.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListMyCardCodeResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

import java.util.List;

public class MyCodeV2Adapter extends PagerAdapter {
    private List<ListMyCardCodeResponseDefault.ListActiveCardDto> expireCardList;
    private LayoutInflater inflater;
    private Context context;
    private OnClickListener onClickListener;


    public MyCodeV2Adapter(List<ListMyCardCodeResponseDefault.ListActiveCardDto> expireCardList, Context context) {
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

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClickItem(expireCardList);
            }
        });
        GlideUtils.loadImageUrl(imgBackgroud, expireCardList.get(position).getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);
        tvNumberCard.setText(expireCardList.get(position).getCardNoDisplay());
        tvNameCard.setText(expireCardList.get(position).getName());
        if (expireCardList.get(position).getStyleDto()==null){

        }else{
            TextViewUtils.setStyleTextView(tvNumberCard, context, null, expireCardList.get(position).getStyleDto().getRgb());
        }
        tvNumberCard.setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_22));
        tvNumberCard.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_sp_18));

        tvContent.setText(expireCardList.get(position).getName());
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

    public void changeData(List<ListMyCardCodeResponseDefault.ListActiveCardDto> expireCardList) {
        this.expireCardList = expireCardList;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClickItem(ListMyCardCodeResponseDefault.ListActiveCardDto item);

        void onClickItem(List<ListMyCardCodeResponseDefault.ListActiveCardDto> expireCardList);
    }
}
