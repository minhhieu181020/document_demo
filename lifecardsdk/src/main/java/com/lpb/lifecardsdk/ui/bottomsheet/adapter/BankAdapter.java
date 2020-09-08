package com.lpb.lifecardsdk.ui.bottomsheet.adapter;

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
import com.lpb.lifecardsdk.common.listener.OnSingleClickListener;
import com.lpb.lifecardsdk.data.model.BankItem;
import com.lpb.lifecardsdk.data.model.Branch;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter.AreaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {
    private List<BankItem> bankItems;
    private OnClickListener onClickListener;
    private Context context;

    public BankAdapter(Context context) {
        bankItems = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<BankItem> items) {
        bankItems.clear();
        bankItems = items;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_bank, parent, false);
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
        final BankItem bankItem = bankItems.get(position);

        holder.imgCopy.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickItem(bankItem);
            }
        });
        holder.tvBank.setText(bankItem.getNganhang());
        holder.tvBankAccountName.setText(bankItem.getTentk());
        holder.tvBranch.setText(bankItem.getChinhanh());
        holder.tvBankNumber.setText(bankItem.getSotk());
    }


    @Override
    public int getItemCount() {
        if (bankItems == null) return 0;
        return bankItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View viewVertical;
        private TextView tvBankAccountName;
        private TextView tvBank;
        private TextView tvBranch;
        private TextView tvBankNumber;
        private ImageView imgCopy;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewVertical = itemView.findViewById(R.id.viewVertical);
            tvBankAccountName = itemView.findViewById(R.id.tvBankAccountName);
            tvBank = itemView.findViewById(R.id.tvBank);
            tvBranch = itemView.findViewById(R.id.tvBranch);
            tvBankNumber = itemView.findViewById(R.id.tvBankNumber);
            imgCopy = itemView.findViewById(R.id.imgCopy);
        }
    }

    public interface OnClickListener {
        void onClickItem(BankItem bankItem);
    }
}
