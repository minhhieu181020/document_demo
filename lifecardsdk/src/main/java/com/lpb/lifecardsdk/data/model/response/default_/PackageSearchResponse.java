package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageSearchResponse {
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("defCardDtos")
    @Expose
    private List<DefCardDto> defCardDtos = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<DefCardDto> getDefCardDtos() {
        return defCardDtos;
    }

    public void setDefCardDtos(List<DefCardDto> defCardDtos) {
        this.defCardDtos = defCardDtos;
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
    public class DefCardDto {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        private Integer priceNumber;
        @SerializedName("listedPriceNumber")
        @Expose
        private Integer listedPriceNumber;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("discountValueNumber")
        @Expose
        private Integer discountValueNumber;
        @SerializedName("discountValue")
        @Expose
        private String discountValue;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("providerDtos")
        @Expose
        private List<ProviderDto> providerDtos = null;
        @SerializedName("providerIds")
        @Expose
        private String providerIds;
        @SerializedName("defServiceDtos")
        @Expose
        private List<Object> defServiceDtos = null;
        @SerializedName("promotions")
        @Expose
        private String promotions;

        @SerializedName("descBrief")
        @Expose
        private String descBrief;


        @SerializedName("promotionDtos")
        @Expose
        private List<PromotionDto> promotionDto = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public String getDescBrief() {
            return descBrief;
        }

        public void setDescBrief(String descBrief) {
            this.descBrief = descBrief;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getListedPrice() {
            return listedPrice;
        }

        public void setListedPrice(String listedPrice) {
            this.listedPrice = listedPrice;
        }

        public Integer getPriceNumber() {
            return priceNumber;
        }

        public void setPriceNumber(Integer priceNumber) {
            this.priceNumber = priceNumber;
        }

        public Integer getListedPriceNumber() {
            return listedPriceNumber;
        }

        public void setListedPriceNumber(Integer listedPriceNumber) {
            this.listedPriceNumber = listedPriceNumber;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getDiscountValueNumber() {
            return discountValueNumber;
        }

        public void setDiscountValueNumber(Integer discountValueNumber) {
            this.discountValueNumber = discountValueNumber;
        }

        public String getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(String discountValue) {
            this.discountValue = discountValue;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<ProviderDto> getProviderDtos() {
            return providerDtos;
        }

        public void setProviderDtos(List<ProviderDto> providerDtos) {
            this.providerDtos = providerDtos;
        }

        public String getProviderIds() {
            return providerIds;
        }

        public void setProviderIds(String providerIds) {
            this.providerIds = providerIds;
        }

        public List<Object> getDefServiceDtos() {
            return defServiceDtos;
        }

        public void setDefServiceDtos(List<Object> defServiceDtos) {
            this.defServiceDtos = defServiceDtos;
        }



        public String getPromotions() {
            return promotions;
        }

        public void setPromotions(String promotions) {
            this.promotions = promotions;
        }

        public List<PromotionDto> getPromotionDto() {
            return promotionDto;
        }

        public void setPromotionDto(List<PromotionDto> promotionDto) {
            this.promotionDto = promotionDto;
        }

    }
    public class PromotionDto {

        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("urlIcon")
        @Expose
        private String urlIcon;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrlIcon() {
            return urlIcon;
        }

        public void setUrlIcon(String urlIcon) {
            this.urlIcon = urlIcon;
        }

        @Override
        public String toString() {
            return "PromotionDto{" +
                    "description='" + description + '\'' +
                    ", urlIcon='" + urlIcon + '\'' +
                    '}';
        }
    }
    public class ProviderDto {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("image")
        @Expose
        private String image;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}
