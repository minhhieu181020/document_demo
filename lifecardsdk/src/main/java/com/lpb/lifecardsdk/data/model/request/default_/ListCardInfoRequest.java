package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ListCardInfoRequest {

    @SerializedName("cardNo")
    @Expose
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public ListCardInfoRequest(String cardNo) {
        this.cardNo = cardNo;
    }
}
