package com.lpb.lifecardsdk.ui.home.fragment.setting.fragment.setting.adapter;

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
import com.lpb.lifecardsdk.data.model.response.default_.mobile_config.DataConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private List<DataConfig> dataConfigs;

    private OnClickListener onClickListener;
    private Context context;

    public SettingAdapter(Context context) {
        dataConfigs = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<DataConfig> items) {
        dataConfigs.clear();
        dataConfigs = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_setttings, parent, false);
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
        final DataConfig dataConfig = dataConfigs.get(position);
        holder.tvSettings.setText(dataConfig.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClickItem(dataConfig);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (dataConfigs == null) return 0;
        return dataConfigs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llSettings;
        private ImageView imgLogo;
        private TextView tvSettings;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            llSettings = itemView.findViewById(R.id.llSettings);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvSettings = itemView.findViewById(R.id.tvSettings);
        }
    }

    public interface OnClickListener {
        void onClickItem(DataConfig item);
    }
}

