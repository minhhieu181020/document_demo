package com.lpb.lifecardsdk.ui.home.fragment.buycard.defcard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardGroup;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.CardListConfig;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.DefCard;
import com.lpb.lifecardsdk.data.model.response.default_.defcard.ProviderDto;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.ScreenUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.CustomWrapViewPager;
import com.lpb.lifecardsdk.widget.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class DefCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CardListConfig> cardListConfigs;
    private OnClickItem onClickItem;
    private OnClickLogo onClickLogo;
    private OnClickPromotion onClickPromotion;
    private Context context;
    private boolean onLoadMore = true;
    private boolean showLogo;

    private static final int CARD = 0;
    private static final int GROUP = 1;
    public static final int LOAD_MORE = 2;
    public static final String TYPE_CARD = "CARD";
    public static final String TYPE_GROUP = "GROUP";

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public DefCardAdapter(Context context, boolean showLogo) {
        this.showLogo = showLogo;
        cardListConfigs = new ArrayList<>();
        this.context = context;
    }

    public void clearData() {
        cardListConfigs.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<CardListConfig> items) {
        cardListConfigs.addAll(items);
        notifyDataSetChanged();
    }

    public void setItems(List<CardListConfig> items) {
        cardListConfigs = items;
//        for (CardListConfig cardListConfig: cardListConfigs){
//            if (cardListConfig.getType().equalsIgnoreCase(TYPE_GROUP)){
//                DefCard objFirst = cardListConfig.getCardGroup().getDefCard().get(0);
//                cardListConfig.getCardGroup().getDefCard().add(objFirst);
//                DefCard objLast = cardListConfig.getCardGroup().getDefCard().get(cardListConfig.getCardGroup().getDefCard().size() - 2);
//                cardListConfig.getCardGroup().getDefCard().add(0, objLast);
//            }
//
//        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == CARD) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_buy_card, parent, false);
            return new Card(itemView);
        } else if (viewType == GROUP) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_def_card_type_group, parent, false);
            return new Group(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadMore(itemView);
        }
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnClickLogoListener(OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }

    public void setOnClickPromotion(OnClickPromotion onClickPromotion) {
        this.onClickPromotion = onClickPromotion;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Card) {
            final DefCard defcard = cardListConfigs.get(position).getDefcard();
            ((Card) holder).setDataCard(defcard);
        } else if (holder instanceof Group) {
            final CardGroup cardGroup = cardListConfigs.get(position).getCardGroup();
            ((Group) holder).setDataGroup(cardGroup);
        }
    }


    @Override
    public int getItemCount() {
        if (cardListConfigs == null) return 0;
        return cardListConfigs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == cardListConfigs.size() - 1) {
                return LOAD_MORE;
            } else {
                return getViewType(cardListConfigs.get(position).getType());
            }
        } else {
            return getViewType(cardListConfigs.get(position).getType());
        }
    }

    private int getViewType(String type) {
        if (StringUtils.isEmpty(type)) {
            return LOAD_MORE;
        } else {
            if (type.equalsIgnoreCase(TYPE_CARD)) {
                return CARD;
            } else if (type.equalsIgnoreCase(TYPE_GROUP)) {
                return GROUP;
            } else {
                return LOAD_MORE;
            }
        }
    }

    class Card extends RecyclerView.ViewHolder {

        final TextView tvCardName, tvPriceOrigin, tvPriceNew, tvPromotion, tvExpandMore, tvDescription;
        final Button btnBuyCard;
        final ImageView imgBackground;
        final RecyclerView rvLogo, rvPromotion;
        final LinearLayout llPromotion;

        Card(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvPriceOrigin = itemView.findViewById(R.id.tvPriceOrigin);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            tvPromotion = itemView.findViewById(R.id.tvPromotion);
            rvPromotion = itemView.findViewById(R.id.rvPromotion);
            tvExpandMore = itemView.findViewById(R.id.tvExpandMore);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            rvLogo = itemView.findViewById(R.id.rvLogo);
            btnBuyCard = itemView.findViewById(R.id.btnBuyCard);
            llPromotion = itemView.findViewById(R.id.llPromotion);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        @SuppressLint("SetTextI18n")
        public void setDataCard(final DefCard defCard) {
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
                    rvPromotion.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
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
                rvLogo.setAdapter(logoAdapter);
                rvLogo.setNestedScrollingEnabled(false);
                rvLogo.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            } else {
                rvLogo.setVisibility(View.INVISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickItem != null)
                        onClickItem.onClickBuyCard(defCard);
                }
            });
        }
    }

    class Group extends RecyclerView.ViewHolder {
        private TextView tvGroupName;
        private CustomWrapViewPager vpGroupCard;
        private CirclePageIndicator cpiGroupCard;

        Group(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            vpGroupCard = itemView.findViewById(R.id.vpGroupCard);
            cpiGroupCard = itemView.findViewById(R.id.cpiGroupCard);
            vpGroupCard = itemView.findViewById(R.id.vpGroupCard);


        }

        @SuppressLint("SetTextI18n")
        public void setDataGroup(final CardGroup cardGroup) {
            tvGroupName.setText(cardGroup.getName());

            cpiGroupCard.setRadius(ScreenUtils.sdpToPixel(context, 4, 4));

            GroupAdapter groupAdapter = new GroupAdapter(cardGroup.getDefCard(), context, onClickItem, onClickLogo, onClickPromotion, showLogo);
            vpGroupCard.setAdapter(groupAdapter);
            cpiGroupCard.setViewPager(vpGroupCard);
            cpiGroupCard.setCentered(false);
//            vpGroupCard.setCurrentItem(1);
//            vpGroupCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//                    if (state == ViewPager.SCROLL_STATE_IDLE) {
//                        int curr = vpGroupCard.getCurrentItem();
//                        int lastReal = vpGroupCard.getAdapter().getCount() - 2;
//                        if (curr == 0) {
//                            vpGroupCard.setCurrentItem(lastReal, false);
//                        } else if (curr > lastReal) {
//                            vpGroupCard.setCurrentItem(1, false);
//                        }
//                    }
//                }
//            });
        }
    }

    class LoadMore extends RecyclerView.ViewHolder {
        LoadMore(View view) {
            super(view);
        }
    }


    public interface OnClickItem {
        void onClickBuyCard(DefCard item);
    }

    public interface OnClickLogo {
        void onClickItem(ProviderDto item);
    }

    public interface OnClickPromotion {
        void onClickPromotion(String code);
    }
}
