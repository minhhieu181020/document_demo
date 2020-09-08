package com.lpb.lifecardsdk.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<AreaResponse.ListArea> listAreas;
    private OnClickListener onClickListener;
    private LinkedHashMap<Integer, String> listSelected;
    private Context context;

    public LocationAdapter(Context context) {
        listAreas = new ArrayList<>();
        listSelected = new LinkedHashMap<>();
        this.context = context;
    }

    public void setItems(List<AreaResponse.ListArea> items) {
        listAreas.clear();
        listAreas = items;
        notifyDataSetChanged();
    }

    public void setListSelected(LinkedHashMap<Integer, String> listSelected) {
        this.listSelected = listSelected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_filter, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AreaResponse.ListArea listArea = listAreas.get(position);
        holder.tvContent.setText(listArea.getAreaName());
        if(listSelected.get(position)!=null){
            holder.setSelected(true);
        }else holder.setSelected(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(listArea, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listAreas == null) return 0;
        return listAreas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
        }

        void setSelected(boolean selected) {
            if (selected) {
                tvContent.setTextColor(context.getResources().getColor(R.color.lifecardsdk_white));
                tvContent.setBackgroundResource(R.drawable.lifecardsdk_round_button_orange);
            } else {
                tvContent.setTextColor(context.getResources().getColor(R.color.lifecardsdk_gray));
                tvContent.setBackgroundResource(R.drawable.lifecardsdk_round_button_white2);
            }
        }
    }

    public interface OnClickListener {
        void onClick(AreaResponse.ListArea item, int position);
    }
}

