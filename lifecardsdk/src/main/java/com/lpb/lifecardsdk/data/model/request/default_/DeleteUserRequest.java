package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 29/07/2020
 */
public class DeleteUserRequest {
    @SerializedName("cardNo")
    @Expose
    private String cardNo;

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;

    public DeleteUserRequest(String cardNo, String mobilePhone) {
        this.cardNo = cardNo;
        this.mobilePhone = mobilePhone;
    }
}
