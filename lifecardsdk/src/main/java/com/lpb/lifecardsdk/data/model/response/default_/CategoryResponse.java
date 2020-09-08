package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("refId")
    @Expose
    private String refId;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("lstCate")
    @Expose
    private List<LstCate> lstCate = null;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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

    public List<LstCate> getLstCate() {
        return lstCate;
    }

    public void setLstCate(List<LstCate> lstCate) {
        this.lstCate = lstCate;
    }

    public class LstCate {

        @SerializedName("redis_meta")
        @Expose
        private RedisMeta redisMeta;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("objectType")
        @Expose
        private String objectType;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("orderNo")
        @Expose
        private Integer orderNo;

        public RedisMeta getRedisMeta() {
            return redisMeta;
        }

        public void setRedisMeta(RedisMeta redisMeta) {
            this.redisMeta = redisMeta;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObjectType() {
            return objectType;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(Integer orderNo) {
            this.orderNo = orderNo;
        }

    }
    public class RedisMeta {

        @SerializedName("timeMark")
        @Expose
        private Integer timeMark;

        public Integer getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(Integer timeMark) {
            this.timeMark = timeMark;
        }

    }
}
