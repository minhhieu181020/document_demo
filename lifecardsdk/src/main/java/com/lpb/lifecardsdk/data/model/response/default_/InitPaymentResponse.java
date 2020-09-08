package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class
InitPaymentResponse {
    @SerializedName("cost")
    @Expose
    public Integer cost;
    @SerializedName("customerName")
    @Expose
    public String customerName;
    @SerializedName("customerMobilePhone")
    @Expose
    public String customerMobilePhone;
    @SerializedName("defCardName")
    @Expose
    public String defCardName;
    @SerializedName("cardNo")
    @Expose
    public String cardNo;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("amountVnd")
    @Expose
    public String amountVnd;
    @SerializedName("orderNo")
    @Expose
    public String orderNo;
    @SerializedName("usageTerms")
    @Expose
    public String usageTerms;
    @SerializedName("providerDTOs")
    @Expose
    public List<ProviderDTO> providerDTOs = null;
    @SerializedName("resultCode")
    @Expose
    public String resultCode;
    @SerializedName("resultDesc")
    @Expose
    public String resultDesc;

    public Integer getCost() {
        return cost;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobilePhone() {
        return customerMobilePhone;
    }

    public String getDefCardName() {
        return defCardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getAmountVnd() {
        return amountVnd;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getUsageTerms() {
        return usageTerms;
    }

    public List<ProviderDTO> getProviderDTOs() {
        return providerDTOs;
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
