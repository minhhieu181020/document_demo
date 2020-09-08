package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class DefCard {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("listedPrice")
    @Expose
    private String listedPrice;
    @SerializedName("priceNumber")
    @Expose
    private Long priceNumber;
    @SerializedName("listedPriceNumber")
    @Expose
    private Long listedPriceNumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("discountValueNumber")
    @Expose
    private Long discountValueNumber;
    @SerializedName("discountValue")
    @Expose
    private String discountValue;
    @SerializedName("total")
    @Expose
    private Long total;
    @SerializedName("providerDtos")
    @Expose
    private List<ProviderDto> providerDtos = null;
    @SerializedName("providerIds")
    @Expose
    private String providerIds;
    @SerializedName("promotions")
    @Expose
    private String promotions;
    @SerializedName("promotionDtos")
    @Expose
    private List<PromotionDto> promotionDtos = null;
    @SerializedName("defServiceDtos")
    @Expose
    private List<Object> defServiceDtos = null;
    @SerializedName("descBrief")
    @Expose
    private String descBrief;


    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getListedPrice() {
        return listedPrice;
    }

    public Long getPriceNumber() {
        return priceNumber;
    }

    public Long getListedPriceNumber() {
        return listedPriceNumber;
    }

    public String getImage() {
        return image;
    }

    public Long getDiscountValueNumber() {
        return discountValueNumber;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public Long getTotal() {
        return total;
    }

    public List<ProviderDto> getProviderDtos() {
        return providerDtos;
    }

    public String getProviderIds() {
        return providerIds;
    }

    public String getPromotions() {
        return promotions;
    }

    public List<PromotionDto> getPromotionDtos() {
        return promotionDtos;
    }

    public List<Object> getDefServiceDtos() {
        return defServiceDtos;
    }

    public String getDescBrief() {
        return descBrief;
    }
}
