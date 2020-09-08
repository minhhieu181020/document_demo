package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 07/08/2020
 */
public class RechargeRequest {
    @SerializedName("code")
    @Expose
    private String code;

    public RechargeRequest(String code) {
        this.code = code;
    }
}
