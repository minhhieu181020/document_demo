package com.lpb.lifecardsdk.data.model.response.default_;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListMyCardCodeResponseDefault implements Serializable {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("listActiveCardDto")
    @Expose
    private List<ListActiveCardDto> listActiveCardDto = null;
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

    public List<ListActiveCardDto> getListActiveCardDto() {
        return listActiveCardDto;
    }

    public void setListActiveCardDto(List<ListActiveCardDto> listActiveCardDto) {
        this.listActiveCardDto = listActiveCardDto;
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


    public class ListActiveCardDto implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("styleDto")
        @Expose
        private StyleDto styleDto;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<Object> ownServiceDtos = null;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        @SerializedName("choose")
        @Expose
        private Boolean choose;
        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;

        public String getName() {
            return name;
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

        public List<Object> getOwnServiceDtos() {
            return ownServiceDtos;
        }

        public void setOwnServiceDtos(List<Object> ownServiceDtos) {
            this.ownServiceDtos = ownServiceDtos;
        }

        public Boolean getChoose() {
            return choose;
        }

        public void setChoose(Boolean choose) {
            this.choose = choose;
        }

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

        @Override
        public String toString() {
            return "ListActiveCardDto{" +
                    "name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    ", cardNoDisplay='" + cardNoDisplay + '\'' +
                    ", image='" + image + '\'' +
                    ", styleDto=" + styleDto +
                    ", ownServiceDtos=" + ownServiceDtos +
                    ", choose=" + choose +
                    ", redisMeta=" + redisMeta +
                    '}';
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


    public class StyleDto implements Serializable {

        @SerializedName("rgb")
        @Expose
        private String rgb;
        @SerializedName("backFontColor")
        @Expose
        private String backFontColor;
        @SerializedName("cardNoSize")
        @Expose
        private Long cardNoSize;
        @SerializedName("marginBottom")
        @Expose
        private Long marginBottom;
        @SerializedName("showQR")
        @Expose
        private Boolean showQR;
        @SerializedName("qrAlign")
        @Expose
        private String qrAlign;
        @SerializedName("qrMargin")
        @Expose
        private Long qrMargin;
        @SerializedName("showDesc")
        @Expose
        private Boolean showDesc;
        @SerializedName("descMargin")
        @Expose
        private Long descMargin;
        @SerializedName("qrMarginTop")
        @Expose
        private Long qrMarginTop;
        @SerializedName("descMarginTop")
        @Expose
        private Long descMarginTop;
        @SerializedName("cardNoFont")
        @Expose
        private String cardNoFont;
        @SerializedName("cardNoMargin")
        @Expose
        private Long cardNoMargin;
        @SerializedName("cardNoAlign")
        @Expose
        private String cardNoAlign;

        public String getRgb() {
            return rgb;
        }

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

        public String getBackFontColor() {
            return backFontColor;
        }

        public void setBackFontColor(String backFontColor) {
            this.backFontColor = backFontColor;
        }

        public Long getCardNoSize() {
            return cardNoSize;
        }

        public void setCardNoSize(Long cardNoSize) {
            this.cardNoSize = cardNoSize;
        }

        public Long getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(Long marginBottom) {
            this.marginBottom = marginBottom;
        }

        public Boolean getShowQR() {
            return showQR;
        }

        public void setShowQR(Boolean showQR) {
            this.showQR = showQR;
        }

        public String getQrAlign() {
            return qrAlign;
        }

        public void setQrAlign(String qrAlign) {
            this.qrAlign = qrAlign;
        }

        public Long getQrMargin() {
            return qrMargin;
        }

        public void setQrMargin(Long qrMargin) {
            this.qrMargin = qrMargin;
        }

        public Boolean getShowDesc() {
            return showDesc;
        }

        public void setShowDesc(Boolean showDesc) {
            this.showDesc = showDesc;
        }

        public Long getDescMargin() {
            return descMargin;
        }

        public void setDescMargin(Long descMargin) {
            this.descMargin = descMargin;
        }

        public Long getQrMarginTop() {
            return qrMarginTop;
        }

        public void setQrMarginTop(Long qrMarginTop) {
            this.qrMarginTop = qrMarginTop;
        }

        public Long getDescMarginTop() {
            return descMarginTop;
        }

        public void setDescMarginTop(Long descMarginTop) {
            this.descMarginTop = descMarginTop;
        }

        public String getCardNoFont() {
            return cardNoFont;
        }

        public void setCardNoFont(String cardNoFont) {
            this.cardNoFont = cardNoFont;
        }

        public Long getCardNoMargin() {
            return cardNoMargin;
        }

        public void setCardNoMargin(Long cardNoMargin) {
            this.cardNoMargin = cardNoMargin;
        }

        public String getCardNoAlign() {
            return cardNoAlign;
        }

        public void setCardNoAlign(String cardNoAlign) {
            this.cardNoAlign = cardNoAlign;
        }

    }

    @Override
    public String toString() {
        return "ListMyCardCodeResponseDefault{" +
                "cost=" + cost +
                ", listActiveCardDto=" + listActiveCardDto +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}