package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vannh.lvt on 14/08/2020
 */
public class PaymentWaitRechargeResponse implements Serializable {
    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("paymentGuide")
    @Expose
    private String paymentGuide;

    @SerializedName("transId")
    @Expose
    private String transId;

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaymentGuide() {
        return paymentGuide;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public String getTransId() {
        return transId;
    }
}
