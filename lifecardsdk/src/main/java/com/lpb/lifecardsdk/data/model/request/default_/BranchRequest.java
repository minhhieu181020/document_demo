package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchRequest {
    @SerializedName("areaCode")
    @Expose
    private String areaCode;
    @SerializedName("areaType")
    @Expose
    private String areaType;
    @SerializedName("providerId")
    @Expose
    private String providerId;
    @SerializedName("pageIndex")
    @Expose
    private int pageIndex;
    @SerializedName("rowCount")
    @Expose
    private int rowCount;

    @SerializedName("containProvider")
    @Expose
    private boolean containProvider;


    public BranchRequest(String areaCode, String areaType, String providerId, int pageIndex, int rowCount, boolean containProvider) {
        this.areaCode = areaCode;
        this.areaType = areaType;
        this.providerId = providerId;
        this.pageIndex = pageIndex;
        this.rowCount = rowCount;
        this.containProvider = containProvider;
    }

    @Override
    public String toString() {
        return "BranchRequest{" +
                "areaCode='" + areaCode + '\'' +
                ", areaType='" + areaType + '\'' +
                ", providerId='" + providerId + '\'' +
                ", pageIndex=" + pageIndex +
                ", rowCount=" + rowCount +
                ", containProvider=" + containProvider +
                '}';
    }
}
