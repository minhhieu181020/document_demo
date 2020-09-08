package com.lpb.lifecardsdk.data.model;

import java.util.List;

public class CardDetails {
//    imgbrandlogo
//            tvNameBrand
//    tvNamePackage
//            tvStatusPackage
//    tvCreditLimitGranted
//            tvCreditLimitRemaining
//    tvCredEXP
//            imgQRcode

    private int imgbrandlogo, imgQRcode;
    private String tvNameBrand, tvNamePackage, tvStatusPackage, tvCreditLimitGranted, tvCreditLimitRemaining, tvCredEXP;
    private List<HistoryDeatilC1R2> historyDeatilsC1R2;
    private List<HistoryDeatilC2R3> historyDeatilsC2R3;
    private List<HistoryDeatilC3R4> historyDeatilsC3R4;
    private int type;

    public int getImgbrandlogo() {
        return imgbrandlogo;
    }

    public void setImgbrandlogo(int imgbrandlogo) {
        this.imgbrandlogo = imgbrandlogo;
    }

    public int getImgQRcode() {
        return imgQRcode;
    }

    public void setImgQRcode(int imgQRcode) {
        this.imgQRcode = imgQRcode;
    }

    public String getTvNameBrand() {
        return tvNameBrand;
    }

    public void setTvNameBrand(String tvNameBrand) {
        this.tvNameBrand = tvNameBrand;
    }

    public String getTvNamePackage() {
        return tvNamePackage;
    }

    public void setTvNamePackage(String tvNamePackage) {
        this.tvNamePackage = tvNamePackage;
    }

    public String getTvStatusPackage() {
        return tvStatusPackage;
    }

    public void setTvStatusPackage(String tvStatusPackage) {
        this.tvStatusPackage = tvStatusPackage;
    }

    public String getTvCreditLimitGranted() {
        return tvCreditLimitGranted;
    }

    public void setTvCreditLimitGranted(String tvCreditLimitGranted) {
        this.tvCreditLimitGranted = tvCreditLimitGranted;
    }

    public String getTvCreditLimitRemaining() {
        return tvCreditLimitRemaining;
    }

    public void setTvCreditLimitRemaining(String tvCreditLimitRemaining) {
        this.tvCreditLimitRemaining = tvCreditLimitRemaining;
    }

    public String getTvCredEXP() {
        return tvCredEXP;
    }

    public void setTvCredEXP(String tvCredEXP) {
        this.tvCredEXP = tvCredEXP;
    }

    public List<HistoryDeatilC1R2> getHistoryDeatilsC1R2() {
        return historyDeatilsC1R2;
    }

    public void setHistoryDeatilsC1R2(List<HistoryDeatilC1R2> historyDeatilsC1R2) {
        this.historyDeatilsC1R2 = historyDeatilsC1R2;
    }

    public List<HistoryDeatilC2R3> getHistoryDeatilsC2R3() {
        return historyDeatilsC2R3;
    }

    public void setHistoryDeatilsC2R3(List<HistoryDeatilC2R3> historyDeatilsC2R3) {
        this.historyDeatilsC2R3 = historyDeatilsC2R3;
    }

    public List<HistoryDeatilC3R4> getHistoryDeatilsC3R4() {
        return historyDeatilsC3R4;
    }

    public void setHistoryDeatilsC3R4(List<HistoryDeatilC3R4> historyDeatilsC3R4) {
        this.historyDeatilsC3R4 = historyDeatilsC3R4;
    }

    public CardDetails(int imgbrandlogo, int imgQRcode, String tvNameBrand, String tvNamePackage, String tvStatusPackage, String tvCreditLimitGranted, String tvCreditLimitRemaining, String tvCredEXP, List<HistoryDeatilC1R2> historyDeatilsC1R2, List<HistoryDeatilC2R3> historyDeatilsC2R3, List<HistoryDeatilC3R4> historyDeatilsC3R4, int type) {
        this.imgbrandlogo = imgbrandlogo;
        this.imgQRcode = imgQRcode;
        this.tvNameBrand = tvNameBrand;
        this.tvNamePackage = tvNamePackage;
        this.tvStatusPackage = tvStatusPackage;
        this.tvCreditLimitGranted = tvCreditLimitGranted;
        this.tvCreditLimitRemaining = tvCreditLimitRemaining;
        this.tvCredEXP = tvCredEXP;
        this.historyDeatilsC1R2 = historyDeatilsC1R2;
        this.historyDeatilsC2R3 = historyDeatilsC2R3;
        this.historyDeatilsC3R4 = historyDeatilsC3R4;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    //    public int getImgbrandlogo() {
//        return imgbrandlogo;
//    }
//
//    public void setImgbrandlogo(int imgbrandlogo) {
//        this.imgbrandlogo = imgbrandlogo;
//    }
//
//    public int getImgQRcode() {
//        return imgQRcode;
//    }
//
//    public void setImgQRcode(int imgQRcode) {
//        this.imgQRcode = imgQRcode;
//    }
//
//    public String getTvNameBrand() {
//        return tvNameBrand;
//    }
//
//    public void setTvNameBrand(String tvNameBrand) {
//        this.tvNameBrand = tvNameBrand;
//    }
//
//    public String getTvNamePackage() {
//        return tvNamePackage;
//    }
//
//    public void setTvNamePackage(String tvNamePackage) {
//        this.tvNamePackage = tvNamePackage;
//    }
//
//    public String getTvStatusPackage() {
//        return tvStatusPackage;
//    }
//
//    public void setTvStatusPackage(String tvStatusPackage) {
//        this.tvStatusPackage = tvStatusPackage;
//    }
//
//    public String getTvCreditLimitGranted() {
//        return tvCreditLimitGranted;
//    }
//
//    public void setTvCreditLimitGranted(String tvCreditLimitGranted) {
//        this.tvCreditLimitGranted = tvCreditLimitGranted;
//    }
//
//    public String getTvCreditLimitRemaining() {
//        return tvCreditLimitRemaining;
//    }
//
//    public void setTvCreditLimitRemaining(String tvCreditLimitRemaining) {
//        this.tvCreditLimitRemaining = tvCreditLimitRemaining;
//    }
//
//    public String getTvCredEXP() {
//        return tvCredEXP;
//    }
//
//    public void setTvCredEXP(String tvCredEXP) {
//        this.tvCredEXP = tvCredEXP;
//    }
//
//    public List<HistoryDeatilC2R3> getHistoryDeatils() {
//        return historyDeatils;
//    }
//
//    public void setHistoryDeatils(List<HistoryDeatilC2R3> historyDeatils) {
//        this.historyDeatils = historyDeatils;
//    }
//
//    public CardDetails(int imgbrandlogo, int imgQRcode, String tvNameBrand, String tvNamePackage, String tvStatusPackage, String tvCreditLimitGranted, String tvCreditLimitRemaining, String tvCredEXP, List<HistoryDeatilC2R3> historyDeatils) {
//        this.imgbrandlogo = imgbrandlogo;
//        this.imgQRcode = imgQRcode;
//        this.tvNameBrand = tvNameBrand;
//        this.tvNamePackage = tvNamePackage;
//        this.tvStatusPackage = tvStatusPackage;
//        this.tvCreditLimitGranted = tvCreditLimitGranted;
//        this.tvCreditLimitRemaining = tvCreditLimitRemaining;
//        this.tvCredEXP = tvCredEXP;
//        this.historyDeatils = historyDeatils;
//    }
}
