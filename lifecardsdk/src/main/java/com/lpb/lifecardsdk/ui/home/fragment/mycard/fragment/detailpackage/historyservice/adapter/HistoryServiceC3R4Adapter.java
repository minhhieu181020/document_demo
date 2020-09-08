package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.detailpackage.historyservice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.TransactionHistoryResponseDefault;

import java.util.ArrayList;
import java.util.List;

public class HistoryServiceC3R4Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC3R4List;
    private OnClickListener onMenuClickListener;
    private Context context;
    private boolean onLoadMore = false;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public HistoryServiceC3R4Adapter(Context context) {
        this.historyServiceC3R4List = new ArrayList<>();
        this.context = context;
    }

    public void clearData() {
        historyServiceC3R4List.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC3R4List = items;
        notifyDataSetChanged();
    }

    public void addItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC3R4List.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_service_case3, parent, false);
            return new ViewHolder(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC3R4 = historyServiceC3R4List.get(i);
            ((ViewHolder) viewHolder).onBindData(historyServiceC3R4);
        } else {
            //todo
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == historyServiceC3R4List.size() - 1)
                return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        TransactionHistoryResponseDefault.AccountEntryDto historyDeatil = historyServiceC3R4List.get(position);
//        holder.tvTimeHistoryServiceCase3Row4.setText(historyDeatil.getEntryTime());
//        holder.tvNumberServiceCase3Row4.setText(historyDeatil.getAmountUnit());
//        if (historyDeatil.getAmountUnit().startsWith("+")) {
//            holder.tvNumberServiceCase3Row4.setText(historyDeatil.getAmountUnit());
//            holder.tvNumberServiceCase3Row4.setTextColor(context.getResources().getColor(R.color.lifecardsdk_green));
//        } else {
//            holder.tvNumberServiceCase3Row4.setText(historyDeatil.getAmountUnit());
//            holder.tvNumberServiceCase3Row4.setTextColor(context.getResources().getColor(R.color.lifecardsdk_red));
//        }
//        holder.tvCreditLimitServiceCase3Row4.setText(historyDeatil.getAvailableAfterUnit());
//        holder.itemView.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                    onMenuClickListener.onClick(historyServiceC3R4List.get(position));
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        if (historyServiceC3R4List == null) return 0;
        return historyServiceC3R4List.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryServiceCase3Row4, tvNumberServiceCase3Row4, tvCreditLimitServiceCase3Row4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryServiceCase3Row4 = itemView.findViewById(R.id.tvTimeHistoryServiceCase3Row4);
            tvNumberServiceCase3Row4 = itemView.findViewById(R.id.tvNumberServiceCase3Row4);
            tvCreditLimitServiceCase3Row4 = itemView.findViewById(R.id.tvCreditLimitServiceCase3Row4);
        }

        public void onBindData(final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC3R4) {
            tvTimeHistoryServiceCase3Row4.setText(historyServiceC3R4.getEntryTime());
            tvNumberServiceCase3Row4.setText(historyServiceC3R4.getAmountUnit());
            if (historyServiceC3R4.getAmountUnit().startsWith("+")) {
                tvNumberServiceCase3Row4.setText(historyServiceC3R4.getAmountUnit());
                tvNumberServiceCase3Row4.setTextColor(context.getResources().getColor(R.color.lifecardsdk_green));
            } else {
                tvNumberServiceCase3Row4.setText(historyServiceC3R4.getAmountUnit());
                tvNumberServiceCase3Row4.setTextColor(context.getResources().getColor(R.color.lifecardsdk_red));
            }
            tvCreditLimitServiceCase3Row4.setText(historyServiceC3R4.getAvailableAfterUnit());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.onClick(historyServiceC3R4);
                }
            });
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View view) {
            super(view);
        }
    }

    public interface OnClickListener {
        void onClick(TransactionHistoryResponseDefault.AccountEntryDto item);
    }

}
