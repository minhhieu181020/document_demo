package com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;
import com.lpb.lifecardsdk.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<AreaResponse.ListArea> listAreas;
    private OnClickListener onClickListener;
    private Context context;
    private String areaChecked;

    public AreaAdapter(Context context) {
        listAreas = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<AreaResponse.ListArea> items) {
        listAreas.clear();
        listAreas = items;
        notifyDataSetChanged();
    }

    public void setAreaChecked(String areaChecked) {
        this.areaChecked = areaChecked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecard_item_layout_area, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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
        final AreaResponse.ListArea listArea = listAreas.get(position);
        holder.tvAreaName.setText(listArea.getAreaName());
        if (!StringUtils.isEmpty(areaChecked)) {
            if (areaChecked.equals(listArea.getAreaCode())) {
                holder.imgChecked.setVisibility(View.VISIBLE);
            } else {
                holder.imgChecked.setVisibility(View.GONE);
            }
        }
        if (position == 0) {
            holder.viewVertical.setVisibility(View.GONE);
        } else {
            holder.viewVertical.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickItem(listArea);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listAreas == null) return 0;
        return listAreas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaName;
        private View viewVertical;
        private ImageView imgChecked;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAreaName = itemView.findViewById(R.id.tvAreaName);
            viewVertical = itemView.findViewById(R.id.viewVertical);
            imgChecked = itemView.findViewById(R.id.imgChecked);
        }
    }

    public interface OnClickListener {
        void onClickItem(AreaResponse.ListArea item);
    }
}
