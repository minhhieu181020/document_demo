package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentMethodResponse {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("paymentAccountDto")
        @Expose
        private PaymentAccountDto paymentAccountDto;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public PaymentAccountDto getPaymentAccountDto() {
            return paymentAccountDto;
        }

        public void setPaymentAccountDto(PaymentAccountDto paymentAccountDto) {
            this.paymentAccountDto = paymentAccountDto;
        }
        @Override
        public String toString() {
            return name;
        }
    }


    public class PaymentAccountDto {

        @SerializedName("bankName")
        @Expose
        private String bankName;
        @SerializedName("bankBranch")
        @Expose
        private String bankBranch;
        @SerializedName("accountHolder")
        @Expose
        private String accountHolder;
        @SerializedName("creditAccount")
        @Expose
        private String creditAccount;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankBranch() {
            return bankBranch;
        }

        public void setBankBranch(String bankBranch) {
            this.bankBranch = bankBranch;
        }

        public String getAccountHolder() {
            return accountHolder;
        }

        public void setAccountHolder(String accountHolder) {
            this.accountHolder = accountHolder;
        }

        public String getCreditAccount() {
            return creditAccount;
        }

        public void setCreditAccount(String creditAccount) {
            this.creditAccount = creditAccount;
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
}