package com.lpb.lifecardsdk.ui.home.fragment.buycard.content.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PackageSearchResponse;
import com.lpb.lifecardsdk.util.GlideUtils;

import java.util.List;

public class LogoAdapter extends RecyclerView.Adapter<LogoAdapter.ViewHolder> {
    private List<PackageSearchResponse.ProviderDto> providerDtoList;
    private Context context;
    private BuyCardAdapter.OnClickLogo onClickLogo;

    LogoAdapter(Context context, List<PackageSearchResponse.ProviderDto> providerDtoList, BuyCardAdapter.OnClickLogo onClickLogo) {
        this.providerDtoList = providerDtoList;
        this.context = context;
        this.onClickLogo = onClickLogo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_logo, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtils.loadImageUrl(holder.imgLogo,providerDtoList.get(position).getLogo(),context, Constants.PlaceHolderType.LOGO_PROVIDER);
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickLogo != null)
                    onClickLogo.onClickItem(providerDtoList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if (providerDtoList == null) return 0;
        return providerDtoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);

        }
    }

}
