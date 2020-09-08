package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vannh.lvt on 07/08/2020
 */
public class RechargeResponse implements Serializable {
    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("rechargeType")
    @Expose
    private String rechargeType;
    @SerializedName("fromValue")
    @Expose
    private Long fromValue;
    @SerializedName("toValue")
    @Expose
    private Long toValue;
    @SerializedName("fromValueDisplay")
    @Expose
    private String fromValueDisplay;
    @SerializedName("toValueDisplay")
    @Expose
    private String toValueDisplay;
    @SerializedName("providerDtos")
    @Expose
    private List<ProviderDTO> providerDtos = null;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("ownCardDto")
    @Expose
    private OwnCardDto ownCardDto;
    @SerializedName("defRechargeDtos")
    @Expose
    private List<DefRechargeDto> defRechargeDtos = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public Long getFromValue() {
        return fromValue;
    }

    public Long getToValue() {
        return toValue;
    }

    public String getFromValueDisplay() {
        return fromValueDisplay;
    }

    public String getToValueDisplay() {
        return toValueDisplay;
    }

    public List<ProviderDTO> getProviderDtos() {
        return providerDtos;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public OwnCardDto getOwnCardDto() {
        return ownCardDto;
    }

    public List<DefRechargeDto> getDefRechargeDtos() {
        return defRechargeDtos;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public class OwnCardDto implements Serializable{

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("styleDto")
        @Expose
        private StyleDto styleDto;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<Object> ownServiceDtos = null;
        @SerializedName("choose")
        @Expose
        private Boolean choose;

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getCardNoDisplay() {
            return cardNoDisplay;
        }

        public String getCardNo() {
            return cardNo;
        }

        public String getImage() {
            return image;
        }

        public StyleDto getStyleDto() {
            return styleDto;
        }

        public List<Object> getOwnServiceDtos() {
            return ownServiceDtos;
        }

        public Boolean getChoose() {
            return choose;
        }
    }
    public class StyleDto implements Serializable {

        @SerializedName("rgb")
        @Expose
        private String rgb;

        @SerializedName("appStyle")
        @Expose
        private Style appStyle;

        public String getRgb() {
            return rgb;
        }

        public Style getAppStyle() {
            return appStyle;
        }
    }
    public class DefRechargeDto implements Serializable{
        private boolean checked;

        @SerializedName("price")
        @Expose
        private Long price;

        @SerializedName("id")
        @Expose
        private Long id;

        @SerializedName("listedPrice")
        @Expose
        private Long listedPrice;
        @SerializedName("discount")
        @Expose
        private Float discount;
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

        public Float getDiscount() {
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

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public Long getId() {
            return id;
        }

        @Override
        public String toString() {
            return priceDisplay + " - Giá: " + listedPriceDisplay;
        }
    }
}
