package com.lpb.lifecardsdk.ui.carddetails.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.CardDetails;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.ViewHolder> {
    private List<CardDetails> cardDetailsList;
    private List<CardDetails> cardDetailsListClone;
    private OnClickListener onMenuClickListener;
    private Context context;
    private RecyclerView rcHistory;
    private HistoryCardC1R2Adapter historyCardC1R2Adapter;
    private HistoryCardC2R3Adapter historyCardC2R3Adapter;
    private HistoryCardC3R4Adapter historyCardC3R4Adapter;
    private int size = 2;

    public CardDetailsAdapter(Context context) {
        this.cardDetailsList = new ArrayList<>();
        this.context = context;
    }


    public void setItems(List<CardDetails> items) {
        cardDetailsList.clear();
        cardDetailsList = items;
        notifyDataSetChanged();
    }


    public void setSize(int size) {
        this.size = size;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_rc_card_detail, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CardDetails deatils = cardDetailsList.get(position);
        holder.imgbrandlogo.setImageResource(deatils.getImgbrandlogo());
        holder.tvNameBrand.setText(deatils.getTvNameBrand());
        holder.tvNamePackage.setText(deatils.getTvNamePackage());
        holder.tvStatusPackage.setText(deatils.getTvStatusPackage());
        holder.tvCreditLimitGranted.setText(deatils.getTvCreditLimitGranted());
        holder.tvCreditLimitRemaining.setText(deatils.getTvCreditLimitRemaining());
        holder.tvNameBrand.setText(deatils.getTvNameBrand());
        holder.tvCredEXP.setText(deatils.getTvCredEXP());
        holder.imgQRcode.setImageResource(deatils.getImgQRcode());
        holder.imgQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null)
                    onMenuClickListener.onClick(cardDetailsList.get(position));
            }
        });
        holder.tvCredEXP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null)
                    onMenuClickListener.onClick2(position);
            }
        });
        holder.tvLessMoreHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                     onMenuClickListener.onClick2(position);

                if (cardDetailsList.get(position).getType() == 1) {
                    historyCardC1R2Adapter.setSize(2);
                    notifyItemChanged(position);
                } else if (cardDetailsList.get(position).getType() == 2) {
                    historyCardC2R3Adapter.setSize(2);
                    notifyItemChanged(position);
                } else if (cardDetailsList.get(position).getType() == 3) {
                    historyCardC3R4Adapter.setSize(2);
                    notifyItemChanged(position);
                }
                holder.tvMoreHistory.setVisibility(View.VISIBLE);
                holder.tvLessMoreHistory.setVisibility(View.GONE);
            }
        });
        holder.tvMoreHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (onMenuClickListener != null)
//                    onMenuClickListener.onClick2(position);

                if (cardDetailsList.get(position).getType() == 1) {
                    historyCardC1R2Adapter.setSize(5);
                    notifyItemChanged(position);
                } else if (cardDetailsList.get(position).getType() == 2) {
                    historyCardC2R3Adapter.setSize(5);
                    notifyItemChanged(position);
                } else if (cardDetailsList.get(position).getType() == 3) {
                    historyCardC3R4Adapter.setSize(5);
                    notifyItemChanged(position);
                }
                holder.tvMoreHistory.setVisibility(View.GONE);
                holder.tvLessMoreHistory.setVisibility(View.VISIBLE);

            }
        });


        if (deatils.getType() == 1) {
            historyCardC1R2Adapter = new HistoryCardC1R2Adapter(deatils.getHistoryDeatilsC1R2());
            rcHistory.setAdapter(historyCardC1R2Adapter);
            holder.case1_2row.setVisibility(View.VISIBLE);
            holder.case2_3row.setVisibility(View.GONE);
            holder.case3_4row.setVisibility(View.GONE);
            LinearLayoutManager doc = new LinearLayoutManager(context);
            rcHistory.setLayoutManager(doc);
        } else if (deatils.getType() == 2) {
            historyCardC2R3Adapter = new HistoryCardC2R3Adapter(deatils.getHistoryDeatilsC2R3());
            rcHistory.setAdapter(historyCardC2R3Adapter);
            holder.case1_2row.setVisibility(View.GONE);
            holder.case2_3row.setVisibility(View.VISIBLE);
            holder.case3_4row.setVisibility(View.GONE);
            LinearLayoutManager doc = new LinearLayoutManager(context);
            rcHistory.setLayoutManager(doc);
        } else if (deatils.getType() == 3) {
            historyCardC3R4Adapter = new HistoryCardC3R4Adapter(deatils.getHistoryDeatilsC3R4());
            rcHistory.setAdapter(historyCardC3R4Adapter);
            holder.case1_2row.setVisibility(View.GONE);
            holder.case2_3row.setVisibility(View.GONE);
            holder.case3_4row.setVisibility(View.VISIBLE);
            LinearLayoutManager doc = new LinearLayoutManager(context);
            rcHistory.setLayoutManager(doc);
        }


        holder.tvNameBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null)
                    onMenuClickListener.onClick(cardDetailsList.get(position));
            }
        });
//        holder.tvMoreHistory.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                holder.tvMoreHistory.setVisibility(View.GONE);
//                holder.tvLessMoreHistory.setVisibility(View.VISIBLE);
////                historyCardC2R3Adapter.setSize(3);
////                notifyItemChanged(position);
//            }
//        });
//        holder.tvLessMoreHistory.setOnChangeItemChecked(new View.OnChangeItemChecked() {
//            @Override
//            public void onClick(View view) {
//                holder.tvMoreHistory.setVisibility(View.VISIBLE);
//                holder.tvLessMoreHistory.setVisibility(View.GONE);
//                if (deatils.getType() == 1) {
//
//                }
//                //                historyCardC2R3Adapter.setSize(2);
//                //                notifyItemChanged(position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (cardDetailsList == null) return 0;
        return cardDetailsList.size();

        //        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNameBrand, tvNamePackage, tvStatusPackage, tvCreditLimitGranted, tvCreditLimitRemaining, tvCredEXP, tvMoreHistory, tvLessMoreHistory;
        final ImageView imgbrandlogo, imgQRcode;
        final LinearLayout case1_2row, case2_3row, case3_4row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbrandlogo = itemView.findViewById(R.id.imgbrandlogo);
            tvNameBrand = itemView.findViewById(R.id.tvNameBrand);
            tvNamePackage = itemView.findViewById(R.id.tvNamePackage);
            tvStatusPackage = itemView.findViewById(R.id.tvStatusPackage);
            tvCreditLimitGranted = itemView.findViewById(R.id.tvCreditLimitGranted);
            tvCreditLimitRemaining = itemView.findViewById(R.id.tvCreditLimitRemaining);
            tvCredEXP = itemView.findViewById(R.id.tvCredEXP);
            imgQRcode = itemView.findViewById(R.id.imgQRcode);

            rcHistory = itemView.findViewById(R.id.rcHistory);
            tvMoreHistory = itemView.findViewById(R.id.tvMoreHistory);
            tvLessMoreHistory = itemView.findViewById(R.id.tvLessMoreHistory);
            case1_2row = itemView.findViewById(R.id.case1_2row);
            case2_3row = itemView.findViewById(R.id.case2_3row);
            case3_4row = itemView.findViewById(R.id.case3_4row);
        }
    }

    public interface OnClickListener {
        void onClick(CardDetails item);

        void onClick2(int pos);
    }

//    private void expandRecyclerView(TextView tvHide, TextView tvShow, boolean isExpand, RecyclerView recyclerView) {
//        tvShow.setVisibility(View.VISIBLE);
//        tvHide.setVisibility(View.GONE);
//        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
//        if (isExpand)
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        else
//            // default height 75dp
//            params.height = (int) (100 * context.getResources().getDisplayMetrics().density);
//        recyclerView.setLayoutParams(params);
//    }
}
