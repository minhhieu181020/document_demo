package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 19/06/2020
 */
public class GuideResponse {
    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("providerDTOs")
    @Expose
    private List<ProviderDTO> providerDTOs = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public void setProviderDTOs(List<ProviderDTO> providerDTOs) {
        this.providerDTOs = providerDTOs;
    }

    public Long getCost() {
        return cost;
    }

    public List<ProviderDTO> getProviderDTOs() {
        return providerDTOs;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }



}
