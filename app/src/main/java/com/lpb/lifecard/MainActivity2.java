package com.lpb.lifecard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lpb.lifecardsdk.data.model.response.default_.Style;
import com.lpb.lifecardsdk.widget.TextViewUtils;
import com.lpb.lifecardsdk.widget.autofilltextview.AutofitTextView;

public class MainActivity2 extends AppCompatActivity {
    private AutofitTextView tvNumberCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvNumberCard = findViewById(R.id.tvNumberCard);

        Style style = new Style();
        style.setGravityHorizontal("left");
        TextViewUtils.setStyleTextView(tvNumberCard,this,style,"#000000");
        tvNumberCard.setText("2100 0000 0000 123");
    }
}