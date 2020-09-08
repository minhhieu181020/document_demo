package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentWaitResponse {
    @SerializedName("cost")
    @Expose
    public Integer cost;
    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("paymentGuide")
    @Expose
    public String paymentGuide;
    @SerializedName("providerIds")
    @Expose
    public List<Integer> providerIds = null;
    @SerializedName("expirationDate")
    @Expose
    public String expirationDate;
    @SerializedName("expirationTime")
    @Expose
    public String expirationTime;
    @SerializedName("resultCode")
    @Expose
    public String resultCode;
    @SerializedName("cardNo")
    @Expose
    public String cardNo;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Integer getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentGuide() {
        return paymentGuide;
    }

    public void setPaymentGuide(String paymentGuide) {
        this.paymentGuide = paymentGuide;
    }

    public List<Integer> getProviderIds() {
        return providerIds;
    }

    public void setProviderIds(List<Integer> providerIds) {
        this.providerIds = providerIds;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}