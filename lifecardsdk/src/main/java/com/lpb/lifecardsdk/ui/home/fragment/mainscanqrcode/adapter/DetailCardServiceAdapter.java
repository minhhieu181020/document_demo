package com.lpb.lifecardsdk.ui.home.fragment.mainscanqrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.ServicePaymentQrBillResponse;

import java.util.ArrayList;
import java.util.List;


public class DetailCardServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ServicePaymentQrBillResponse.ServiceDetailData> listCardServices;
    private OnClickListener onMenuClickListener;
    private Context context;
    private boolean onLoadMore = false;
    private static final int STATUS1 = 1;
    private static final int STATUS2 = 2;
    private static final int STATUS3 = 3;
    Boolean status;

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }


    public DetailCardServiceAdapter(Context context) {
        this.listCardServices = new ArrayList<>();
        this.context = context;
    }

    public void addItems(List<ServicePaymentQrBillResponse.ServiceDetailData> items) {
        listCardServices.addAll(items);
        notifyDataSetChanged();
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setItems(List<ServicePaymentQrBillResponse.ServiceDetailData> items) {
        listCardServices.clear();
        listCardServices = items;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {

            case STATUS1:
                LayoutInflater inflater = LayoutInflater.from(context);
                View itemView = inflater.inflate(R.layout.lifecardsdk_item_transaction, parent, false);
                return new ViewHolder(itemView);
            case STATUS2:
                LayoutInflater inflater1;
                inflater1 = LayoutInflater.from(context);
                itemView = inflater1.inflate(R.layout.lifecardsdk_item_transaction_times, parent, false);
                return new ViewHolder(itemView);
            case STATUS3:
                LayoutInflater inflater2;
                inflater2 = LayoutInflater.from(context);
                itemView = inflater2.inflate(R.layout.lifecardsdk_layout_loadmore, parent, false);
                return new ViewHolder(itemView);

            default:
                LayoutInflater inflater4;
                inflater4 = LayoutInflater.from(context);
                itemView = inflater4.inflate(R.layout.lifecardsdk_item_transaction, parent, false);
                return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            final ServicePaymentQrBillResponse.ServiceDetailData listCardService = listCardServices.get(i);
            ((ViewHolder) viewHolder).onBindData(listCardService);
        } else {
            //todo
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (onLoadMore) {
            if (position == listCardServices.size() - 1) {
                return STATUS3;
            } else {
                if (listCardServices.get(position).getUnitType().equalsIgnoreCase(Constants.BundleConstants.LIMIT)) {
                    return STATUS1;
                } else if (listCardServices.get(position).getUnitType().equalsIgnoreCase(Constants.BundleConstants.TIMES)) {
                    return STATUS2;
                }
            }

        } else {
            if (listCardServices.get(position).getUnitType().equalsIgnoreCase(Constants.BundleConstants.LIMIT)) {
                return STATUS1;
            } else if (listCardServices.get(position).getUnitType().equalsIgnoreCase(Constants.BundleConstants.TIMES)) {
                return STATUS2;
            }
        }
        return STATUS3;
    }

    @Override
    public int getItemCount() {
        if (listCardServices == null) {
            return 0;
        } else {
            return listCardServices.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAmountBeforeTaxes;
        private TextView tvAmountAfterTaxes;
        private TextView tvPaymentCard;
        private TextView tvKHPayment;
        private TextView tvCardNumber;
        private LinearLayout llAmountBeforeTaxes, llPaymentCard, llKHPayment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmountBeforeTaxes = itemView.findViewById(R.id.tvAmountBeforeTaxes);
            tvAmountAfterTaxes = itemView.findViewById(R.id.tvAmountAfterTaxes);
            tvPaymentCard = itemView.findViewById(R.id.tvPaymentCard);
            tvKHPayment = itemView.findViewById(R.id.tvKHPayment);
            tvCardNumber = itemView.findViewById(R.id.tvCardNumber);
            llAmountBeforeTaxes = itemView.findViewById(R.id.llAmountBeforeTaxes);
            llPaymentCard = itemView.findViewById(R.id.llPaymentCard);
            llKHPayment = itemView.findViewById(R.id.llKHPayment);
        }

        public void onBindData(final ServicePaymentQrBillResponse.ServiceDetailData listCardService) {
            if (!status) {
                llAmountBeforeTaxes.setVisibility(View.GONE);
                llPaymentCard.setVisibility(View.GONE);
                llKHPayment.setVisibility(View.GONE);
            }
            tvName.setText(listCardService.getName());
            tvAmountBeforeTaxes.setText(listCardService.getAmountDisplay());
            tvAmountAfterTaxes.setText(listCardService.getInputAmountDisplay());
            tvPaymentCard.setText(listCardService.getTransAmountDisplay());
            tvKHPayment.setText(listCardService.getAdditionalAmountDisplay());
            tvCardNumber.setText(listCardService.getCardNoDisplay());
            tvCardNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.onClick(listCardService);
                }
            });
        }

    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View view) {
            super(view);
        }
    }

    public interface OnClickListener {
        void onClick(ServicePaymentQrBillResponse.ServiceDetailData item);
    }
}
