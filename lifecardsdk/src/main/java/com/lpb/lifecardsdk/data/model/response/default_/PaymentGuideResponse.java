package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentGuideResponse {
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


}
