package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrPersonalResponseDefault {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("timeToken")
    @Expose
    private Integer timeToken;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    @SerializedName("guideFaceRegistration")
    @Expose
    private String guideFaceRegistration;

    @SerializedName("faceRegistrationStatus")
    @Expose
    private String faceRegistrationStatus;

    public String getGuideFaceRegistration() {
        return guideFaceRegistration;
    }

    public String getFaceRegistrationStatus() {
        return faceRegistrationStatus;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTimeToken() {
        return timeToken;
    }

    public void setTimeToken(Integer timeToken) {
        this.timeToken = timeToken;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

}