package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class PromotionDto {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("urlIcon")
    @Expose
    private String urlIcon;

    public String getDescription() {
        return description;
    }

    public String getUrlIcon() {
        return urlIcon;
    }
}
