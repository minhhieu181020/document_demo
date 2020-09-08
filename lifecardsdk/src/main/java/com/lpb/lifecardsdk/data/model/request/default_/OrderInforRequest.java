package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 28/05/2020
 */
public class OrderInforRequest {
    @SerializedName("orderNo")
    @Expose
    private String orderNo;

    public OrderInforRequest(String orderNo) {
        this.orderNo = orderNo;
    }
}
