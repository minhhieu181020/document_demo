package com.lpb.lifecardsdk.data.model;

public class PackageItem {
    private String name,useLimit,expireDate;
    private int avatar;

    public PackageItem(String name, String useLimit, String expireDate, int avatar) {
        this.name = name;
        this.useLimit = useLimit;
        this.expireDate = expireDate;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(String useLimit) {
        this.useLimit = useLimit;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
