package com.lpb.lifecardsdk.ui.paymentguide.fragment.direct.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.TimeItem;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private List<TimeItem> timeItems;
    private Context context;

    public TimeAdapter(List<TimeItem> timeItems, Context context) {
        this.timeItems = timeItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_time, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TimeItem timeItem = timeItems.get(position);
        holder.tvDay.setText(timeItem.getDay());
        holder.tvHour.setText(timeItem.getHour());
    }


    @Override
    public int getItemCount() {
        if (timeItems == null) return 0;
        return timeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDay, tvHour;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvHour = itemView.findViewById(R.id.tvHour);
        }
    }

}
