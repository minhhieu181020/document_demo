package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentcarddetails.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsInForAdapter extends RecyclerView.Adapter<CardDetailsInForAdapter.ViewHolder> {
    private List<MyCardDetailsWaitResponseDefault.OwnServiceDto> cardDeatilsInFors;

    private OnClickListener onClickListener;
    private OnClickLogo onClickLogo;
    private Context context;

    public CardDetailsInForAdapter(Context context) {
        cardDeatilsInFors = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<MyCardDetailsWaitResponseDefault.OwnServiceDto> items) {
        cardDeatilsInFors.clear();
        cardDeatilsInFors.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_card_details, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnClickLogoListener(OnClickLogo onClickLogo) {
        this.onClickLogo = onClickLogo;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MyCardDetailsWaitResponseDefault.OwnServiceDto cardDeatilsInFor = cardDeatilsInFors.get(position);
        if (cardDeatilsInFors.get(position).getProviderDto() == null) {

        } else {
            GlideUtils.loadImageUrl(holder.imgLogo, cardDeatilsInFors.get(position).getProviderDto().getLogo(), context, Constants.PlaceHolderType.LOGO_PROVIDER);
        }

//        holder.imgLogo.setImageResource(imgLogo);


        holder.tvName.setText(cardDeatilsInFor.getNamDefService());

        if (cardDeatilsInFor.getOwnAccountDtos().size() != 0) {
            if (StringUtils.isEmpty(cardDeatilsInFor.getUsageDescription())) {
                holder.tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(cardDeatilsInFor.getOwnAccountDtos().get(0).getAllocatedUnit())));
            } else {
                holder.tvUseLimit.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(cardDeatilsInFor.getUsageDescription())));
            }
            holder.tvLimitRemaining.setText(StringUtils.convertHTMLToString("<b>" + cardDeatilsInFor.getOwnAccountDtos().get(0).getAvailableUnit() + "</b>", context));
        } else {
            holder.tvUseLimit.setText("");
            holder.tvLimitRemaining.setText("");
        }
        if (StringUtils.isEmpty(cardDeatilsInFor.getExpirationDate())) {
            holder.tvExpirationDate.setText("");
            holder.tvExpirationDate.setVisibility(View.GONE);
            holder.tvLabelExpiration.setVisibility(View.GONE);
        } else {
            holder.tvExpirationDate.setVisibility(View.VISIBLE);
            holder.tvLabelExpiration.setVisibility(View.VISIBLE);
            holder.tvExpirationDate.setText(StringUtils.convertHTMLToString("<b>" + cardDeatilsInFor.getExpirationDate() + "</b>", context));
        }
        Log.e("ListIDCardDeatilInFor", "ID: " + cardDeatilsInFor.getId());
        if (cardDeatilsInFor.getStatus().equals("A") || cardDeatilsInFor.getStatus().equals("O")) {
            holder.ll_card_Exprie.setVisibility(View.GONE);
            holder.ll_card_Still_validated.setVisibility(View.VISIBLE);
            holder.llLook.setVisibility(View.GONE);
            holder.imgQRcode.setVisibility(View.VISIBLE);
        } else if (cardDeatilsInFor.getStatus().equals("E") || cardDeatilsInFor.getStatus().equals("C")) {
            holder.ll_card_Exprie.setVisibility(View.VISIBLE);
            holder.ll_card_Still_validated.setVisibility(View.GONE);
            holder.llLook.setVisibility(View.GONE);
            holder.imgQRcode.setVisibility(View.VISIBLE);
        } else {
            holder.imgQRcode.setVisibility(View.INVISIBLE);
            holder.ll_card_Exprie.setVisibility(View.GONE);
            holder.ll_card_Still_validated.setVisibility(View.GONE);
            holder.llLook.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClickItem(cardDeatilsInFor);
                Log.e("getIDcard", "ID Card: " + cardDeatilsInFor.getId());

            }
        });
        holder.imgQRcode.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClickQrCode(cardDeatilsInFor);
            }
        });
        holder.imgLogo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (onClickLogo != null)
                    onClickLogo.OnClickLogo(cardDeatilsInFor.getProviderDto());
            }
        });
    }


    @Override
    public int getItemCount() {
        if (cardDeatilsInFors == null) return 0;
        return cardDeatilsInFors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView tvName, tvUseLimit, tvLimitRemaining, tvExpirationDate, tvLabelExpiration;
        private LinearLayout ll_card_Still_validated, ll_card_Exprie, imgQRcode, llLook;
        private boolean stats;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvName = itemView.findViewById(R.id.tvName);
            tvUseLimit = itemView.findViewById(R.id.tvUseLimit);
            tvLimitRemaining = itemView.findViewById(R.id.tvLimitRemaining);
            tvExpirationDate = itemView.findViewById(R.id.tvExpirationDate);
            ll_card_Exprie = itemView.findViewById(R.id.ll_card_Exprie);
            ll_card_Still_validated = itemView.findViewById(R.id.ll_card_Still_validated);
            llLook = itemView.findViewById(R.id.llLook);
            imgQRcode = itemView.findViewById(R.id.imgQRcode);
            tvLabelExpiration = itemView.findViewById(R.id.tvLabelExpiration);
        }
    }

    public interface OnClickListener {
        void onClickItem(MyCardDetailsWaitResponseDefault.OwnServiceDto item);

        void onClickQrCode(MyCardDetailsWaitResponseDefault.OwnServiceDto item);
    }

    public interface OnClickLogo {
        void OnClickLogo(MyCardDetailsWaitResponseDefault.ProviderDto item);
    }
}
