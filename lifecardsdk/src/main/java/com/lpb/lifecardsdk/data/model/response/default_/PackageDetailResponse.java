package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PackageDetailResponse implements Serializable {
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("defCardDto")
    @Expose
    private DefCardDto defCardDto;
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

    public DefCardDto getDefCardDto() {
        return defCardDto;
    }

    public void setDefCardDto(DefCardDto defCardDto) {
        this.defCardDto = defCardDto;
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
    public class DefCardDto implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("descBrief")
        @Expose
        private String descBrief;
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
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("usageTerms")
        @Expose
        private String usageTerms;
        @SerializedName("discountValueNumber")
        @Expose
        private Integer discountValueNumber;
        @SerializedName("discountValue")
        @Expose
        private String discountValue;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("promotionDtos")
        @Expose
        private List<PromotionDto> promotionDtos = null;
        @SerializedName("defServiceDtos")
        @Expose
        private List<DefServiceDto> defServiceDtos = null;
        @SerializedName("freeServices")
        @Expose
        private List<DefServiceDto> freeServices = null;

        @SerializedName("rootService")
        @Expose
        private RootService rootService;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
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

        public String getDescBrief() {
            return descBrief;
        }

        public void setDescBrief(String descBrief) {
            this.descBrief = descBrief;
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

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUsageTerms() {
            return usageTerms;
        }

        public void setUsageTerms(String usageTerms) {
            this.usageTerms = usageTerms;
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

        public List<PromotionDto> getPromotionDtos() {
            return promotionDtos;
        }

        public void setPromotionDtos(List<PromotionDto> promotionDtos) {
            this.promotionDtos = promotionDtos;
        }

        public List<DefServiceDto> getDefServiceDtos() {
            return defServiceDtos;
        }

        public void setDefServiceDtos(List<DefServiceDto> defServiceDtos) {
            this.defServiceDtos = defServiceDtos;
        }

        public RootService getRootService() {
            return rootService;
        }

        public void setRootService(RootService rootService) {
            this.rootService = rootService;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<DefServiceDto> getFreeServices() {
            return freeServices;
        }
    }
    public class DefServiceDto implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("listedPrice")
        @Expose
        private String listedPrice;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("listedPriceNumber")
        @Expose
        private Integer listedPriceNumber;
        @SerializedName("priceNumber")
        @Expose
        private Integer priceNumber;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("usageDesciption")
        @Expose
        private String usageDesciption;
        @SerializedName("insertTime")
        @Expose
        private String insertTime;
        @SerializedName("expiryDate")
        @Expose
        private String expiryDate;
        @SerializedName("providerDto")
        @Expose
        private ProviderDto providerDto;
        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;
        @SerializedName("limitDetailDescription")
        @Expose
        private String limitDetailDescription;
        @SerializedName("rootService")
        @Expose
        private String rootService;
        @SerializedName("defServiceUnitDtos")
        @Expose
        private List<DefServiceUnitDto> defServiceUnitDtos = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getListedPrice() {
            return listedPrice;
        }

        public void setListedPrice(String listedPrice) {
            this.listedPrice = listedPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getListedPriceNumber() {
            return listedPriceNumber;
        }

        public void setListedPriceNumber(Integer listedPriceNumber) {
            this.listedPriceNumber = listedPriceNumber;
        }

        public Integer getPriceNumber() {
            return priceNumber;
        }

        public void setPriceNumber(Integer priceNumber) {
            this.priceNumber = priceNumber;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getUsageDesciption() {
            return usageDesciption;
        }

        public void setUsageDesciption(String usageDesciption) {
            this.usageDesciption = usageDesciption;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public ProviderDto getProviderDto() {
            return providerDto;
        }

        public void setProviderDto(ProviderDto providerDto) {
            this.providerDto = providerDto;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getLimitDetailDescription() {
            return limitDetailDescription;
        }

        public void setLimitDetailDescription(String limitDetailDescription) {
            this.limitDetailDescription = limitDetailDescription;
        }

        public String getRootService() {
            return rootService;
        }

        public void setRootService(String rootService) {
            this.rootService = rootService;
        }

        public List<DefServiceUnitDto> getDefServiceUnitDtos() {
            return defServiceUnitDtos;
        }

        public void setDefServiceUnitDtos(List<DefServiceUnitDto> defServiceUnitDtos) {
            this.defServiceUnitDtos = defServiceUnitDtos;
        }


    }
    public class DefServiceUnitDto implements Serializable{

        @SerializedName("displayLimitUnit")
        @Expose
        private String displayLimitUnit;
        @SerializedName("unitCode")
        @Expose
        private String unitCode;

        public String getDisplayLimitUnit() {
            return displayLimitUnit;
        }

        public void setDisplayLimitUnit(String displayLimitUnit) {
            this.displayLimitUnit = displayLimitUnit;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }


    }
    public class DefServiceUnitDto_ implements Serializable{

        @SerializedName("displayLimitUnit")
        @Expose
        private String displayLimitUnit;
        @SerializedName("unitCode")
        @Expose
        private String unitCode;

        public String getDisplayLimitUnit() {
            return displayLimitUnit;
        }

        public void setDisplayLimitUnit(String displayLimitUnit) {
            this.displayLimitUnit = displayLimitUnit;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }


    }
    public class PromotionDto implements Serializable{

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


    }
    public class ProviderDto implements Serializable{
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
    public class ProviderDto_ implements Serializable{

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
    public class RootService implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("listedPrice")
        @Expose
        private String listedPrice;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("listedPriceNumber")
        @Expose
        private Integer listedPriceNumber;
        @SerializedName("priceNumber")
        @Expose
        private Integer priceNumber;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("insertTime")
        @Expose
        private String insertTime;
        @SerializedName("expiryDate")
        @Expose
        private String expiryDate;
        @SerializedName("providerDto")
        @Expose
        private ProviderDto_ providerDto;
        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;
        @SerializedName("limitDetailDescription")
        @Expose
        private String limitDetailDescription;
        @SerializedName("rootService")
        @Expose
        private String rootService;

        @SerializedName("usageDesciption")
        @Expose
        private String usageDesciption;

        @SerializedName("defServiceUnitDtos")
        @Expose
        private List<DefServiceUnitDto_> defServiceUnitDtos = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getListedPrice() {
            return listedPrice;
        }

        public void setListedPrice(String listedPrice) {
            this.listedPrice = listedPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getListedPriceNumber() {
            return listedPriceNumber;
        }

        public void setListedPriceNumber(Integer listedPriceNumber) {
            this.listedPriceNumber = listedPriceNumber;
        }

        public Integer getPriceNumber() {
            return priceNumber;
        }

        public void setPriceNumber(Integer priceNumber) {
            this.priceNumber = priceNumber;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public ProviderDto_ getProviderDto() {
            return providerDto;
        }

        public void setProviderDto(ProviderDto_ providerDto) {
            this.providerDto = providerDto;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getLimitDetailDescription() {
            return limitDetailDescription;
        }

        public void setLimitDetailDescription(String limitDetailDescription) {
            this.limitDetailDescription = limitDetailDescription;
        }

        public String getRootService() {
            return rootService;
        }

        public void setRootService(String rootService) {
            this.rootService = rootService;
        }

        public List<DefServiceUnitDto_> getDefServiceUnitDtos() {
            return defServiceUnitDtos;
        }

        public void setDefServiceUnitDtos(List<DefServiceUnitDto_> defServiceUnitDtos) {
            this.defServiceUnitDtos = defServiceUnitDtos;
        }

        public String getUsageDesciption() {
            return usageDesciption;
        }

        public void setUsageDesciption(String usageDesciption) {
            this.usageDesciption = usageDesciption;
        }
    }
}
