package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardPhysicalRequestDefault {

    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("token")
    @Expose
    private String token;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public CardPhysicalRequestDefault(String cardNo, String customerName, String mobilePhone, String token) {
        this.cardNo = cardNo;
        this.customerName = customerName;
        this.mobilePhone = mobilePhone;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}