package com.lpb.lifecard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("packageNo")
    @Expose
    private String packageNo;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("refNo")
    @Expose
    private String refNo;

    public PaymentRequest(Integer amount, String packageNo, String orderNo, String refNo) {
        this.amount = amount;
        this.packageNo = packageNo;
        this.orderNo = orderNo;
        this.refNo = refNo;
    }
}
