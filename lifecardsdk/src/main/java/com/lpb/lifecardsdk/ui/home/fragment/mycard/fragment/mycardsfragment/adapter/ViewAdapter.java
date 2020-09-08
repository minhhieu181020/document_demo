package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.mycardsfragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.Card;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    private List<Card> cardList;
    private OnClickListener onMenuClickListener;
    private Context context;

    public ViewAdapter(Context context) {
        cardList = new ArrayList<>();
        this.context = context;
    }


    public void setItems(List<Card> items) {
        cardList.clear();
        cardList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_fragment_expire, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onMenuClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Card card = cardList.get(position);
        holder.tvNameCards.setText(card.getTvNameCards());
        holder.tvNumberCard.setText(card.getTvNumberCard());
        holder.imgAvatar.setImageResource(card.getImgAvatar());



        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null)
                    onMenuClickListener.onClick(cardList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if (cardList == null) return 0;
        return cardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNameCards, tvNumberCard;
        final ImageView imgAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCards = itemView.findViewById(R.id.tvNameCards);
            tvNumberCard = itemView.findViewById(R.id.tvNumberCard);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

        }
    }

    public interface OnClickListener {
        void onClick(Card item);
    }
}
