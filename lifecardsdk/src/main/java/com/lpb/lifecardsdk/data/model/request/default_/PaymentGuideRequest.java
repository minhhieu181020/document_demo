package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentGuideRequest {
    @SerializedName("transId")
    @Expose
    private String transId;

    public PaymentGuideRequest(String transId) {
        this.transId = transId;
    }

    @Override
    public String toString() {
        return "PaymentGuideRequest{" +
                "transId='" + transId + '\'' +
                '}';
    }
}
