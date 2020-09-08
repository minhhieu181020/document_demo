package com.lpb.lifecardsdk.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CardDetailResponseDefault {
    @Override
    public String toString() {
        return "CardDetailResponseDefault{" +
                "cost=" + cost +
                ", branchName='" + branchName + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", staff='" + staff + '\'' +
                ", billCode='" + billCode + '\'' +
                ", transCode='" + transCode + '\'' +
                ", vat=" + vat +
                ", vatDisplay='" + vatDisplay + '\'' +
                ", transTime='" + transTime + '\'' +
                ", customerName='" + customerName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", transAmountDisplay='" + transAmountDisplay + '\'' +
                ", transAmount=" + transAmount +
                ", transLimitTimesDisplay='" + transLimitTimesDisplay + '\'' +
                ", totalAdditionalAmount=" + totalAdditionalAmount +
                ", totalAdditionalAmountDisplay='" + totalAdditionalAmountDisplay + '\'' +
                ", serviceDetailDatas=" + serviceDetailDatas +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("branchAddress")
    @Expose
    private String branchAddress;
    @SerializedName("branchCode")
    @Expose
    private String branchCode;
    @SerializedName("staff")
    @Expose
    private String staff;
    @SerializedName("billCode")
    @Expose
    private String billCode;
    @SerializedName("transCode")
    @Expose
    private String transCode;
    @SerializedName("vat")
    @Expose
    private Long vat;
    @SerializedName("vatDisplay")
    @Expose
    private String vatDisplay;
    @SerializedName("transTime")
    @Expose
    private String transTime;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("transAmountDisplay")
    @Expose
    private String transAmountDisplay;
    @SerializedName("transAmount")
    @Expose
    private Long transAmount;
    @SerializedName("transLimitTimesDisplay")
    @Expose
    private String transLimitTimesDisplay;
    @SerializedName("totalAdditionalAmount")
    @Expose
    private Long totalAdditionalAmount;
    @SerializedName("totalAdditionalAmountDisplay")
    @Expose
    private String totalAdditionalAmountDisplay;
    @SerializedName("serviceDetailDatas")
    @Expose
    private List<ServiceDetailData> serviceDetailDatas = null;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public String getVatDisplay() {
        return vatDisplay;
    }

    public void setVatDisplay(String vatDisplay) {
        this.vatDisplay = vatDisplay;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTransAmountDisplay() {
        return transAmountDisplay;
    }

    public void setTransAmountDisplay(String transAmountDisplay) {
        this.transAmountDisplay = transAmountDisplay;
    }

    public Long getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransLimitTimesDisplay() {
        return transLimitTimesDisplay;
    }

    public void setTransLimitTimesDisplay(String transLimitTimesDisplay) {
        this.transLimitTimesDisplay = transLimitTimesDisplay;
    }

    public Long getTotalAdditionalAmount() {
        return totalAdditionalAmount;
    }

    public void setTotalAdditionalAmount(Long totalAdditionalAmount) {
        this.totalAdditionalAmount = totalAdditionalAmount;
    }

    public String getTotalAdditionalAmountDisplay() {
        return totalAdditionalAmountDisplay;
    }

    public void setTotalAdditionalAmountDisplay(String totalAdditionalAmountDisplay) {
        this.totalAdditionalAmountDisplay = totalAdditionalAmountDisplay;
    }

    public List<ServiceDetailData> getServiceDetailDatas() {
        return serviceDetailDatas;
    }

    public void setServiceDetailDatas(List<ServiceDetailData> serviceDetailDatas) {
        this.serviceDetailDatas = serviceDetailDatas;
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


    public class ServiceDetailData implements Serializable {
        @Override
        public String toString() {
            return "ServiceDetailData{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", cardNoDisplay='" + cardNoDisplay + '\'' +
                    ", cardNo='" + cardNo + '\'' +
                    ", accountNo='" + accountNo + '\'' +
                    ", unitType='" + unitType + '\'' +
                    ", inputAmountDisplay='" + inputAmountDisplay + '\'' +
                    ", inputAmount=" + inputAmount +
                    ", amountDisplay='" + amountDisplay + '\'' +
                    ", amount=" + amount +
                    ", transAmountDisplay='" + transAmountDisplay + '\'' +
                    ", transAmount=" + transAmount +
                    ", additionalAmountDisplay='" + additionalAmountDisplay + '\'' +
                    ", additionalAmount=" + additionalAmount +
                    '}';
        }

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("accountNo")
        @Expose
        private String accountNo;
        @SerializedName("unitType")
        @Expose
        private String unitType;
        @SerializedName("inputAmountDisplay")
        @Expose
        private String inputAmountDisplay;
        @SerializedName("inputAmount")
        @Expose
        private Long inputAmount;
        @SerializedName("amountDisplay")
        @Expose
        private String amountDisplay;
        @SerializedName("amount")
        @Expose
        private Long amount;
        @SerializedName("transAmountDisplay")
        @Expose
        private String transAmountDisplay;
        @SerializedName("transAmount")
        @Expose
        private Long transAmount;
        @SerializedName("additionalAmountDisplay")
        @Expose
        private String additionalAmountDisplay;
        @SerializedName("additionalAmount")
        @Expose
        private Long additionalAmount;

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

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getUnitType() {
            return unitType;
        }

        public void setUnitType(String unitType) {
            this.unitType = unitType;
        }

        public String getInputAmountDisplay() {
            return inputAmountDisplay;
        }

        public void setInputAmountDisplay(String inputAmountDisplay) {
            this.inputAmountDisplay = inputAmountDisplay;
        }

        public Long getInputAmount() {
            return inputAmount;
        }

        public void setInputAmount(Long inputAmount) {
            this.inputAmount = inputAmount;
        }

        public String getAmountDisplay() {
            return amountDisplay;
        }

        public void setAmountDisplay(String amountDisplay) {
            this.amountDisplay = amountDisplay;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getTransAmountDisplay() {
            return transAmountDisplay;
        }

        public void setTransAmountDisplay(String transAmountDisplay) {
            this.transAmountDisplay = transAmountDisplay;
        }

        public Long getTransAmount() {
            return transAmount;
        }

        public void setTransAmount(Long transAmount) {
            this.transAmount = transAmount;
        }

        public String getAdditionalAmountDisplay() {
            return additionalAmountDisplay;
        }

        public void setAdditionalAmountDisplay(String additionalAmountDisplay) {
            this.additionalAmountDisplay = additionalAmountDisplay;
        }

        public Long getAdditionalAmount() {
            return additionalAmount;
        }

        public void setAdditionalAmount(Long additionalAmount) {
            this.additionalAmount = additionalAmount;
        }

    }
}