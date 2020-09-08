package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vannh.lvt on 03/06/2020
 * Update by hieudm.lvt on 03/09/2020
 */
public class PaymentGuideResponse2 {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("paymentGuideCash")
    @Expose
    private String paymentGuideCash;
    @SerializedName("noteTransfer")
    @Expose
    private String noteTransfer;
    @SerializedName("transferInstruction")
    @Expose
    private String transferInstruction;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("providerIds")
    @Expose
    private List<Integer> providerIds = null;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("expirationTime")
    @Expose
    private String expirationTime;
    @SerializedName("paymentGuide")
    @Expose
    private String paymentGuide;
    @SerializedName("paymentGuide2")
    @Expose
    private String paymentGuide2;
    @SerializedName("ownCardDto")
    @Expose
    private OwnCardDto ownCardDto;

    public StyleDto getStyleDto() {
        return styleDto;
    }

    public void setStyleDto(StyleDto styleDto) {
        this.styleDto = styleDto;
    }

    @SerializedName("styleDto")
    @Expose
    private StyleDto styleDto;
    @SerializedName("amountPaymentDisplay")
    @Expose
    private String amountPaymentDisplay;
    @SerializedName("amountPayment")
    @Expose
    private Integer amountPayment;
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

    public String getPaymentGuideCash() {
        return paymentGuideCash;
    }

    public void setPaymentGuideCash(String paymentGuideCash) {
        this.paymentGuideCash = paymentGuideCash;
    }

    public String getNoteTransfer() {
        return noteTransfer;
    }

    public void setNoteTransfer(String noteTransfer) {
        this.noteTransfer = noteTransfer;
    }

    public String getTransferInstruction() {
        return transferInstruction;
    }

    public void setTransferInstruction(String transferInstruction) {
        this.transferInstruction = transferInstruction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getProviderIds() {
        return providerIds;
    }

    public void setProviderIds(List<Integer> providerIds) {
        this.providerIds = providerIds;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getPaymentGuide() {
        return paymentGuide;
    }

    public void setPaymentGuide(String paymentGuide) {
        this.paymentGuide = paymentGuide;
    }

    public String getPaymentGuide2() {
        return paymentGuide2;
    }

    public void setPaymentGuide2(String paymentGuide2) {
        this.paymentGuide2 = paymentGuide2;
    }

    public OwnCardDto getOwnCardDto() {
        return ownCardDto;
    }

    public void setOwnCardDto(OwnCardDto ownCardDto) {
        this.ownCardDto = ownCardDto;
    }

    public String getAmountPaymentDisplay() {
        return amountPaymentDisplay;
    }

    public void setAmountPaymentDisplay(String amountPaymentDisplay) {
        this.amountPaymentDisplay = amountPaymentDisplay;
    }

    public Integer getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(Integer amountPayment) {
        this.amountPayment = amountPayment;
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
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("cardNoDisplay")
        @Expose
        private String cardNoDisplay;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("ownServiceDtos")
        @Expose
        private List<Object> ownServiceDtos = null;
        @SerializedName("choose")
        @Expose
        private Boolean choose;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
