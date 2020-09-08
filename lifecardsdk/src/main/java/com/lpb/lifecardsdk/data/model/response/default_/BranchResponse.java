package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lpb.lifecardsdk.data.model.restheader.Location;

public class BranchResponse {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;
    @SerializedName("totalPage")
    @Expose
    private int totalPage;

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

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public class List {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("areaId")
        @Expose
        private Integer areaId;
        @SerializedName("providerId")
        @Expose
        private Integer providerId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("website")
        @Expose
        private String website;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("fullAddress")
        @Expose
        private String fullAddress;
        @SerializedName("businessHourDisplay")
        @Expose
        private String businessHourDisplay;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("distanceFormat")
        @Expose
        private String distanceFormat;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("openStatus")
        @Expose
        private String openStatus;
        @SerializedName("parentCode")
        @Expose
        private String parentCode;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public Integer getProviderId() {
            return providerId;
        }

        public void setProviderId(Integer providerId) {
            this.providerId = providerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getBusinessHourDisplay() {
            return businessHourDisplay;
        }

        public void setBusinessHourDisplay(String businessHourDisplay) {
            this.businessHourDisplay = businessHourDisplay;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = openStatus;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getDistanceFormat() {
            return distanceFormat;
        }
    }
}
