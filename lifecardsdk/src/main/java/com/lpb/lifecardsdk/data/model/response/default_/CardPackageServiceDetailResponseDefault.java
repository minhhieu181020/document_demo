package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CardPackageServiceDetailResponseDefault {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("ownServiceDto")
    @Expose
    private OwnServiceDto ownServiceDto;
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

    public OwnServiceDto getOwnServiceDto() {
        return ownServiceDto;
    }

    public void setOwnServiceDto(OwnServiceDto ownServiceDto) {
        this.ownServiceDto = ownServiceDto;
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

    public class OwnAccountDto {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("availableAmount")
        @Expose
        private Long availableAmount;
        @SerializedName("transferable")
        @Expose
        private String transferable;
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

        public String getTransferable() {
            return transferable;
        }

        public void setTransferable(String transferable) {
            this.transferable = transferable;
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

    public class OwnServiceDto {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("priceNumber")
        @Expose
        private Long priceNumber;


        @SerializedName("listedPriceNumber")
        @Expose
        private Long listedPriceNumber;

        @SerializedName("listedPrice")
        @Expose
        private String listedPrice;

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("namDefService")
        @Expose
        private String namDefService;
        @SerializedName("usageDescription")
        @Expose
        private String usageDescription;
        @SerializedName("description")
        @Expose
        private String description;
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
        @SerializedName("limitDetailDescription")
        @Expose
        private String limitDetailDescription;
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;
        @SerializedName("amountPayment")
        @Expose
        private Long amountPayment;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_ redisMeta;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public Long getPriceNumber() {
            return priceNumber;
        }

        public Long getListedPriceNumber() {
            return listedPriceNumber;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getLimitDetailDescription() {
            return limitDetailDescription;
        }

        public void setLimitDetailDescription(String limitDetailDescription) {
            this.limitDetailDescription = limitDetailDescription;
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

        public RedisMeta_ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_ redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class RedisMeta {

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

    public class RedisMeta_ {

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
}