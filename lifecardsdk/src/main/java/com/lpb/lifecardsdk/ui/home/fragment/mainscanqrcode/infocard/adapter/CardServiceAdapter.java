package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.infocard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListServiceInfoResponse;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

public class CardServiceAdapter extends RecyclerView.Adapter<CardServiceAdapter.ViewHolder> {
    private List<ListServiceInfoResponse.OwnServiceDto> serviceItemList;
    private Context context;
    public CardServiceAdapter(Context context) {
        serviceItemList = new ArrayList<>();
        this.context = context;
    }
    public void cleardataserver(){
        serviceItemList.clear();
    }
    public void setItems(List<ListServiceInfoResponse.OwnServiceDto> items) {
        serviceItemList = items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_service_card, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ListServiceInfoResponse.OwnServiceDto cardServiceItem = serviceItemList.get(position);
        holder.tvServiceName.setText(cardServiceItem.getNamDefService());
        holder.tvLimitRest.setText(cardServiceItem.getOwnAccountDtos().get(0).getAvailableUnit());
        holder.tvExpirationDate.setText(cardServiceItem.getExpiryDate());


        if (StringUtils.isEmpty(cardServiceItem.getUsageDescription())) {
            holder.tvLimitGrant.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(cardServiceItem.getOwnAccountDtos().get(0).getAllocatedUnit())));
        } else {
            holder.tvLimitGrant.setText(HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(cardServiceItem.getUsageDescription())));
        }
        if (cardServiceItem.getStatus().equalsIgnoreCase("A") || cardServiceItem.getStatus().equalsIgnoreCase("O")) {
            holder.tvExpired.setVisibility(View.GONE);
            holder.tvStillValid.setVisibility(View.VISIBLE);
            holder.tvWaitPayment.setVisibility(View.GONE);
        } else if (cardServiceItem.getStatus().equalsIgnoreCase("E") || cardServiceItem.getStatus().equalsIgnoreCase("C")) {
            holder.tvExpired.setVisibility(View.VISIBLE);
            holder.tvStillValid.setVisibility(View.GONE);
            holder.tvWaitPayment.setVisibility(View.GONE);
        } else if (cardServiceItem.getStatus().equalsIgnoreCase("W")) {
            holder.tvExpired.setVisibility(View.GONE);
            holder.tvStillValid.setVisibility(View.GONE);
            holder.tvWaitPayment.setVisibility(View.VISIBLE);
        }
        GlideUtils.loadImageUrl(holder.imgLogo,cardServiceItem.getProviderDto().getLogo(),context, Constants.PlaceHolderType.LOGO_PROVIDER);
    }


    @Override
    public int getItemCount() {
        if (serviceItemList == null) return 0;
        return serviceItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceName;
        private TextView tvStillValid;
        private TextView tvExpired;
        private TextView tvWaitPayment;
        private TextView tvLimitGrant;
        private TextView tvLimitRest;
        private TextView tvExpirationDate;
        private ImageView imgLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvStillValid = itemView.findViewById(R.id.tvStillValid);
            tvExpired = itemView.findViewById(R.id.tvExpired);
            tvWaitPayment = itemView.findViewById(R.id.tvWaitPayment);
            tvLimitGrant = itemView.findViewById(R.id.tvLimitGrant);
            tvLimitRest = itemView.findViewById(R.id.tvLimitRest);
            tvExpirationDate = itemView.findViewById(R.id.tvExpirationDate);
            imgLogo = itemView.findViewById(R.id.imgLogo);

        }
    }
}
