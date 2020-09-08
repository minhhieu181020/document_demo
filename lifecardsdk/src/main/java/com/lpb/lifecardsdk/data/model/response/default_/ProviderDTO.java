package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vannh.lvt on 07/08/2020
 */
public class ProviderDTO implements Serializable {
    @SerializedName("hotline")
    @Expose
    private String hotline;
    @SerializedName("usageGuide")
    @Expose
    private String usageGuide;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("headquarterPhone")
    @Expose
    private String headquarterPhone;
    @SerializedName("headquarterAddress")
    @Expose
    private String headquarterAddress;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("email")
    @Expose
    private String email;

    public String getHotline() {
        return hotline;
    }

    public String getUsageGuide() {
        return usageGuide;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeadquarterPhone() {
        return headquarterPhone;
    }

    public String getHeadquarterAddress() {
        return headquarterAddress;
    }

    public String getLogo() {
        return logo;
    }

    public String getEmail() {
        return email;
    }
}
