package com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCard;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class GroupAdapter extends PagerAdapter {
    private List<DefCard> defCards;
    private LayoutInflater inflater;
    private Context context;
    private DefCardAdapter.OnClickItem onClickItem;
    private DefCardAdapter.OnClickLogo onClickLogo;
    private DefCardAdapter.OnClickPromotion onClickPromotion;
    private boolean showLogo;

    public GroupAdapter(List<DefCard> defCards, Context context, DefCardAdapter.OnClickItem onClickItem, DefCardAdapter.OnClickLogo onClickLogo, DefCardAdapter.OnClickPromotion onClickPromotion, boolean showLogo) {
        this.defCards = defCards;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onClickItem = onClickItem;
        this.onClickLogo = onClickLogo;
        this.onClickPromotion = onClickPromotion;
        this.showLogo = showLogo;
    }

    public void clearData() {
        defCards.clear();
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnClickLogoListener(DefCardAdapter.OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }


    @Override
    public int getCount() {
        return defCards.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.lifecard_item_card_group, view, false);
        assert imageLayout != null;

        TextView tvCardName = imageLayout.findViewById(R.id.tvCardName);
        TextView tvPriceOrigin = imageLayout.findViewById(R.id.tvPriceOrigin);
        TextView tvPriceNew = imageLayout.findViewById(R.id.tvPriceNew);
        TextView tvPromotion = imageLayout.findViewById(R.id.tvPromotion);
        RecyclerView rvPromotion = imageLayout.findViewById(R.id.rvPromotion);
        TextView tvExpandMore = imageLayout.findViewById(R.id.tvExpandMore);
        ImageView imgBackground = imageLayout.findViewById(R.id.imgBackground);
        RecyclerView rvLogo = imageLayout.findViewById(R.id.rvLogo);
        LinearLayout llPromotion = imageLayout.findViewById(R.id.llPromotion);
        TextView tvDescription = imageLayout.findViewById(R.id.tvDescription);
        NestedScrollView nsvLogo = imageLayout.findViewById(R.id.nsvLogo);
        final DefCard defCard = defCards.get(position);
        tvCardName.setText(defCard.getName());
        if (defCard.getPriceNumber().equals(defCard.getListedPriceNumber())) {
            tvPriceOrigin.setText("");
        } else {
            tvPriceOrigin.setText(defCard.getListedPrice());
            tvPriceOrigin.setPaintFlags(tvPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        tvPriceNew.setText(defCard.getPrice());
        if (StringUtils.isEmpty(defCard.getDescBrief())) {
            tvDescription.setText("");
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setText(StringUtils.convertHTMLToString(defCard.getDescBrief(), context));
            tvDescription.setVisibility(View.VISIBLE);
        }


        if (defCard.getPriceNumber() == 0 && defCard.getListedPriceNumber() == 0) {
            tvPriceNew.setVisibility(View.GONE);
            tvPriceOrigin.setVisibility(View.GONE);
        } else {
            if (Config.SHOW_LISTED_PRICE) {
                tvPriceOrigin.setVisibility(View.VISIBLE);
            } else {
                tvPriceOrigin.setVisibility(View.GONE);
            }
            tvPriceNew.setVisibility(View.VISIBLE);

        }

        if (defCard.getPromotionDtos() == null) {
            rvPromotion.setVisibility(View.GONE);
            tvExpandMore.setVisibility(View.GONE);
            tvExpandMore.setText("");
        } else {
            if (defCard.getPromotionDtos().size() == 0) {
                rvPromotion.setVisibility(View.GONE);
                tvExpandMore.setVisibility(View.GONE);
                tvExpandMore.setText("");
            } else {
                rvPromotion.setVisibility(View.VISIBLE);
                if (defCard.getPromotionDtos().size() > Config.MAX_ITEM_PROMOTION) {
                    tvExpandMore.setVisibility(View.VISIBLE);
                    tvExpandMore.setText(context.getString(R.string.lifecardsdk_common_expand_more, String.valueOf(defCard.getPromotionDtos().size() - Config.MAX_ITEM_PROMOTION)));
                } else {
                    tvExpandMore.setVisibility(View.GONE);
                    tvExpandMore.setText("");
                }
                PromotionAdapter promotionAdapter = new PromotionAdapter(context, defCard.getPromotionDtos(), defCard.getCode(), onClickPromotion);
                rvPromotion.setAdapter(promotionAdapter);
                rvPromotion.setLayoutManager(new LinearLayoutManager(context));
            }
        }
        GlideUtils.loadImageUrl(imgBackground, defCard.getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);

        if (defCard.getDiscountValueNumber() == 0) {
            llPromotion.setVisibility(View.GONE);
            tvPromotion.setText("");
        } else {
            tvPromotion.setText(defCard.getDiscountValue());
            llPromotion.setVisibility(View.VISIBLE);
        }
        if (showLogo) {
            nsvLogo.setVisibility(View.VISIBLE);
            rvLogo.setVisibility(View.VISIBLE);
            if (defCard.getProviderDtos().size() > 2) {
                ViewGroup.LayoutParams layoutParams = rvLogo.getLayoutParams();
                layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_170);
                rvLogo.setLayoutParams(layoutParams);
            } else {
                ViewGroup.LayoutParams layoutParams = rvLogo.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                rvLogo.setLayoutParams(layoutParams);
            }
            LogoAdapter logoAdapter = new LogoAdapter(context, defCard.getProviderDtos(), onClickLogo);
            rvLogo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rvLogo.setNestedScrollingEnabled(false);
            rvLogo.setAdapter(logoAdapter);
        } else {
            rvLogo.setVisibility(View.INVISIBLE);
            nsvLogo.setVisibility(View.INVISIBLE);
        }
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItem != null)
                    onClickItem.onClickBuyCard(defCard);
            }
        });


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

    public void changeData(List<DefCard> defCards) {
        this.defCards = defCards;
        notifyDataSetChanged();
    }
}
