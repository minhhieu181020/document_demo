package com.lpb.lifecardsdk.data.model.response.default_;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardPhysicalResponseDefault {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("ownCardDto")
    @Expose
    private OwnCardDto ownCardDto;
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


    public class OwnCardDto {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("paymentValue")
        @Expose
        private Integer paymentValue;
        @SerializedName("paymentTime")
        @Expose
        private String paymentTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<Object> ownServiceDtos = null;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;

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

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Integer getPaymentValue() {
            return paymentValue;
        }

        public void setPaymentValue(Integer paymentValue) {
            this.paymentValue = paymentValue;
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

        public List<Object> getOwnServiceDtos() {
            return ownServiceDtos;
        }

        public void setOwnServiceDtos(List<Object> ownServiceDtos) {
            this.ownServiceDtos = ownServiceDtos;
        }

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

    }

    public class RedisMeta {

        @SerializedName("timeMark")
        @Expose
        private Integer timeMark;

        public Integer getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Integer timeMark) {
            this.timeMark = timeMark;
        }

    }
}