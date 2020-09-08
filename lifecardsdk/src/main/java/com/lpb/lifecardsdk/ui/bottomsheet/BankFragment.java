package com.lpb.lifecardsdk.ui.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.data.model.BankItem;
import com.lpb.lifecardsdk.ui.bottomsheet.adapter.BankAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class BankFragment extends BottomSheetDialogFragment {
    private RecyclerView rvContent;
    private BankAdapter bankAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.LifeCardSDK_AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lifecardsdk_bank_bottom_sheet, container, false);
        rvContent = view.findViewById(R.id.rvContent);
        bankAdapter = new BankAdapter(getActivity());
        rvContent.setAdapter(bankAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bankAdapter.setOnClickListener(new BankAdapter.OnClickListener() {
            @Override
            public void onClickItem(BankItem bankItem) {
                Toast.makeText(getActivity(), "Sao chep", Toast.LENGTH_SHORT).show();
                // TODO: 02/06/2020
            }
        });

        List<BankItem> bankItems = new ArrayList<>();
        BankItem bankItem1 = new BankItem("Công ty BTEK", "Ngân hàng Việt Nam Thịnh Vượng", "Hội sở chính", "0124567958");
        BankItem bankItem2 = new BankItem("Công ty BTEK", "Ngân hàng Việt Nam Thịnh Vượng", "Hội sở chính", "0124567958");
        BankItem bankItem3 = new BankItem("Công ty BTEK", "Ngân hàng Việt Nam Thịnh Vượng", "Hội sở chính", "0124567958");
        bankItems.add(bankItem1);
        bankItems.add(bankItem2);
        bankItems.add(bankItem3);
        bankAdapter.setItems(bankItems);
    }
}

