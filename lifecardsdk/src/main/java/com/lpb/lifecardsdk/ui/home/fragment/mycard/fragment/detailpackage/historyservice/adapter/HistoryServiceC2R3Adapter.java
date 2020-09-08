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

public class HistoryServiceC2R3Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TransactionHistoryResponseDefault.AccountEntryDto> historyServiceC2R3List;
    private OnClickListener onMenuClickListener;
    private Context context;
    private boolean onLoadMore = false;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }


    public HistoryServiceC2R3Adapter(Context context) {
        this.historyServiceC2R3List = new ArrayList<>();
        this.context = context;
    }
    public void clearData() {
        historyServiceC2R3List.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC2R3List = items;
        notifyDataSetChanged();
    }

    public void addItems(List<TransactionHistoryResponseDefault.AccountEntryDto> items) {
        historyServiceC2R3List.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_history_service_case2, parent, false);
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
            final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC2R3 = historyServiceC2R3List.get(i);
            ((ViewHolder) viewHolder).onBindData(historyServiceC2R3);
        } else {
            //todo
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == historyServiceC2R3List.size() - 1)
                return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        if (historyServiceC2R3List == null) return 0;
        return historyServiceC2R3List.size();

// return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTimeHistoryServiceCase2Row3, tvMoneyServiceCase2Row3, tvCreditLimitServiceCase2Row3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHistoryServiceCase2Row3 = itemView.findViewById(R.id.tvTimeHistoryServiceCase2Row3);
            tvMoneyServiceCase2Row3 = itemView.findViewById(R.id.tvMoneyServiceCase2Row3);
            tvCreditLimitServiceCase2Row3 = itemView.findViewById(R.id.tvCreditLimitServiceCase2Row3);
        }

        public void onBindData(final TransactionHistoryResponseDefault.AccountEntryDto historyServiceC2R3) {
            tvTimeHistoryServiceCase2Row3.setText(historyServiceC2R3.getEntryTime());
            if (historyServiceC2R3.getAmountUnit().startsWith("+")) {
                tvMoneyServiceCase2Row3.setText(historyServiceC2R3.getAmountUnit());
                tvMoneyServiceCase2Row3.setTextColor(context.getResources().getColor(R.color.lifecardsdk_green));
            } else {
                tvMoneyServiceCase2Row3.setText(historyServiceC2R3.getAmountUnit());
                tvMoneyServiceCase2Row3.setTextColor(context.getResources().getColor(R.color.lifecardsdk_red));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.onClick(historyServiceC2R3);
                }
            });
            tvCreditLimitServiceCase2Row3.setText(historyServiceC2R3.getAvailableAfterUnit());

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
