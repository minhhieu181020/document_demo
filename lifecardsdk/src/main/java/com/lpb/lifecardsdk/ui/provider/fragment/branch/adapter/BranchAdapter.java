package com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter;

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
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.BranchResponse;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BranchResponse.List> listBranch;
    private OnClickListener onClickListener;
    private Context context;
    private boolean onLoadMore = true;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }


    public BranchAdapter(Context context) {
        listBranch = new ArrayList<>();
        this.context = context;
    }

    public void clearData() {
        listBranch.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<BranchResponse.List> items) {
        clearData();
        listBranch = items;
        notifyDataSetChanged();
    }

    public void addItems(List<BranchResponse.List> items) {
        listBranch.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == Constants.RecyclerViewType.ITEM) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_item_provider_branch, parent, false);
            return new ViewHolder(itemView);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
            return new LoadHolder(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == listBranch.size() - 1) return Constants.RecyclerViewType.LOAD_MORE;
            else return Constants.RecyclerViewType.ITEM;
        } else return Constants.RecyclerViewType.ITEM;

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final BranchResponse.List branch = listBranch.get(position);
            ((ViewHolder) holder).tvName.setText(branch.getName());
            ((ViewHolder) holder).tvAddress.setText(branch.getFullAddress());
            if (StringUtils.isEmpty(branch.getPhone())){
                ((ViewHolder) holder).tvPhoneNumber.setText("");
                ((ViewHolder) holder).tvPhoneNumber.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).tvPhoneNumber.setText(StringUtils.underlineText(branch.getPhone()));
                ((ViewHolder) holder).tvPhoneNumber.setVisibility(View.VISIBLE);
            }

            if (StringUtils.isEmpty(branch.getDistanceFormat())) {
                ((ViewHolder) holder).tvKm.setText("");
            } else {
                ((ViewHolder) holder).tvKm.setText(branch.getDistanceFormat());
            }
            if (branch.getOpenStatus().equalsIgnoreCase("1")) {
                ((ViewHolder) holder).tvClose.setVisibility(View.GONE);
                ((ViewHolder) holder).tvOpen.setVisibility(View.VISIBLE);
            } else if (branch.getOpenStatus().equalsIgnoreCase("0")) {
                ((ViewHolder) holder).tvClose.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvOpen.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).tvClose.setVisibility(View.GONE);
                ((ViewHolder) holder).tvOpen.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).tvTime.setText(StringUtils.convertHTMLToString(branch.getBusinessHourDisplay(), context));
            ((ViewHolder) holder).imgMap.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClickItem(branch);
                }
            });
            ((ViewHolder) holder).tvPhoneNumber.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClickPhone(branch.getPhone());
                }
            });

        }  // todo


    }


    @Override
    public int getItemCount() {
        if (listBranch == null) return 0;
        return listBranch.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvKm;
        private TextView tvAddress;
        private TextView tvOpen;
        private TextView tvClose;
        private TextView tvTime;
        private TextView tvPhoneNumber;
        private ImageView imgMap;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpen = itemView.findViewById(R.id.tvOpen);
            tvClose = itemView.findViewById(R.id.tvClose);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            imgMap = itemView.findViewById(R.id.imgMap);
            tvKm = itemView.findViewById(R.id.tvKm);
        }
    }

    class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View view) {
            super(view);
        }
    }

    public interface OnClickListener {
        void onClickItem(BranchResponse.List item);

        void onClickPhone(String phoneNumber);
    }
}
