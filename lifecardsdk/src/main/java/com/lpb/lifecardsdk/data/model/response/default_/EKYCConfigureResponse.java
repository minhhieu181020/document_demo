package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 15/07/2020
 */
public class EKYCConfigureResponse {
    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("actions")
    @Expose
    private List<String> actions = null;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("numberOfActions")
    @Expose
    private String numberOfActions;
    @SerializedName("imageQuality")
    @Expose
    private String imageQuality;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public List<String> getActions() {
        return actions;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getNumberOfActions() {
        return numberOfActions;
    }

    public String getImageQuality() {
        return imageQuality;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }
}
