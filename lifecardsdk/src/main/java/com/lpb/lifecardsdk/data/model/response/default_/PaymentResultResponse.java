package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentResultResponse {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("templatePromotion")
    @Expose
    private String templatePromotion;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("entryTimeDetail")
    @Expose
    private String entryTimeDetail;
    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTemplatePromotion() {
        return templatePromotion;
    }

    public void setTemplatePromotion(String templatePromotion) {
        this.templatePromotion = templatePromotion;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEntryTimeDetail() {
        return entryTimeDetail;
    }

    public void setEntryTimeDetail(String entryTimeDetail) {
        this.entryTimeDetail = entryTimeDetail;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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