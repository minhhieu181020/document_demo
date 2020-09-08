package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 17/07/2020
 */
public class MobileConfigRequest {
    @SerializedName("configCodes")
    @Expose
    private List<String> configCodes = null;

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setConfigCodes(List<String> configCodes) {
        this.configCodes = configCodes;
    }
}
