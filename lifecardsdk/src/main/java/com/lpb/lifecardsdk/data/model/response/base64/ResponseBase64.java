package com.lpb.lifecardsdk.data.model.response.base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lpb.lifecardsdk.data.model.restheader.ResponseHeader;

public class ResponseBase64 {
    @SerializedName("restHeader")
    @Expose
    private ResponseHeader restHeader;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("secure")
    @Expose
    private Boolean secure;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public ResponseHeader getRestHeader() {
        return restHeader;
    }

    public void setRestHeader(ResponseHeader restHeader) {
        this.restHeader = restHeader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
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

    @Override
    public String toString() {
        return "ResponseBase64{" +
                "restHeader=" + restHeader +
                ", body='" + body + '\'' +
                ", secure=" + secure +
                ", cost=" + cost +
                ", resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}
