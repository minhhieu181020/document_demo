package com.lpb.lifecardsdk.data.model;

import java.util.List;

public class BranchItem {
    private String branchName,phoneNumber,address;
    private boolean status;
    private List<TimeItem> timeItems;

    public BranchItem(String branchName, String phoneNumber, String address, boolean status, List<TimeItem> timeItems) {
        this.branchName = branchName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.timeItems = timeItems;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<TimeItem> getTimeItems() {
        return timeItems;
    }

    public void setTimeItems(List<TimeItem> timeItems) {
        this.timeItems = timeItems;
    }
}
