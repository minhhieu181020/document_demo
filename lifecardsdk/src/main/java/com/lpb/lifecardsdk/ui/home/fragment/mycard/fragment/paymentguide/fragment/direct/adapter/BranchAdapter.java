package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.paymentguide.fragment.direct.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.BranchItem;
import com.lpb.lifecardsdk.widget.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {
    private List<BranchItem> branchItems;
    private OnClickListener onClickListener;
    private Context context;
    private LinkedHashMap<Integer, Integer> listSelected;

    public BranchAdapter(Context context) {
        branchItems = new ArrayList<>();
        this.context = context;
        listSelected = new LinkedHashMap<>();
    }

    public void setItems(List<BranchItem> items) {
        branchItems.clear();
        branchItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_branch, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (branchItems == null) return 0;
        return branchItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvStatus, tvPhoneNumber, tvAddress;
        final RecyclerView rvTime;
        final LinearLayout llTime;
        final ImageView imgExpand;
        private ExpandableLayout expandableLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            rvTime = itemView.findViewById(R.id.rvTime);
            llTime = itemView.findViewById(R.id.llTime);
            imgExpand = itemView.findViewById(R.id.imgExpand);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
        }

        public void onBindViewHolder(final int position) {
            final BranchItem branchItem = branchItems.get(position);
            tvName.setText(branchItem.getBranchName());
            tvAddress.setText(branchItem.getAddress());
            tvPhoneNumber.setText(branchItem.getPhoneNumber());
            if (branchItem.isStatus()) {
                tvStatus.setText("Đang mở cửa");
                tvStatus.setTextColor(context.getResources().getColor(R.color.lifecardsdk_green));
            } else {
                tvStatus.setText("Đang đóng cửa");
                tvStatus.setTextColor(context.getResources().getColor(R.color.lifecardsdk_red));
            }
            TimeAdapter timeAdapter = new TimeAdapter(branchItem.getTimeItems(), context);
            rvTime.setLayoutManager(new LinearLayoutManager(context));
            rvTime.setAdapter(timeAdapter);


            if (listSelected.get(position) != null)
                expandableLayout.setExpanded(true, false);
            else expandableLayout.setExpanded(false, false);

            llTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listSelected.get(position) != null) {
                        expandableLayout.setExpanded(false, true);
                        listSelected.remove(position);
                    } else {
                        expandableLayout.setExpanded(true, true);
                        listSelected.put(position, position);
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null)
                        onClickListener.onClickItem(branchItem);
                }
            });
            expandableLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
                @Override
                public void onExpansionUpdate(float expansionFraction, int state) {
                    imgExpand.setRotation(expansionFraction * 180);
                }
            });
        }
    }
    public interface OnClickListener {
        void onClickItem(BranchItem item);
    }
}
