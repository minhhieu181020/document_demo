package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TransactionHistoryResponseDefault implements Serializable {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("ownServiceCode")
    @Expose
    private String ownServiceCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("usageDescription")
    @Expose
    private String usageDescription;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("unitType")
    @Expose
    private String unitType;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("ownAccountDtos")
    @Expose
    private List<OwnAccountDto> ownAccountDtos = null;
    @SerializedName("accountEntryDtos")
    @Expose
    private List<AccountEntryDto> accountEntryDtos = null;
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

    public String getOwnServiceCode() {
        return ownServiceCode;
    }

    public void setOwnServiceCode(String ownServiceCode) {
        this.ownServiceCode = ownServiceCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public void setUsageDescription(String usageDescription) {
        this.usageDescription = usageDescription;
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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<OwnAccountDto> getOwnAccountDtos() {
        return ownAccountDtos;
    }

    public void setOwnAccountDtos(List<OwnAccountDto> ownAccountDtos) {
        this.ownAccountDtos = ownAccountDtos;
    }

    public List<AccountEntryDto> getAccountEntryDtos() {
        return accountEntryDtos;
    }

    public void setAccountEntryDtos(List<AccountEntryDto> accountEntryDtos) {
        this.accountEntryDtos = accountEntryDtos;
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

    public class AccountEntryDto implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("transactionId")
        @Expose
        private Long transactionId;
        @SerializedName("amountUnit")
        @Expose
        private String amountUnit;
        @SerializedName("availableAfterUnit")
        @Expose
        private String availableAfterUnit;
        @SerializedName("providerAddress")
        @Expose
        private String providerAddress;
        @SerializedName("providerName")
        @Expose
        private String providerName;

        @SerializedName("customerName")
        @Expose
        private String customerName;


        @SerializedName("mobilePhone")
        @Expose
        private String mobilePhone;

        @SerializedName("entryTime")
        @Expose
        private String entryTime;
        @SerializedName("entryTimeDetail")
        @Expose
        private String entryTimeDetail;
        @SerializedName("transType")
        @Expose
        private String transType;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_ redisMeta;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
        }

        public String getAmountUnit() {
            return amountUnit;
        }

        public void setAmountUnit(String amountUnit) {
            this.amountUnit = amountUnit;
        }

        public String getAvailableAfterUnit() {
            return availableAfterUnit;
        }

        public void setAvailableAfterUnit(String availableAfterUnit) {
            this.availableAfterUnit = availableAfterUnit;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public String getProviderAddress() {
            return providerAddress;
        }

        public void setProviderAddress(String providerAddress) {
            this.providerAddress = providerAddress;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getEntryTimeDetail() {
            return entryTimeDetail;
        }

        public void setEntryTimeDetail(String entryTimeDetail) {
            this.entryTimeDetail = entryTimeDetail;
        }

        public String getTransType() {
            return transType;
        }

        public void setTransType(String transType) {
            this.transType = transType;
        }

        public RedisMeta_ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta_ redisMeta) {
            this.redisMeta = redisMeta;
        }
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

}