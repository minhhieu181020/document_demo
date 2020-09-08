package com.lpb.lifecardsdk.ui.carddetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.HistoryDeatilC3R4;

import java.util.List;

public class HistoryCardC3R4Adapter extends RecyclerView.Adapter<HistoryCardC3R4Adapter.ViewHolder> {
    private List<HistoryDeatilC3R4> historyCardList;
    private OnClickListener onMenuClickListener;
    private int size = 2;

    public HistoryCardC3R4Adapter(List<HistoryDeatilC3R4> historyCardList) {
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
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_detail_case3, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryDeatilC3R4 historyDeatil = historyCardList.get(position);
        holder.tvTimeHistoryCase3Row4.setText(historyDeatil.getTvTimeHistoryCase3Row4());
        holder.tvMoneyCase3Row4.setText(historyDeatil.getTvMoneyCase3Row4());
        holder.tvNumberCase3Row4.setText(historyDeatil.getTvNumberCase3Row4());
        holder.tvCreditLimitCase3Row4.setText(historyDeatil.getTvCreditLimitCase3Row4());
    }

    @Override
    public int getItemCount() {
//        if (historyCardList == null) return 0;
//        return historyCardList.size();
        return size;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryCase3Row4, tvMoneyCase3Row4, tvNumberCase3Row4, tvCreditLimitCase3Row4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryCase3Row4 = itemView.findViewById(R.id.tvTimeHistoryCase3Row4);
            tvMoneyCase3Row4 = itemView.findViewById(R.id.tvMoneyCase3Row4);
            tvNumberCase3Row4 = itemView.findViewById(R.id.tvNumberCase3Row4);
            tvCreditLimitCase3Row4 = itemView.findViewById(R.id.tvCreditLimitCase3Row4);
        }
    }

    public interface OnClickListener {
        void onClick(HistoryCardC3R4Adapter item);
    }

}
