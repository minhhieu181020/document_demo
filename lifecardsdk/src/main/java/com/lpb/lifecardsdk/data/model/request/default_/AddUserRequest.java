package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 29/07/2020
 */
public class AddUserRequest {
    @SerializedName("cardNo")
    @Expose
    private String cardNo;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;

    public AddUserRequest(String cardNo, String customerName, String mobilePhone) {
        this.cardNo = cardNo;
        this.customerName = customerName;
        this.mobilePhone = mobilePhone;
    }
}
