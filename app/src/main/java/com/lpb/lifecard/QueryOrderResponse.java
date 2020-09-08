package com.lpb.lifecard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryOrderResponse {
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("orderTime")
    @Expose
    private String orderTime;
    @SerializedName("packageNo")
    @Expose
    private String packageNo;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
