package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResultRequest {
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;

    public PaymentResultRequest(String mobilePhone, String orderNo, String paymentStatus) {
        this.mobilePhone = mobilePhone;
        this.orderNo = orderNo;
        this.paymentStatus = paymentStatus;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentResultRequest{" +
                "mobilePhone='" + mobilePhone + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
