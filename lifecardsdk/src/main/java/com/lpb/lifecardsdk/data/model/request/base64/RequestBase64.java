package com.lpb.lifecardsdk.data.model.request.base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lpb.lifecardsdk.data.model.restheader.RequestHeader;

public class RequestBase64 {
    @SerializedName("restHeader")
    @Expose
    private RequestHeader restHeader;
    @SerializedName("body")
    @Expose
    private String body;

    public RequestHeader getRestHeader() {
        return restHeader;
    }

    public void setRestHeader(RequestHeader restHeader) {
        this.restHeader = restHeader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestBase64{" +
                "restHeader=" + restHeader +
                ", body='" + body + '\'' +
                '}';
    }
}

