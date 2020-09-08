package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyCardDetailsWaitResponseDefault implements Serializable {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("ownCardDto")
    @Expose
    private OwnCardDto ownCardDto;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public OwnCardDto getOwnCardDto() {
        return ownCardDto;
    }

    public void setOwnCardDto(OwnCardDto ownCardDto) {
        this.ownCardDto = ownCardDto;
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

    public class OwnAccountDto implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("availableAmount")
        @Expose
        private Long availableAmount;
        @SerializedName("unitCode")
        @Expose
        private String unitCode;
        @SerializedName("allocatedUnit")
        @Expose
        private String allocatedUnit;
        @SerializedName("availableUnit")
        @Expose
        private String availableUnit;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getAvailableAmount() {
            return availableAmount;
        }

        public void setAvailableAmount(Long availableAmount) {
            this.availableAmount = availableAmount;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        public String getAllocatedUnit() {
            return allocatedUnit;
        }

        public void setAllocatedUnit(String allocatedUnit) {
            this.allocatedUnit = allocatedUnit;
        }

        public String getAvailableUnit() {
            return availableUnit;
        }

        public void setAvailableUnit(String availableUnit) {
            this.availableUnit = availableUnit;
        }

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class OwnAccountDto_ implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("availableAmount")
        @Expose
        private Long availableAmount;
        @SerializedName("unitCode")
        @Expose
        private String unitCode;
        @SerializedName("allocatedUnit")
        @Expose
        private String allocatedUnit;
        @SerializedName("availableUnit")
        @Expose
        private String availableUnit;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta___ redisMeta;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getAvailableAmount() {
            return availableAmount;
        }

        public void setAvailableAmount(Long availableAmount) {
            this.availableAmount = availableAmount;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        public String getAllocatedUnit() {
            return allocatedUnit;
        }

        public void setAllocatedUnit(String allocatedUnit) {
            this.allocatedUnit = allocatedUnit;
        }

        public String getAvailableUnit() {
            return availableUnit;
        }

        public void setAvailableUnit(String availableUnit) {
            this.availableUnit = availableUnit;
        }

        public RedisMeta___ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta___ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class OwnCardDto implements Serializable {
        @SerializedName("descBrief")
        @Expose
        private String descBrief;
        @SerializedName("giftCard")
        @Expose
        private String giftCard;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("listedPrice")
        @Expose
        private String listedPrice;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("paymentTime")
        @Expose
        private String paymentTime;

        @SerializedName("cardShare")
        @Expose
        private String cardShare;


        @SerializedName("numberOfShareCardUsers")
        @Expose
        private Integer numberOfShareCardUsers;


        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("transactionCode")
        @Expose
        private String transactionCode;
        @SerializedName("defCardCode")
        @Expose
        private String defCardCode;
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("usageTerms")
        @Expose
        private String usageTerms;
        @SerializedName("templatePromotion")
        @Expose
        private String templatePromotion;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<OwnServiceDto> ownServiceDtos = null;

        @SerializedName("freeServices")
        @Expose
        private List<OwnServiceDto> freeServices = null;


        @SerializedName("rootService")
        @Expose
        private RootService rootService;
        @SerializedName("promotionDtos")
        @Expose
        private List<PromotionDto> promotionDtos = null;
        @SerializedName("choose")
        @Expose
        private Boolean choose;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_______ redisMeta;
        @SerializedName("paymentExpirationDate")
        @Expose
        private String paymentExpirationDate;

        public String getPaymentExpirationDate() {
            return paymentExpirationDate;
        }

        public void setPaymentExpirationDate(String paymentExpirationDate) {
            this.paymentExpirationDate = paymentExpirationDate;
        }

        public String getGiftCard() {
            return giftCard;
        }

        public String getCardShare() {
            return cardShare;
        }

        public Integer getNumberOfShareCardUsers() {
            return numberOfShareCardUsers;
        }

        public String getCardNoDisplay() {
            return cardNoDisplay;
        }

        public void setCardNoDisplay(String cardNoDisplay) {
            this.cardNoDisplay = cardNoDisplay;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public List<OwnServiceDto> getFreeServices() {
            return freeServices;
        }

        public String getDescBrief() {
            return descBrief;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getTransactionCode() {
            return transactionCode;
        }

        public void setTransactionCode(String transactionCode) {
            this.transactionCode = transactionCode;
        }

        public String getDefCardCode() {
            return defCardCode;
        }

        public void setDefCardCode(String defCardCode) {
            this.defCardCode = defCardCode;
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

        public String getTemplatePromotion() {
            return templatePromotion;
        }

        public void setTemplatePromotion(String templatePromotion) {
            this.templatePromotion = templatePromotion;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public List<OwnServiceDto> getOwnServiceDtos() {
            return ownServiceDtos;
        }

        public void setOwnServiceDtos(List<OwnServiceDto> ownServiceDtos) {
            this.ownServiceDtos = ownServiceDtos;
        }

        public RootService getRootService() {
            return rootService;
        }

        public void setRootService(RootService rootService) {
            this.rootService = rootService;
        }

        public List<PromotionDto> getPromotionDtos() {
            return promotionDtos;
        }

        public void setPromotionDtos(List<PromotionDto> promotionDtos) {
            this.promotionDtos = promotionDtos;
        }

        public Boolean getChoose() {
            return choose;
        }

        public void setChoose(Boolean choose) {
            this.choose = choose;
        }

        public RedisMeta_______ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_______ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class OwnServiceDto implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("code")
        @Expose
        private String code;
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


        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("namDefService")
        @Expose
        private String namDefService;
        @SerializedName("usageDescription")
        @Expose
        private String usageDescription;
        @SerializedName("rootService")
        @Expose
        private String rootService;
        @SerializedName("expiryDate")
        @Expose
        private String expiryDate;
        @SerializedName("expirationDate")
        @Expose
        private String expirationDate;
        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;
        @SerializedName("ownAccountDtos")
        @Expose
        private List<OwnAccountDto> ownAccountDtos = null;
        @SerializedName("providerDto")
        @Expose
        private ProviderDto providerDto;
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;
        @SerializedName("amountPayment")
        @Expose
        private Long amountPayment;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta__ redisMeta;

        public Long getId() {
            return id;
        }

        public Long getPriceNumber() {
            return priceNumber;
        }

        public Long getListedPriceNumber() {
            return listedPriceNumber;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNamDefService() {
            return namDefService;
        }

        public void setNamDefService(String namDefService) {
            this.namDefService = namDefService;
        }

        public String getUsageDescription() {
            return usageDescription;
        }

        public void setUsageDescription(String usageDescription) {
            this.usageDescription = usageDescription;
        }

        public String getRootService() {
            return rootService;
        }

        public void setRootService(String rootService) {
            this.rootService = rootService;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public List<OwnAccountDto> getOwnAccountDtos() {
            return ownAccountDtos;
        }

        public void setOwnAccountDtos(List<OwnAccountDto> ownAccountDtos) {
            this.ownAccountDtos = ownAccountDtos;
        }

        public ProviderDto getProviderDto() {
            return providerDto;
        }

        public void setProviderDto(ProviderDto providerDto) {
            this.providerDto = providerDto;
        }

        public Long getAdditionalAmount() {
            return additionalAmount;
        }

        public void setAdditionalAmount(Long additionalAmount) {
            this.additionalAmount = additionalAmount;
        }

        public Long getAmountPayment() {
            return amountPayment;
        }

        public void setAmountPayment(Long amountPayment) {
            this.amountPayment = amountPayment;
        }

        public RedisMeta__ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta__ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class PromotionDto implements Serializable {

        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("urlIcon")
        @Expose
        private String urlIcon;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta______ redisMeta;

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

        public RedisMeta______ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta______ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class ProviderDto implements Serializable {

        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_ redisMeta;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("image")
        @Expose
        private String image;

        public RedisMeta_ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_ redisMeta) {
            this.redisMeta = redisMeta;
        }

        public Integer getId() {
            return id;
        }

        public void setId(int id) {
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

    public class ProviderDto_ implements Serializable {

        @SerializedName("redis_meta")
        @Expose
        private RedisMeta____ redisMeta;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("image")
        @Expose
        private String image;

        public RedisMeta____ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta____ redisMeta) {
            this.redisMeta = redisMeta;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
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

    public class RedisMeta implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta_ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta__ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta___ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta____ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta_____ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta______ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RedisMeta_______ implements Serializable {

        @SerializedName("timeMark")
        @Expose
        private Long timeMark;

        public Long getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Long timeMark) {
            this.timeMark = timeMark;
        }

    }

    public class RootService implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("listedPrice")
        @Expose
        private String listedPrice;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("namDefService")
        @Expose
        private String namDefService;
        @SerializedName("rootService")
        @Expose
        private String rootService;
        @SerializedName("expiryDate")
        @Expose
        private String expiryDate;
        @SerializedName("expirationDate")
        @Expose
        private String expirationDate;
        @SerializedName("unitPrice")
        @Expose
        private String unitPrice;
        @SerializedName("ownAccountDtos")
        @Expose
        private List<OwnAccountDto_> ownAccountDtos = null;
        @SerializedName("providerDto")
        @Expose
        private ProviderDto_ providerDto;
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;
        @SerializedName("amountPayment")
        @Expose
        private Long amountPayment;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_____ redisMeta;
        @SerializedName("usageDescription")
        @Expose
        private String usageDescription;

        public String getUsageDescription() {
            return usageDescription;
        }

        public void setUsageDescription(String usageDescription) {
            this.usageDescription = usageDescription;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNamDefService() {
            return namDefService;
        }

        public void setNamDefService(String namDefService) {
            this.namDefService = namDefService;
        }

        public String getRootService() {
            return rootService;
        }

        public void setRootService(String rootService) {
            this.rootService = rootService;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public List<OwnAccountDto_> getOwnAccountDtos() {
            return ownAccountDtos;
        }

        public void setOwnAccountDtos(List<OwnAccountDto_> ownAccountDtos) {
            this.ownAccountDtos = ownAccountDtos;
        }

        public ProviderDto_ getProviderDto() {
            return providerDto;
        }

        public void setProviderDto(ProviderDto_ providerDto) {
            this.providerDto = providerDto;
        }

        public Long getAdditionalAmount() {
            return additionalAmount;
        }

        public void setAdditionalAmount(Long additionalAmount) {
            this.additionalAmount = additionalAmount;
        }

        public Long getAmountPayment() {
            return amountPayment;
        }

        public void setAmountPayment(Long amountPayment) {
            this.amountPayment = amountPayment;
        }

        public RedisMeta_____ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_____ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }
}