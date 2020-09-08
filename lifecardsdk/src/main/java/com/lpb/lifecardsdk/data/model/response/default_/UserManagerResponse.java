package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 29/07/2020
 */
public class UserManagerResponse {

    @SerializedName("cost")
    @Expose
    private Long cost;
    @SerializedName("ownerDtos")
    @Expose
    private List<OwnerDto> ownerDtos = null;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;

    public Long getCost() {
        return cost;
    }

    public List<OwnerDto> getOwnerDtos() {
        return ownerDtos;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public class OwnerDto {

        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("insertTime")
        @Expose
        private String insertTime;
        @SerializedName("lastUpdate")
        @Expose
        private String lastUpdate;
        @SerializedName("mobilePhone")
        @Expose
        private String mobilePhone;
        @SerializedName("customerName")
        @Expose
        private String customerName;

        public Long getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public String getCustomerName() {
            return customerName;
        }
    }
}
