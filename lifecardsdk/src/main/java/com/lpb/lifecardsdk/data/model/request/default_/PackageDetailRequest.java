package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageDetailRequest {
    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PackageDetailRequest(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PackageDetailRequest{" +
                "code='" + code + '\'' +
                '}';
    }
}
