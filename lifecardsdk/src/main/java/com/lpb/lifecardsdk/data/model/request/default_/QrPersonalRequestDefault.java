package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrPersonalRequestDefault {
    public QrPersonalRequestDefault(String cardNo, String mobilePhone, String ownServiceCode) {
        this.cardNo = cardNo;
        this.mobilePhone = mobilePhone;
        this.ownServiceCode = ownServiceCode;
    }

    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("ownServiceCode")
    @Expose
    private String ownServiceCode;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOwnServiceCode() {
        return ownServiceCode;
    }

    public void setOwnServiceCode(String ownServiceCode) {
        this.ownServiceCode = ownServiceCode;
    }

}