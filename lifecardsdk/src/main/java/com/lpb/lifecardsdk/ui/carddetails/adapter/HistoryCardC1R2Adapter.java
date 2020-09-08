package com.lpb.lifecardsdk.ui.carddetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.HistoryDeatilC1R2;

import java.util.List;

public class HistoryCardC1R2Adapter extends RecyclerView.Adapter<HistoryCardC1R2Adapter.ViewHolder> {
    private List<HistoryDeatilC1R2> historyCardList;
    private OnClickListener onMenuClickListener;
    private int size = 2;

    public HistoryCardC1R2Adapter(List<HistoryDeatilC1R2> historyCardList) {
        this.historyCardList = historyCardList;
    }

    public void setSize(int size) {
        this.size = size;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_detail_case1, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryDeatilC1R2 historyDeatil = historyCardList.get(position);
        holder.tvTimeHistoryCase1Row2.setText(historyDeatil.getTvTimeHistoryCase1Row2());
        holder.tvCreditLimitCase1Row2.setText(historyDeatil.getTvCreditLimitCase1Row2());
    }

    @Override
    public int getItemCount() {
//        if (historyCardList == null) return 0;
//        return historyCardList.size();
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryCase1Row2, tvCreditLimitCase1Row2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryCase1Row2 = itemView.findViewById(R.id.tvTimeHistoryCase1Row2);
            tvCreditLimitCase1Row2 = itemView.findViewById(R.id.tvTimeHistoryCase1Row2);
        }
    }

    public interface OnClickListener {
        void onClick(HistoryCardC1R2Adapter item);
    }

}
