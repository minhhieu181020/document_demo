package com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.data.model.response.default_.ListCardResponseDefault;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

public class ListOwnServiceAdapter extends RecyclerView.Adapter<ListOwnServiceAdapter.ViewHolder> {
    private List<ListCardResponseDefault.OwnServiceDto> ownCardDtoList;
    private Context context;
    private MyCardStillValidateAdapter.onClickOwnService onClickOwnService;
    private String code, cardNoDisplay;

    public ListOwnServiceAdapter(Context context, List<ListCardResponseDefault.OwnServiceDto> ownCardDtoList, String code, String cardNoDisplay, MyCardStillValidateAdapter.onClickOwnService onClickOwnService) {
        this.ownCardDtoList = ownCardDtoList;
        this.context = context;
        this.onClickOwnService = onClickOwnService;
        this.code = code;
        this.cardNoDisplay = cardNoDisplay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lifecardsdk_item_list_service_my_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvNameService.setText(StringUtils.convertHTMLToString(ownCardDtoList.get(i).getNamDefService(),context));
        if (ownCardDtoList.get(i).getOwnAccountDtos() != null) {
            if (ownCardDtoList.get(i).getOwnAccountDtos().size() > 0)
                viewHolder.tvLimitService.setText(StringUtils.convertHTMLToString(ownCardDtoList.get(i).getOwnAccountDtos().get(0).getAvailableUnit(),context));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickOwnService != null)
                    onClickOwnService.onClickOwnService(code, cardNoDisplay,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (ownCardDtoList == null ? 0 : (ownCardDtoList.size() < Config.MAX_ITEM_PROMOTION ? ownCardDtoList.size() : Config.MAX_ITEM_PROMOTION));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNameService, tvLimitService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameService = itemView.findViewById(R.id.tvNameService);
            tvLimitService = itemView.findViewById(R.id.tvLimitService);
        }
    }
}
