package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCardDetailsReqestDefault {

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("isPaid")
    @Expose
    private Boolean isPaid;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public MyCardDetailsReqestDefault(String mobilePhone, String cardNo, Boolean isPaid) {
        this.mobilePhone = mobilePhone;
        this.cardNo = cardNo;
        this.isPaid = isPaid;
    }
}