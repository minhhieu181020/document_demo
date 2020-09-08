package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultViPayRequest {

    @SerializedName("transId")
    @Expose
    private String transId;
    @SerializedName("url")
    @Expose
    private String url;

    public ResultViPayRequest(String transId, String url) {
        this.transId = transId;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
