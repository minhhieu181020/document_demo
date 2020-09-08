package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.recharge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.RechargeResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 28/08/2020
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {
    private List<RechargeResponse.DefRechargeDto> defRechargeDtos;
    private Context context;
    private OnClickListener onClickListener;

    public RechargeAdapter(Context context) {
        defRechargeDtos = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<RechargeResponse.DefRechargeDto> items) {
        defRechargeDtos.clear();
        defRechargeDtos.addAll(items);
        notifyDataSetChanged();
    }

    public void clearData() {
        defRechargeDtos.clear();
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_recharge, parent, false);
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
        final RechargeResponse.DefRechargeDto defRechargeDto = defRechargeDtos.get(position);

        if (defRechargeDto.isChecked()) {
            holder.itemView.setBackgroundResource(R.drawable.lifecardsdk_bg_white_radius_selected);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.lifecardsdk_bg_white_radius_border_black);
        }
        holder.tvAmount.setText(defRechargeDto.getListedPriceDisplay());
        holder.tvPrice.setText(defRechargeDto.getPriceDisplay());
    }


    @Override
    public int getItemCount() {
        if (defRechargeDtos == null) return 0;
        return defRechargeDtos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPrice;
        private TextView tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (RechargeResponse.DefRechargeDto defRechargeDto: defRechargeDtos) {
                        defRechargeDto.setChecked(false);
                    }
                    RechargeResponse.DefRechargeDto defRechargeDto = defRechargeDtos.get(getAdapterPosition());
                    defRechargeDto.setChecked(true);
                    notifyDataSetChanged();
                    onClickListener.onClickItem(defRechargeDto);
                }
            });
        }
    }

    public interface OnClickListener {
        void onClickItem(RechargeResponse.DefRechargeDto defRechargeDto);
    }
}

