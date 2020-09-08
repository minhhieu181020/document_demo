package com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
    private List<PackageSearchResponse.PromotionDto> promotionDtos;
    private Context context;
    private BuyCardAdapter.OnClickPromotion onClickPromotion;
    private String code;

    PromotionAdapter(Context context, List<PackageSearchResponse.PromotionDto> promotionDtos, String code, BuyCardAdapter.OnClickPromotion onClickPromotion) {
        this.promotionDtos = promotionDtos;
        this.context = context;
        this.onClickPromotion = onClickPromotion;
        this.code = code;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_promotion, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtils.loadImageUrl(holder.imgPromotion, promotionDtos.get(position).getUrlIcon(), context, Constants.PlaceHolderType.LOGO_PROMOTION);
        holder.tvPromotion.setText(StringUtils.convertHTMLToString(promotionDtos.get(position).getDescription(), context));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickPromotion != null)
                    onClickPromotion.onClickPromotion(code);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (promotionDtos == null ? 0 : (promotionDtos.size() < Config.MAX_ITEM_PROMOTION ? promotionDtos.size() : Config.MAX_ITEM_PROMOTION));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPromotion;
        final TextView tvPromotion;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPromotion = itemView.findViewById(R.id.imgPromotion);
            tvPromotion = itemView.findViewById(R.id.tvPromotion);
        }
    }

}