package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetailswait.card_information.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CardInforWaitAdapter extends RecyclerView.Adapter<CardInforWaitAdapter.ViewHolder> {
    private List<MyCardDetailsWaitResponseDefault.OwnServiceDto> packageItemList;

    private OnClickListener onClickListener;
    private Context context;
    private OnClickLogo onClickLogo;

    public CardInforWaitAdapter(Context context) {
        packageItemList = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<MyCardDetailsWaitResponseDefault.OwnServiceDto> items) {
        packageItemList.clear();
        packageItemList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_card_wait_pake, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnClickLogoListener(OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MyCardDetailsWaitResponseDefault.OwnServiceDto packageItem = packageItemList.get(position);

        holder.tvName.setText(packageItem.getNamDefService());

        holder.tvPriceNew.setText(packageItem.getPrice());
        if (packageItem.getPriceNumber().equals(packageItem.getListedPriceNumber())) {
            holder.tvPriceOrigin.setText("");
        } else {
            holder.tvPriceOrigin.setText(packageItem.getListedPrice());
            holder.tvPriceOrigin.setPaintFlags(holder.tvPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (packageItem.getPriceNumber() == 0 && packageItem.getListedPriceNumber() == 0) {
            holder.tvPriceNew.setVisibility(View.GONE);
            holder.tvPriceOrigin.setVisibility(View.GONE);
        } else {
            holder.tvPriceNew.setVisibility(View.VISIBLE);
            if (Config.SHOW_LISTED_PRICE) {
                holder.tvPriceOrigin.setVisibility(View.VISIBLE);
            } else {
                holder.tvPriceOrigin.setVisibility(View.GONE);
            }
        }
        if (StringUtils.isEmpty(packageItem.getExpiryDate())){
            holder.tvExpirationDate.setText("");
            holder.tvExpirationDate.setVisibility(View.GONE);
            holder.tvLabelExpiration.setVisibility(View.GONE);
        }else {
            holder.tvExpirationDate.setVisibility(View.VISIBLE);
            holder.tvLabelExpiration.setVisibility(View.VISIBLE);
            holder.tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + packageItem.getExpiryDate() + "</b>", context));

        }

        if (!StringUtils.isEmpty(packageItem.getUsageDescription())) {
            holder.tvUseLimit.setText(StringUtils.convertHTMLToString(packageItem.getUsageDescription(), context));
        } else {
            holder.tvUseLimit.setText(StringUtils.convertHTMLToString("<b>"+packageItem.getOwnAccountDtos().get(0).getAllocatedUnit()+"</b>",context));
        }
        if (packageItem.getOwnAccountDtos().get(0).getUnitCode().equals(Constants.UnitCode.TIMES)) {
            holder.tvUseTimes.setVisibility(View.VISIBLE);
            holder.tvUseTimes.setText(packageItem.getUnitPrice()+" x "+packageItem.getOwnAccountDtos().get(0).getAllocatedUnit());
        } else {
            holder.tvUseTimes.setVisibility(View.GONE);
            holder.tvUseTimes.setText("");
        }
        GlideUtils.loadImageUrl(holder.imgLogo,packageItem.getProviderDto().getLogo(),context,Constants.PlaceHolderType.LOGO_PROVIDER);

        holder.llDetails.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClickItem(packageItem);
            }
        });
        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClickItem(packageItem);
            }
        });
        holder.imgLogo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (onClickLogo != null)
                    onClickLogo.onClickItem(packageItem.getProviderDto());
            }
        });
    }


    @Override
    public int getItemCount() {
        if (packageItemList == null) return 0;
        return packageItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPriceNew, tvPriceOrigin, tvUseLimit, tvExpirationDate,tvUseTimes,tvLabelExpiration;
        private LinearLayout llDetails;
        private ImageView imgLogo;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            tvPriceOrigin = itemView.findViewById(R.id.tvPriceOrigin);
            tvUseLimit = itemView.findViewById(R.id.tvUseLimit);
            tvExpirationDate = itemView.findViewById(R.id.tvExpirationDate);
            llDetails = itemView.findViewById(R.id.llDetails);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvUseTimes = itemView.findViewById(R.id.tvUseTimes);
            tvLabelExpiration = itemView.findViewById(R.id.tvLabelExpiration);
        }
    }

    public interface OnClickListener {
        void onClickItem(MyCardDetailsWaitResponseDefault.OwnServiceDto item);
    }

    public interface OnClickLogo {
        void onClickItem(MyCardDetailsWaitResponseDefault.ProviderDto item);
    }
}
