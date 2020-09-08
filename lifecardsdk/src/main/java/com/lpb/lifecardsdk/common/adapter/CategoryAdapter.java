package com.lpb.lifecardsdk.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryResponse.LstCate> categories;
    private OnClickListener onClickListener;
    private LinkedHashMap<Integer, String> listSelected;
    private Context context;

    public CategoryAdapter(Context context) {
        categories = new ArrayList<>();
        listSelected = new LinkedHashMap<>();
        this.context = context;
    }

    public void setItems(List<CategoryResponse.LstCate> items) {
        categories.clear();
        categories = items;
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
        CategoryResponse.LstCate categoryItem = categories.get(position);
        holder.tvContent.setText(categoryItem.getName());
        if(listSelected.get(position)!=null){
            holder.setSelected(true);
        }else holder.setSelected(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(categories.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories == null) return 0;
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvContent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
        }

        public void setSelected(boolean selected) {
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
        void onClick(CategoryResponse.LstCate item, int position);
    }
}

