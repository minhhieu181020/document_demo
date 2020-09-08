package com.lpb.lifecardsdk.ui.carddetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.HistoryDeatilC2R3;

import java.util.List;

public class HistoryCardC2R3Adapter extends RecyclerView.Adapter<HistoryCardC2R3Adapter.ViewHolder> {
    private List<HistoryDeatilC2R3> historyCardList;
    private OnClickListener onMenuClickListener;
    private int size = 2;

    public HistoryCardC2R3Adapter(List<HistoryDeatilC2R3> historyCardList) {
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
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_detail_case2, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryDeatilC2R3 historyDeatil = historyCardList.get(position);
        holder.tvTimeHistoryCase2Row3.setText(historyDeatil.getTvTimeHistoryCase2Row3());
        holder.tvMoneyCase2Row3.setText(historyDeatil.getTvMoneyCase2Row3());
        holder.tvCreditLimitCase2Row3.setText(historyDeatil.getTvCreditLimitCase2Row3());
    }

    @Override
    public int getItemCount() {
//        if (historyCardList == null) return 0;
//        return historyCardList.size();
////
////
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryCase2Row3, tvMoneyCase2Row3, tvCreditLimitCase2Row3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryCase2Row3 = itemView.findViewById(R.id.tvTimeHistoryCase2Row3);
            tvMoneyCase2Row3 = itemView.findViewById(R.id.tvTimeHistoryCase2Row3);
            tvCreditLimitCase2Row3 = itemView.findViewById(R.id.tvTimeHistoryCase2Row3);
        }
    }

    public interface OnClickListener {
        void onClick(HistoryCardC2R3Adapter item);
    }

}
