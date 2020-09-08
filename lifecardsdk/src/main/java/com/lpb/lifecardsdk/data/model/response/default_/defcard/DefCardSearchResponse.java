package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class DefCardSearchResponse {
    @SerializedName("cardListConfig")
    @Expose
    private List<CardListConfig> cardListConfig = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public List<CardListConfig> getCardListConfig() {
        return cardListConfig;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

}
