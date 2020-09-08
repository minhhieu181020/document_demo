package com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter;

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
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BuyCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PackageSearchResponse.DefCardDto> packageDtoList;
    private OnClickListener onClickListener;
    private OnClickLogo onClickLogo;
    private OnClickPromotion onClickPromotion;
    private Context context;
    private boolean onLoadMore = true;
    private boolean showLogo;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public BuyCardAdapter(Context context, boolean showLogo) {
        this.showLogo = showLogo;
        packageDtoList = new ArrayList<>();
        this.context = context;
    }

    public void clearData() {
        packageDtoList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<PackageSearchResponse.DefCardDto> items) {
        packageDtoList.addAll(items);
        notifyDataSetChanged();
    }

    public void setItems(List<PackageSearchResponse.DefCardDto> items) {
        packageDtoList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_buy_card, parent, false);
            return new ViewHolder(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadHolder(itemView);
        }

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
        if (holder instanceof ViewHolder) {
            final PackageSearchResponse.DefCardDto packageDto = packageDtoList.get(position);
            ((ViewHolder) holder).setItem(packageDto);
        }
    }


    @Override
    public int getItemCount() {
        if (packageDtoList == null) return 0;
        return packageDtoList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == packageDtoList.size() - 1) return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvCardName, tvPriceOrigin, tvPriceNew, tvPromotion, tvExpandMore, tvDescription;
        final Button btnBuyCard;
        final ImageView imgBackground;
        final RecyclerView rvLogo, rvPromotion;
        final LinearLayout llPromotion;

        ViewHolder(@NonNull View itemView) {
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
        public void setItem(final PackageSearchResponse.DefCardDto packageDto) {
            tvCardName.setText(packageDto.getName());
            if (packageDto.getPriceNumber().equals(packageDto.getListedPriceNumber())) {
                tvPriceOrigin.setText("");
            } else {
                tvPriceOrigin.setText(packageDto.getListedPrice());
                tvPriceOrigin.setPaintFlags(tvPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            tvPriceNew.setText(packageDto.getPrice());
            if (StringUtils.isEmpty(packageDto.getDescBrief())) {
                tvDescription.setText("");
                tvDescription.setVisibility(View.GONE);
            } else {
                tvDescription.setText(StringUtils.convertHTMLToString(packageDto.getDescBrief(), context));
                tvDescription.setVisibility(View.VISIBLE);
            }


            if (packageDto.getPriceNumber() == 0 && packageDto.getListedPriceNumber() == 0) {
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

            if (packageDto.getPromotionDto() == null) {
                rvPromotion.setVisibility(View.GONE);
                tvExpandMore.setVisibility(View.GONE);
                tvExpandMore.setText("");
            } else {
                if (packageDto.getPromotionDto().size() == 0) {
                    rvPromotion.setVisibility(View.GONE);
                    tvExpandMore.setVisibility(View.GONE);
                    tvExpandMore.setText("");
                } else {
                    rvPromotion.setVisibility(View.VISIBLE);
                    if (packageDto.getPromotionDto().size() > Config.MAX_ITEM_PROMOTION) {
                        tvExpandMore.setVisibility(View.VISIBLE);
                        tvExpandMore.setText(context.getString(R.string.lifecardsdk_common_expand_more, String.valueOf(packageDto.getPromotionDto().size() - Config.MAX_ITEM_PROMOTION)));
                    } else {
                        tvExpandMore.setVisibility(View.GONE);
                        tvExpandMore.setText("");
                    }
                    PromotionAdapter promotionAdapter = new PromotionAdapter(context, packageDto.getPromotionDto(), packageDto.getCode(), onClickPromotion);
                    rvPromotion.setAdapter(promotionAdapter);
                    rvPromotion.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                }
            }
            GlideUtils.loadImageUrl(imgBackground, packageDto.getImage(), context, Constants.PlaceHolderType.BACKGROUND_CARD);

            if (packageDto.getDiscountValueNumber() == 0) {
                llPromotion.setVisibility(View.GONE);
                tvPromotion.setText("");
            } else {
                tvPromotion.setText(packageDto.getDiscountValue());
                llPromotion.setVisibility(View.VISIBLE);
            }
            if (showLogo) {
                rvLogo.setVisibility(View.VISIBLE);
                if (packageDto.getProviderDtos().size() > 2) {
                    ViewGroup.LayoutParams layoutParams = rvLogo.getLayoutParams();
                    layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.lifecardsdk_dp_170);
                    rvLogo.setLayoutParams(layoutParams);
                } else {
                    ViewGroup.LayoutParams layoutParams = rvLogo.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    rvLogo.setLayoutParams(layoutParams);
                }
                LogoAdapter logoAdapter = new LogoAdapter(context, packageDto.getProviderDtos(), onClickLogo);
                rvLogo.setAdapter(logoAdapter);
                rvLogo.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            } else {
                rvLogo.setVisibility(View.INVISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null)
                        onClickListener.onClickBuyCard(packageDto);
                }
            });
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View view) {
            super(view);
        }
    }

    public interface OnClickListener {
        void onClickBuyCard(PackageSearchResponse.DefCardDto item);
    }

    public interface OnClickLogo {
        void onClickItem(PackageSearchResponse.ProviderDto item);
    }

    public interface OnClickPromotion {
        void onClickPromotion(String code);
    }
}
