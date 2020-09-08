package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageSearchRequest {
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("keywordQuery")
    @Expose
    private String keywordQuery;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("providerId")
    @Expose
    private Integer providerId;

    public PackageSearchRequest(Integer categoryId, String keywordQuery, String sort, String mobilePhone, Integer pageIndex, Integer pageSize, Integer providerId) {
        this.categoryId = categoryId;
        this.keywordQuery = keywordQuery;
        this.sort = sort;
        this.mobilePhone = mobilePhone;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.providerId = providerId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeywordQuery() {
        return keywordQuery;
    }

    public void setKeywordQuery(String keywordQuery) {
        this.keywordQuery = keywordQuery;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "PackageSearchRequest{" +
                "categoryId=" + categoryId +
                ", keywordQuery='" + keywordQuery + '\'' +
                ", sort='" + sort + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", providerId=" + providerId +
                '}';
    }
}
