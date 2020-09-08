package com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.ui.home.fragment.mycode.adapter.MyCodeAdapter;
import com.lpb.lifecardsdk.util.GlideUtils;

import java.util.List;

public class LogoCardAdapter extends RecyclerView.Adapter<LogoCardAdapter.ViewHolder> {

    private List<ListCardResponseDefault.ProviderDto> providerDtoList;
    private Context context;
    private MyCardStillValidateAdapter.OnClickLogo onClickLogo;
    private MyCodeAdapter.OnClickLogo onClickLogoMycode;

    private boolean status;

    public LogoCardAdapter(Context context, List<ListCardResponseDefault.ProviderDto> providerDtoList, boolean status, MyCardStillValidateAdapter.OnClickLogo onClickLogo, MyCodeAdapter.OnClickLogo onClickLogoMycode) {
        this.providerDtoList = providerDtoList;
        this.context = context;
        this.onClickLogo = onClickLogo;
        this.onClickLogoMycode = onClickLogoMycode;
        this.status = status;
    }
//    public LogoCardAdapter(Context context, List<ListCardResponseDefault.ProviderDto> providerDtoList, boolean status, MyCardStillValidateAdapter.OnClickLogo onClickLogo) {
//        this.providerDtoList = providerDtoList;
//        this.context = context;
//        this.status = status;
//        this.onClickLogo = onClickLogo;
//    }
//
//
//        public LogoCardAdapter(Context context, List<ListCardResponseDefault.ProviderDto> listLogo, boolean status,) {
//        this.status = status;
//        this.listLogo = listLogo;
//        this.context = context;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.lifecardsdk_item_logo_card, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtils.loadImageUrl(holder.imgLogo,providerDtoList.get(position).getLogo(),context, Constants.PlaceHolderType.LOGO_PROVIDER);
        if (!status) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.imgLogo.setColorFilter(filter);
        }
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickLogo != null)
                    onClickLogo.onClickItem(providerDtoList.get(position));
                if (onClickLogoMycode != null)
                    onClickLogoMycode.onClickItem(providerDtoList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (providerDtoList == null) return 0;
        return providerDtoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgLogo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogo = itemView.findViewById(R.id.imglogocard);

        }
    }
}

