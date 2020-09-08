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

public class HistoryServiceC1R2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC1R2List;
    private OnClickListener onMenuClickListener;
    private Context context;
    private boolean onLoadMore = false;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    public HistoryServiceC1R2Adapter(Context context) {
        this.historyServiceC1R2List = new ArrayList<>();
        this.context = context;
    }
    public void clearData() {
        historyServiceC1R2List.clear();
        notifyDataSetChanged();
    }

//    public void setItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
//        historyServiceC1R2List.clear();
//        historyServiceC1R2List = items;
//        notifyDataSetChanged();
//    }

    public void addItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC1R2List.addAll(items);
        notifyDataSetChanged();
    }

    public void setItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC1R2List = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_service_case1, parent, false);
            return new ViewHolder(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC1R2 = historyServiceC1R2List.get(position);
//        holder.tvTimeHistoryServiceCase1Row2.setText(historyServiceC1R2.getEntryTime());
//        holder.tvCreditLimitServiceCase1Row2.setText(historyServiceC1R2.getAvailableAfterUnit());
//        holder.itemView.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                    onMenuClickListener.onClick(historyServiceC1R2List.get(position));
//            }
//        });
        if (holder instanceof ViewHolder) {
            final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC1R2 = historyServiceC1R2List.get(position);
            ((ViewHolder) holder).onBindData(historyServiceC1R2);
        } else {
            //todo
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == historyServiceC1R2List.size() - 1)
                return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;
    }

    @Override
    public int getItemCount() {
        if (historyServiceC1R2List == null) return 0;
        return historyServiceC1R2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryServiceCase1Row2, tvCreditLimitServiceCase1Row2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryServiceCase1Row2 = itemView.findViewById(R.id.tvTimeHistoryServiceCase1Row2);
            tvCreditLimitServiceCase1Row2 = itemView.findViewById(R.id.tvCreditLimitServiceCase1Row2);

        }

        public void onBindData(final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC1R2) {
            tvTimeHistoryServiceCase1Row2.setText(historyServiceC1R2.getEntryTime());
            tvCreditLimitServiceCase1Row2.setText(historyServiceC1R2.getAvailableAfterUnit());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.onClick(historyServiceC1R2);
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
