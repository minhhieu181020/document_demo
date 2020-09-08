package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 28/05/2020
 */
public class OrderInforResponse {
    @SerializedName("cost")
    @Expose
    public Integer cost;
    @SerializedName("providerDTOs")
    @Expose
    public List<ProviderDTO> providerDTOs = null;
    @SerializedName("orderName")
    @Expose
    public String orderName;

    @SerializedName("usageTerms")
    @Expose
    public String usageTerms;

    @SerializedName("image")
    @Expose
    public String image;


    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("orderPrice")
    @Expose
    public String orderPrice;
    @SerializedName("resultCode")
    @Expose
    public String resultCode;
    @SerializedName("resultDesc")
    @Expose
    public String resultDesc;

    public Integer getCost() {
        return cost;
    }

    public String getUsageTerms() {
        return usageTerms;
    }

    public String getImage() {
        return image;
    }

    public List<ProviderDTO> getProviderDTOs() {
        return providerDTOs;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public class ProviderDTO {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("headquarterPhone")
        @Expose
        public String headquarterPhone;
        @SerializedName("headquarterAddress")
        @Expose
        public String headquarterAddress;
        @SerializedName("logo")
        @Expose
        public String logo;
        @SerializedName("email")
        @Expose
        public String email;

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
}
