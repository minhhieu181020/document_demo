package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderRequest {

    public ProviderRequest(String providerCode, String providerId) {
        this.providerCode = providerCode;
        this.providerId = providerId;
    }
    @SerializedName("providerCode")
    @Expose
    private String providerCode;
    @SerializedName("providerId")
    @Expose
    private String providerId;

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "ProviderRequest{" +
                "providerCode='" + providerCode + '\'' +
                ", providerId='" + providerId + '\'' +
                '}';
    }
}
