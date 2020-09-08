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
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {
    private List<BranchResponse.List> branchList;
    private Context context;
    private OnClickListener onClickListener;

    public BranchAdapter(Context context) {
        branchList = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<BranchResponse.List> items) {
        branchList.clear();
        branchList = items;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_branch_2, parent, false);
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
        final BranchResponse.List branch = branchList.get(position);
        holder.tvName.setText(branch.getName());
        holder.tvAddress.setText(branch.getFullAddress());
        holder.tvPhoneNumber.setText(StringUtils.underlineText(branch.getPhone()));

        if (branch.getOpenStatus().equalsIgnoreCase("1")) {
            holder.tvClose.setVisibility(View.GONE);
            holder.tvOpen.setVisibility(View.VISIBLE);
        } else if (branch.getOpenStatus().equalsIgnoreCase("0")) {
            holder.tvClose.setVisibility(View.VISIBLE);
            holder.tvOpen.setVisibility(View.GONE);
        }else {
            holder.tvClose.setVisibility(View.GONE);
           holder.tvOpen.setVisibility(View.GONE);
        }
        holder.tvTime.setText(StringUtils.convertHTMLToString(branch.getBusinessHourDisplay(), context));

        if (StringUtils.isEmpty(branch.getDistanceFormat())) {
            holder.tvKm.setText("");
        } else {
            holder.tvKm.setText(branch.getDistanceFormat());
        }
        holder.imgMap.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickItem(branch);
            }
        });
        holder.tvPhoneNumber.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClickPhone(branch.getPhone());
            }
        });
        if (position == 0) {
            holder.viewVertical.setVisibility(View.GONE);
        } else {
            holder.viewVertical.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        if (branchList == null) return 0;
        return branchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvKm;
        private TextView tvAddress;
        private TextView tvOpen;
        private TextView tvClose;
        private TextView tvTime;
        private TextView tvPhoneNumber;
        private ImageView imgMap;
        private View viewVertical;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvKm = itemView.findViewById(R.id.tvKm);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpen = itemView.findViewById(R.id.tvOpen);
            tvClose = itemView.findViewById(R.id.tvClose);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            imgMap = itemView.findViewById(R.id.imgMap);
            viewVertical = itemView.findViewById(R.id.viewVertical);
        }
    }

    public interface OnClickListener {
        void onClickItem(BranchResponse.List item);

        void onClickPhone(String phoneNumber);
    }
}
