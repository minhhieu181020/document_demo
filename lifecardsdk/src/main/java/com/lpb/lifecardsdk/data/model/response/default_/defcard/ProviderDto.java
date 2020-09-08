package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class ProviderDto {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getImage() {
        return image;
    }
}
