package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMyCardRequestDefault {
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("keyFilter")
    @Expose
    private String keyFilter;
    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("isPaid")
    @Expose
    private boolean isPaid;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getKeyFilter() {
        return keyFilter;
    }

    public void setKeyFilter(String keyFilter) {
        this.keyFilter = keyFilter;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public ListMyCardRequestDefault(String mobilePhone, String keyFilter, Integer pageIndex, Integer pageSize, boolean isPaid) {
        this.mobilePhone = mobilePhone;
        this.keyFilter = keyFilter;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.isPaid = isPaid;
    }

    public ListMyCardRequestDefault(String mobilePhone, String keyFilter, Integer pageIndex, Integer pageSize) {
        this.mobilePhone = mobilePhone;
        this.keyFilter = keyFilter;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}