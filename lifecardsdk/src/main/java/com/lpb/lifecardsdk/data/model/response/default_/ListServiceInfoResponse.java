package com.lpb.lifecardsdk.data.model.response.default_;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListServiceInfoResponse implements Serializable {

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

    public class OwnAccountDto {

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


    public static class OwnCardDto implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
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
        @SerializedName("ownServiceDtos")
        @Expose
        private List<OwnServiceDto> ownServiceDtos = null;
        @SerializedName("choose")
        @Expose
        private Boolean choose;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta___ redisMeta;

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

        public List<OwnServiceDto> getOwnServiceDtos() {
            return ownServiceDtos;
        }

        public void setOwnServiceDtos(List<OwnServiceDto> ownServiceDtos) {
            this.ownServiceDtos = ownServiceDtos;
        }

        public Boolean getChoose() {
            return choose;
        }

        public void setChoose(Boolean choose) {
            this.choose = choose;
        }

        public RedisMeta___ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta___ redisMeta) {
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
        @SerializedName("ownAccountDtos")
        @Expose
        private List<OwnAccountDto> ownAccountDtos = null;
        @SerializedName("providerDto")
        @Expose
        private ProviderDto providerDto;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta__ redisMeta;
        @SerializedName("usageDescription")
        @Expose
        private String usageDescription;

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

        public String getCardNoDisplay() {
            return cardNoDisplay;
        }

        public void setCardNoDisplay(String cardNoDisplay) {
            this.cardNoDisplay = cardNoDisplay;
        }

        public RedisMeta__ getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta__ redisMeta) {
            this.redisMeta = redisMeta;
        }

        public String getUsageDescription() {
            return usageDescription;
        }

        public void setUsageDescription(String usageDescription) {
            this.usageDescription = usageDescription;
        }
    }

    public class ProviderDto implements Serializable {

        @SerializedName("redis_meta")
        @Expose
        private RedisMeta_ redisMeta;
        @SerializedName("id")
        @Expose
        private Long id;
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


    public class StyleDto {

        @SerializedName("rgb")
        @Expose
        private String rgb;

        public String getRgb() {
            return rgb;
        }

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

    }

    @Override
    public String toString() {
        return "ListCardNoAppResponse{" +
                "cost=" + cost +
                ", ownCardDtos=" + ownCardDto +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }

    public ListServiceInfoResponse() {

    }

}