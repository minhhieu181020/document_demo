package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionHistoryRequestDefault {

    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("ownServiceCode")
    @Expose
    private String ownServiceCode;
    @SerializedName("searchType")
    @Expose
    private String searchType;
    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("searchEndTime")
    @Expose
    private String searchEndTime;
    @SerializedName("searchStartTime")
    @Expose
    private String searchStartTime;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOwnServiceCode() {
        return ownServiceCode;
    }

    public void setOwnServiceCode(String ownServiceCode) {
        this.ownServiceCode = ownServiceCode;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
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

    public String getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(String searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public String getSearchStartTime() {
        return searchStartTime;
    }

    public void setSearchStartTime(String searchStartTime) {
        this.searchStartTime = searchStartTime;
    }

    public TransactionHistoryRequestDefault(String mobilePhone, String ownServiceCode, String searchType, Integer pageIndex, Integer pageSize, String searchStartTime, String searchEndTime) {
        this.mobilePhone = mobilePhone;
        this.ownServiceCode = ownServiceCode;
        this.searchType = searchType;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.searchStartTime = searchStartTime;
        this.searchEndTime = searchEndTime;
    }
}
