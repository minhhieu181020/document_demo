package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lpb.lifecardsdk.data.model.response.default_.ListServicePaymentQrBillResponse;

import java.io.Serializable;
import java.util.List;


public class ServicePaymentQrBillRequest implements Serializable {
    @Override
    public String toString() {
        return "ServicePaymentQrBillRequest{" +
                "billCode='" + billCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", staffNo='" + staffNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", serviceDetailDatas=" + ServiceDetailData +
                '}';
    }

    @SerializedName("billCode")
    @Expose
    private String billCode;
    @SerializedName("branchCode")
    @Expose
    private String branchCode;
    @SerializedName("staffNo")
    @Expose
    private String staffNo;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerPhone")
    @Expose
    private String customerPhone;
    @SerializedName("serviceDetailDatas")
    @Expose
    private List<ListServicePaymentQrBillResponse.ServiceDetailData> ServiceDetailData = null;


    public ServicePaymentQrBillRequest(String billCode, String branchCode, String staffNo, String customerName, String customerPhone, List<ListServicePaymentQrBillResponse.ServiceDetailData> ServiceDetailData) {
        this.billCode = billCode;
        this.branchCode = branchCode;
        this.staffNo = staffNo;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.ServiceDetailData = ServiceDetailData;
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
        private Integer inputAmount;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("transAmountDisplay")
        @Expose
        private String transAmountDisplay;
        @SerializedName("transAmount")
        @Expose
        private Integer transAmount;
        @SerializedName("additionalAmountDisplay")
        @Expose
        private String additionalAmountDisplay;
        @SerializedName("additionalAmount")
        @Expose
        private Integer additionalAmount;

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

        public Integer getInputAmount() {
            return inputAmount;
        }

        public void setInputAmount(Integer inputAmount) {
            this.inputAmount = inputAmount;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getTransAmountDisplay() {
            return transAmountDisplay;
        }

        public void setTransAmountDisplay(String transAmountDisplay) {
            this.transAmountDisplay = transAmountDisplay;
        }

        public Integer getTransAmount() {
            return transAmount;
        }

        public void setTransAmount(Integer transAmount) {
            this.transAmount = transAmount;
        }

        public String getAdditionalAmountDisplay() {
            return additionalAmountDisplay;
        }

        public void setAdditionalAmountDisplay(String additionalAmountDisplay) {
            this.additionalAmountDisplay = additionalAmountDisplay;
        }

        public Integer getAdditionalAmount() {
            return additionalAmount;
        }

        public void setAdditionalAmount(Integer additionalAmount) {
            this.additionalAmount = additionalAmount;
        }

        @Override
        public String toString() {
            return "ServiceDetailData{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", cardNoDisplay='" + cardNoDisplay + '\'' +
                    ", cardNo='" + cardNo + '\'' +
                    ", unitType='" + unitType + '\'' +
                    ", inputAmountDisplay='" + inputAmountDisplay + '\'' +
                    ", inputAmount=" + inputAmount +
                    ", amount=" + amount +
                    ", transAmountDisplay='" + transAmountDisplay + '\'' +
                    ", transAmount=" + transAmount +
                    ", additionalAmountDisplay='" + additionalAmountDisplay + '\'' +
                    ", additionalAmount=" + additionalAmount +
                    '}';
        }
    }


}