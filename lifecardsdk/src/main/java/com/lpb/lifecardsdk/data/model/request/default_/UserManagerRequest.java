package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 29/07/2020
 */
public class UserManagerRequest {
    @SerializedName("cardNo")
    @Expose
    private String cardNo;

    public UserManagerRequest(String cardNo) {
        this.cardNo = cardNo;
    }
}
