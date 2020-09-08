package com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.CategoryResponse;
import com.lpb.lifecardsdk.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryResponse.LstCate> categories;
    private OnClickListener onClickListener;
    private Context context;
    private int posSelected;

    public CategoryAdapter(Context context) {
        categories = new ArrayList<>();
        this.context = context;
    }

    public void setPosSelected(int posSelected) {
        this.posSelected = posSelected;
    }

    public void setItems(List<CategoryResponse.LstCate> items) {
        categories.clear();
        categories = items;
        notifyDataSetChanged();
    }
    public void clearData() {
        categories.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_main_category, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CategoryResponse.LstCate categoryItem = categories.get(position);
        holder.tvName.setText(categoryItem.getName());

        GlideUtils.loadImageUrl(holder.imgAvatar,categoryItem.getIcon(),context, Constants.PlaceHolderType.LOGO_CATEGORY);

        if (position == posSelected)
            holder.setSelected(true);
        else holder.setSelected(false);
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
        final FrameLayout flContent;
        final ImageView imgBackground;
        final ImageView imgAvatar;
        final TextView tvName;



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            flContent = itemView.findViewById(R.id.flContent);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
        }

        void setSelected(boolean selected) {
            if (selected) {
                imgBackground.setVisibility(View.VISIBLE);
            } else imgBackground.setVisibility(View.GONE);
        }

    }

    public interface OnClickListener {
        void onClick(CategoryResponse.LstCate item, int position);
    }
}

