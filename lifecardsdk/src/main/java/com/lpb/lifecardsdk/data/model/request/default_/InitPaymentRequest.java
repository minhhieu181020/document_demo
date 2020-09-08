package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitPaymentRequest {

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("defCardCode")
    @Expose
    private String defCardCode;

    public InitPaymentRequest(String mobilePhone, String customerName, String defCardCode) {
        this.mobilePhone = mobilePhone;
        this.customerName = customerName;
        this.defCardCode = defCardCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDefCardCode() {
        return defCardCode;
    }

    public void setDefCardCode(String defCardCode) {
        this.defCardCode = defCardCode;
    }

    @Override
    public String toString() {
        return "InitPaymentRequest{" +
                "mobilePhone='" + mobilePhone + '\'' +
                ", customerName='" + customerName + '\'' +
                ", defCardCode='" + defCardCode + '\'' +
                '}';
    }
}
