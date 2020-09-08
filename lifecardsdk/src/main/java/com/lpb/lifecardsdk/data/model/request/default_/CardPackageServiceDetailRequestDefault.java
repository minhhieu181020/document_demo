package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardPackageServiceDetailRequestDefault {

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("code")
    @Expose
    private String code;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CardPackageServiceDetailRequestDefault(String mobilePhone, String code) {
        this.mobilePhone = mobilePhone;
        this.code = code;
    }
}