package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class CardListConfig {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("providerId")
    @Expose
    private Long providerId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("orderNo")
    @Expose
    private Long orderNo;
    @SerializedName("cardGroup")
    @Expose
    private CardGroup cardGroup;
    @SerializedName("defcard")
    @Expose
    private DefCard defcard;

    public Long getId() {
        return id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getType() {
        return type;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public CardGroup getCardGroup() {
        return cardGroup;
    }

    public DefCard getDefcard() {
        return defcard;
    }
}
