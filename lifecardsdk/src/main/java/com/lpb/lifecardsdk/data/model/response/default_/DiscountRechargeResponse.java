package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 10/08/2020
 */
public class DiscountRechargeResponse {
    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("defRechargeDto")
    @Expose
    private DefRechargeDto defRechargeDto;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public DefRechargeDto getDefRechargeDto() {
        return defRechargeDto;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public class DefRechargeDto{

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("price")
        @Expose
        private Long price;
        @SerializedName("listedPrice")
        @Expose
        private Long listedPrice;
        @SerializedName("discount")
        @Expose
        private Long discount;
        @SerializedName("numberOfExpireDate")
        @Expose
        private Long numberOfExpireDate;
        @SerializedName("time_extend_unit")
        @Expose
        private String timeExtendUnit;
        @SerializedName("priceDisplay")
        @Expose
        private String priceDisplay;
        @SerializedName("listedPriceDisplay")
        @Expose
        private String listedPriceDisplay;
        @SerializedName("discountDisplay")
        @Expose
        private String discountDisplay;
        @SerializedName("expireDate")
        @Expose
        private String expireDate;

        public Long getPrice() {
            return price;
        }

        public Long getListedPrice() {
            return listedPrice;
        }

        public Long getDiscount() {
            return discount;
        }

        public Long getNumberOfExpireDate() {
            return numberOfExpireDate;
        }

        public String getTimeExtendUnit() {
            return timeExtendUnit;
        }

        public String getPriceDisplay() {
            return priceDisplay;
        }

        public String getListedPriceDisplay() {
            return listedPriceDisplay;
        }

        public String getDiscountDisplay() {
            return discountDisplay;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public Long getId() {
            return id;
        }
    }
}
