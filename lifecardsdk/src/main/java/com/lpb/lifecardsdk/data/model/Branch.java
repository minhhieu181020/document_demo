package com.lpb.lifecardsdk.data.model;

public class Branch {
    private String branchName,address,time,phoneNumber;
    private boolean status;

    public Branch(String branchName, String address, String time, String phoneNumber, boolean status) {
        this.branchName = branchName;
        this.address = address;
        this.time = time;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Branch(String branchName, String address, String phoneNumber) {
        this.branchName = branchName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
