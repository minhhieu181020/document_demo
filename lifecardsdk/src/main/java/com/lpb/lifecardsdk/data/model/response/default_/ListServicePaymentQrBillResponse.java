package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListServicePaymentQrBillResponse implements Serializable {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("serviceDetailDatas")
    @Expose
    private List<ServiceDetailData> serviceDetailDatas = null;
    @SerializedName("branchDTO")
    @Expose
    private BranchDTO branchDTO;
    @SerializedName("staffDto")
    @Expose
    private StaffDto staffDto;
    @SerializedName("billCode")
    @Expose
    private String billCode;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
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

    public List<ServiceDetailData> getServiceDetailDatas() {
        return serviceDetailDatas;
    }

    public void setServiceDetailDatas(List<ServiceDetailData> serviceDetailDatas) {
        this.serviceDetailDatas = serviceDetailDatas;
    }

    public BranchDTO getBranchDTO() {
        return branchDTO;
    }

    public void setBranchDTO(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
    }

    public StaffDto getStaffDto() {
        return staffDto;
    }

    public void setStaffDto(StaffDto staffDto) {
        this.staffDto = staffDto;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
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

    public class BranchDTO implements Serializable {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("fullAddress")
        @Expose
        private String fullAddress;

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

    }

    public class ServiceDetailData implements Serializable {

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


    public class StaffDto implements Serializable {

        @SerializedName("staffId")
        @Expose
        private String staffId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("staffNo")
        @Expose
        private String staffNo;
        @SerializedName("mobilePhone")
        @Expose
        private String mobilePhone;
        @SerializedName("providerId")
        @Expose
        private Long providerId;

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStaffNo() {
            return staffNo;
        }

        public void setStaffNo(String staffNo) {
            this.staffNo = staffNo;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Long getProviderId() {
            return providerId;
        }

        public void setProviderId(Long providerId) {
            this.providerId = providerId;
        }

    }
}