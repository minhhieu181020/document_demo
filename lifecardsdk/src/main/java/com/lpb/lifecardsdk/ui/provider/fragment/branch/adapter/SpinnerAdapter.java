package com.lpb.lifecardsdk.ui.provider.fragment.branch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.response.default_.AreaResponse;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<AreaResponse.ListArea> listAreas;
    private LayoutInflater inflter;
    private OnClickListener onClickListener;
    public SpinnerAdapter(Context context) {
        this.context = context;
        listAreas = new ArrayList<>();
        inflter = (LayoutInflater.from(context));
    }

    public void setItems(List<AreaResponse.ListArea> items) {
        listAreas.clear();
        listAreas = items;
        notifyDataSetChanged();
    }

    public void clearData() {
        listAreas.clear();
        notifyDataSetChanged();
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    public int getCount() {
        return listAreas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.lifecardsdk_item_spinner, null);
        TextView area = view.findViewById(R.id.tvContent);
        area.setText(listAreas.get(i).getAreaName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(listAreas.get(i));
            }
        });
        return view;
    }

    public interface OnClickListener {
        void onClick(AreaResponse.ListArea item);
    }
}
