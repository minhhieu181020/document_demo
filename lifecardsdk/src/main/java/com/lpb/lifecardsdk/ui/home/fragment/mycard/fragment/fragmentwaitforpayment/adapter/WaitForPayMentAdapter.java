package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.CardWaitForPayMent;

import java.util.ArrayList;
import java.util.List;

public class WaitForPayMentAdapter extends RecyclerView.Adapter<WaitForPayMentAdapter.ViewHolder> {
//    private List<PackageSearchResponse.PackageDto> packageDtoList;
//
//    private BuyCardAdapter.OnChangeItemChecked onMenuClickListener;
//    private Context context;
//
//    public WaitForPayMentAdapter(Context context) {
//        packageDtoList = new ArrayList<>();
//        this.context = context;
//    }
//
//    public void setItems(List<PackageSearchResponse.PackageDto> items) {
//        packageDtoList.clear();
//        packageDtoList = items;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public WaitForPayMentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View itemView = inflater.inflate(R.layout.lifecardsdk_item_buy_card, parent, false);
//        return new WaitForPayMentAdapter.ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final PackageSearchResponse.PackageDto packageDto = packageDtoList.get(position);
//        holder.tvCardName.setText(packageDto.getName());
//        holder.tvPriceOrigin.setText(packageDto.getServicePrice()+" "+packageDto.getCurrency());
//        holder.tvPriceNew.setText(packageDto.getPackagePrice()+" "+packageDto.getCurrency());
//        for (int i=0;i<packageDto.getPromotionDtos().size();i++){
//            holder.tvDescription.append(packageDto.getPromotionDtos().get(i).getDescription()+"\n");
//        }
//        holder.imgBackground.setImageResource(R.mipmap.lifecardsdk_background);
//        holder.tvPromotion.setText((packageDto.getServicePrice()-packageDto.getPackagePrice())+" "+packageDto.getCurrency());
//        LogoAdapter logoAdapter = new LogoAdapter(context, packageDto.getProviderDtos());
//        holder.rvLogo.setAdapter(logoAdapter);
//        holder.rvLogo.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//
//        holder.btnBuyCard.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                    onMenuClickListener.onClickBuyCard(packageDto);
//            }
//        });
//        holder.itemView.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                    onMenuClickListener.onClickItem(packageDto);
//            }
//        });
//    }
//
//
//    public void setOnChangeItemChecked(BuyCardAdapter.OnChangeItemChecked onClickListener) {
//        this.onMenuClickListener = onClickListener;
//    }
//
//    @Override
//    public int getItemCount() {
//        if (packageDtoList == null) return 0;
//        return packageDtoList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        final TextView tvCardName, tvPriceOrigin, tvPriceNew, tvDescription,tvPromotion;
//        final Button btnBuyCard;
//        final ImageView imgBackground;
//        final RecyclerView rvLogo;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvCardName = itemView.findViewById(R.id.tvCardName);
//            tvPriceOrigin = itemView.findViewById(R.id.tvPriceOrigin);
//            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);;
//            tvPromotion = itemView.findViewById(R.id.tvPromotion);
//            tvDescription = itemView.findViewById(R.id.tvDescription);
//            imgBackground = itemView.findViewById(R.id.imgBackground);
//            rvLogo = itemView.findViewById(R.id.rvLogo);
//            btnBuyCard = itemView.findViewById(R.id.btnBuyCard);
//        }
//    }
//
//    public interface OnChangeItemChecked {
//        void onClickItem(PackageSearchResponse.PackageDto item);
//
//        void onClickBuyCard(PackageSearchResponse.PackageDto item);
//    }
//}

    private List<CardWaitForPayMent> waitForPayMents;
    private OnClickListener onMenuClickListener;
    private Context context;


    public WaitForPayMentAdapter(Context context) {
        waitForPayMents = new ArrayList<>();
        this.context = context;
    }


    public void setItems(List<CardWaitForPayMent> items) {
        waitForPayMents.clear();
        waitForPayMents = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_wait_for_payment, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardWaitForPayMent cardWaitForPayMent = waitForPayMents.get(position);
        holder.tvCardName.setText(cardWaitForPayMent.getTvCardName());
        holder.tvCodeProduct.setText(cardWaitForPayMent.getTvCodeProduct());
        holder.tvPriceNew.setText(cardWaitForPayMent.getTvPriceNew());
        holder.tvPriceOrigin.setText(cardWaitForPayMent.getTvPriceOrigin());
        holder.imgBackground.setImageResource(cardWaitForPayMent.getImgBackground());
        LogoCardWAdapter logoCardAdapter = new LogoCardWAdapter(context, cardWaitForPayMent.getRcImgLogo());
        holder.rcImgLogo.setAdapter(logoCardAdapter);
        holder.rcImgLogo.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null)
                    onMenuClickListener.onClick(cardWaitForPayMent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (waitForPayMents == null) return 0;
        return waitForPayMents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvCardName, tvCodeProduct, tvPriceNew, tvPriceOrigin, tvDescription;
        final ImageView imgBackground;
        private RecyclerView rcImgLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvCodeProduct = itemView.findViewById(R.id.tvCodeProduct);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            tvPriceOrigin = itemView.findViewById(R.id.tvPriceOrigin);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgBackground = itemView.findViewById(R.id.imgBackground);
            rcImgLogo = itemView.findViewById(R.id.rvLogoCard);
        }
    }

    public interface OnClickListener {
        void onClick(CardWaitForPayMent item);
    }
}
