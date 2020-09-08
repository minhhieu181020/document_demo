package com.lpb.lifecardsdk.data.model.response.default_.mobile_config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class MobileConfigResponse {
    @SerializedName("faceRegistrationStatus")
    @Expose
    private String faceRegistrationStatus;

    @SerializedName("faceRegisteredInfo")
    @Expose
    private String faceRegisteredInfo;
    @SerializedName("faceRegisteredTime")
    @Expose
    private String faceRegisteredTime;

    @SerializedName("faceDescription")
    @Expose
    private String faceDescription;


    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("data")
    @Expose
    private List<DataConfig> dataConfigs = null;

    public String getFaceRegistrationStatus() {
        return faceRegistrationStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public List<DataConfig> getDataConfigs() {
        return dataConfigs;
    }

    public String getFaceRegisteredInfo() {
        return faceRegisteredInfo;
    }

    public String getFaceRegisteredTime() {
        return faceRegisteredTime;
    }

    public String getFaceDescription() {
        return faceDescription;
    }
}
