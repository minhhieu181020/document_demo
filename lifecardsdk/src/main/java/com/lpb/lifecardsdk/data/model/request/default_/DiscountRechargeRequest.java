package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 10/08/2020
 */
public class DiscountRechargeRequest {
    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    @SerializedName("amount")
    @Expose
    private String amount;

    public DiscountRechargeRequest(String accountNo, String amount) {
        this.accountNo = accountNo;
        this.amount = amount;
    }
}
