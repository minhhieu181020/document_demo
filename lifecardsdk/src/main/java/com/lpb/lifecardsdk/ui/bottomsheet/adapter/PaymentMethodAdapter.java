package com.lpb.lifecardsdk.ui.bottomsheet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.PaymentMethodResponse;
import com.lpb.lifecardsdk.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {
    private List<PaymentMethodResponse.Datum> list;
    private Context context;
    private OnChangeItemChecked onChangeItemChecked;

    public PaymentMethodAdapter(Context context) {
        list = new ArrayList<>();

        this.context = context;
    }

    public void setItems(List<PaymentMethodResponse.Datum> items) {
        list = items;
        notifyDataSetChanged();
    }

    public void setOnChangeItemChecked(OnChangeItemChecked onChangeItemChecked) {
        this.onChangeItemChecked = onChangeItemChecked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_payment_method, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final PaymentMethodResponse.Datum datum = list.get(i);
        viewHolder.rbMethod.setText(datum.getName());
        viewHolder.rbMethod.setChecked(datum.isChecked());
        if (datum.getLogo() != null) {
            GlideUtils.loadImageUrl(viewHolder.imgMethod, datum.getLogo(), context, Constants.PlaceHolderType.BACKGROUND_CARD);
        }

    }

    public List<PaymentMethodResponse.Datum> getList() {
        return list;
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RadioButton rbMethod;
        private ImageView imgMethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rbMethod = itemView.findViewById(R.id.rbMethod);
            imgMethod = itemView.findViewById(R.id.imgMethod);

            rbMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list == null)
                        return;
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                    }
                    list.get(getAdapterPosition()).setChecked(true);
                    onChangeItemChecked.onChangeChecked(list.get(getAdapterPosition()));
                    notifyDataSetChanged();

                }
            });
            imgMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list == null)
                        return;
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                    }
                    list.get(getAdapterPosition()).setChecked(true);
                    onChangeItemChecked.onChangeChecked(list.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }

    }

    public interface OnChangeItemChecked {
        void onChangeChecked(PaymentMethodResponse.Datum item);
    }
}
