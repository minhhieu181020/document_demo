package com.lpb.lifecardsdk.data.model;

/**
 * Created by vannh.lvt on 02/06/2020
 */
public class BankItem {
    private String tentk,nganhang,chinhanh,sotk;

    public BankItem(String tentk, String nganhang, String chinhanh, String sotk) {
        this.tentk = tentk;
        this.nganhang = nganhang;
        this.chinhanh = chinhanh;
        this.sotk = sotk;
    }

    public String getTentk() {
        return tentk;
    }

    public String getNganhang() {
        return nganhang;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public String getSotk() {
        return sotk;
    }
}
