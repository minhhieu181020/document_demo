package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMyCardCodeRequestDefault {

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("pageIndex")
    @Expose
    private Long pageIndex;
    @SerializedName("pageSize")
    @Expose
    private Long pageSize;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public ListMyCardCodeRequestDefault(String mobilePhone, Long pageIndex, Long pageSize) {
        this.mobilePhone = mobilePhone;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}