package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListCardResponseDefault implements Serializable {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("ownCardDtos")
    @Expose
    private List<OwnCardDto> ownCardDtos = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;

    @SerializedName("guideFaceRegistration")
    @Expose
    private String guideFaceRegistration;

    @SerializedName("faceRegistrationStatus")
    @Expose
    private String faceRegistrationStatus;


    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public List<OwnCardDto> getOwnCardDtos() {
        return ownCardDtos;
    }

    public void setOwnCardDtos(List<OwnCardDto> ownCardDtos) {
        this.ownCardDtos = ownCardDtos;
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

    public String getGuideFaceRegistration() {
        return guideFaceRegistration;
    }

    public String getFaceRegistrationStatus() {
        return faceRegistrationStatus;
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
        private RedisMeta_ redisMeta;

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

        public RedisMeta_ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_ redisMeta) {
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
        @SerializedName("cardShare")
        @Expose
        private String cardShare;
        @SerializedName("numberOfShareCardUsers")
        @Expose
        private Integer numberOfShareCardUsers;
        @SerializedName("id")
        @Expose
        private Long id;
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
        @SerializedName("customerName")
        @Expose
        private String customerName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("styleDto")
        @Expose
        private StyleDto styleDto;
        @SerializedName("providerDtos")
        @Expose
        private List<ProviderDto> providerDtos = null;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<OwnServiceDto> ownServiceDtos = null;
        @SerializedName("rootService")
        @Expose
        private RootService rootService;
        @SerializedName("choose")
        @Expose
        private Boolean choose;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_____ redisMeta;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("paymentExpirationDate")
        @Expose
        private String paymentExpirationDate;
        @SerializedName("transactionCode")
        @Expose
        private String transactionCode;
        public Long getId() {
            return id;
        }

        public String getTransactionCode() {
            return transactionCode;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getCardShare() {
            return cardShare;
        }

        public Integer getNumberOfShareCardUsers() {
            return numberOfShareCardUsers;
        }

        public void setNumberOfShareCardUsers(Integer numberOfShareCardUsers) {
            this.numberOfShareCardUsers = numberOfShareCardUsers;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCardNoDisplay() {
            return cardNoDisplay;
        }

        public void setCardNoDisplay(String cardNoDisplay) {
            this.cardNoDisplay = cardNoDisplay;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public StyleDto getStyleDto() {
            return styleDto;
        }

        public void setStyleDto(StyleDto styleDto) {
            this.styleDto = styleDto;
        }

        public List<ProviderDto> getProviderDtos() {
            return providerDtos;
        }

        public void setProviderDtos(List<ProviderDto> providerDtos) {
            this.providerDtos = providerDtos;
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

        public Boolean getChoose() {
            return choose;
        }

        public void setChoose(Boolean choose) {
            this.choose = choose;
        }

        public RedisMeta_____ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_____ redisMeta) {
            this.redisMeta = redisMeta;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPaymentExpirationDate() {
            return paymentExpirationDate;
        }

        public void setPaymentExpirationDate(String paymentExpirationDate) {
            this.paymentExpirationDate = paymentExpirationDate;
        }

    }

    public class OwnServiceDto implements Serializable {

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
        private List<OwnAccountDto> ownAccountDtos = null;
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;
        @SerializedName("amountPayment")
        @Expose
        private Long amountPayment;
        @SerializedName("insertedTime")
        @Expose
        private String insertedTime;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta__ redisMeta;

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

        public List<OwnAccountDto> getOwnAccountDtos() {
            return ownAccountDtos;
        }

        public void setOwnAccountDtos(List<OwnAccountDto> ownAccountDtos) {
            this.ownAccountDtos = ownAccountDtos;
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

        public String getInsertedTime() {
            return insertedTime;
        }

        public void setInsertedTime(String insertedTime) {
            this.insertedTime = insertedTime;
        }

        public RedisMeta__ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta__ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class ProviderDto implements Serializable {

        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("image")
        @Expose
        private String image;

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

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

    public class RootService implements Serializable {

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
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;
        @SerializedName("amountPayment")
        @Expose
        private Long amountPayment;
        @SerializedName("insertedTime")
        @Expose
        private String insertedTime;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta____ redisMeta;

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

        public String getInsertedTime() {
            return insertedTime;
        }

        public void setInsertedTime(String insertedTime) {
            this.insertedTime = insertedTime;
        }

        public RedisMeta____ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta____ redisMeta) {
            this.redisMeta = redisMeta;
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

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

        public Style getAppStyle() {
            return appStyle;
        }
    }
}