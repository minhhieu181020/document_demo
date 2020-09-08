package com.lpb.lifecardsdk.ui.usermanager.adapter;

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
import com.lpb.lifecardsdk.data.model.response.default_.UserManagerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 27/07/2020
 */
public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.ViewHolder> {
    private List<UserManagerResponse.OwnerDto> userList;
    private OnClickListener onClickListener;
    private Context context;

    public UserManagerAdapter(Context context) {
        userList = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<UserManagerResponse.OwnerDto> items) {
        userList.clear();
        userList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_user, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final UserManagerResponse.OwnerDto user = userList.get(position);
        holder.tvDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onClickListener.onClick(user);
            }
        });
        holder.tvName.setText(user.getCustomerName());
        holder.tvPhoneNumber.setText(user.getMobilePhone());
        holder.tvTime.setText(user.getInsertTime());
    }


    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvDelete;
        private TextView tvPhoneNumber;
        private TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public interface OnClickListener {
        void onClick(UserManagerResponse.OwnerDto item);
    }
}
