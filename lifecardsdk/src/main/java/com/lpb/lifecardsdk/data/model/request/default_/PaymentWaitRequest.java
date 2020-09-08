package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentWaitRequest {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("defCardNo")
    @Expose
    private String defCardNo;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;

    public PaymentWaitRequest(Integer amount, String defCardNo, String mobilePhone, String orderNo) {
        this.amount = amount;
        this.defCardNo = defCardNo;
        this.mobilePhone = mobilePhone;
        this.orderNo = orderNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDefCardNo() {
        return defCardNo;
    }

    public void setDefCardNo(String defCardNo) {
        this.defCardNo = defCardNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
