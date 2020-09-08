package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryRequest {
    @SerializedName("providerId")
    @Expose
    private String providerId;
    @SerializedName("type")
    @Expose
    private String type;


    public CategoryRequest(String providerId, String type) {
        this.providerId = providerId;
        this.type = type;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "providerId='" + providerId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
