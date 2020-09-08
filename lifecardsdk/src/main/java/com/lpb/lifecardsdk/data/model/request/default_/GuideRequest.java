package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 19/06/2020
 */
public class GuideRequest {
    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("defCardCode")
    @Expose
    private String defCardCode;

    public void setDefCardCode(String defCardCode) {
        this.defCardCode = defCardCode;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
