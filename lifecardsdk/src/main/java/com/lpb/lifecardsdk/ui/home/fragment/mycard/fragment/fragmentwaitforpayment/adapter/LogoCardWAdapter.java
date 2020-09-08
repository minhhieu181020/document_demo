package com.lpb.lifecardsdk.ui.home.fragment.mycard.fragment.fragmentwaitforpayment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpb.lifecardsdk.R;

public class LogoCardWAdapter extends RecyclerView.Adapter<LogoCardWAdapter.ViewHolder> {

    private int[] listLogo;
    private Context context;

    public LogoCardWAdapter(Context context, int[] listLogo) {
        this.listLogo = listLogo;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_logo_card, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.imgLogo.setImageResource(listLogo[position]);
    }

    @Override
    public int getItemCount() {
        if (listLogo == null) return 0;
        return listLogo.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgLogo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogo = itemView.findViewById(R.id.imglogocard);

        }
    }
}

