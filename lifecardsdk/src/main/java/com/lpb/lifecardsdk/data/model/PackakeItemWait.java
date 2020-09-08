package com.lpb.lifecardsdk.data.model;

public class PackakeItemWait {
    //    imgLogo tvName,tvPriceNew,tvPriceOrigin,tvUseLimit,tvExpirationDate
    private int imgLogo;
    private String tvName, tvPriceNew, tvPriceOrigin, tvUseLimit, tvExpirationDate;

    public int getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(int imgLogo) {
        this.imgLogo = imgLogo;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvPriceNew() {
        return tvPriceNew;
    }

    public void setTvPriceNew(String tvPriceNew) {
        this.tvPriceNew = tvPriceNew;
    }

    public String getTvPriceOrigin() {
        return tvPriceOrigin;
    }

    public void setTvPriceOrigin(String tvPriceOrigin) {
        this.tvPriceOrigin = tvPriceOrigin;
    }

    public String getTvUseLimit() {
        return tvUseLimit;
    }

    public void setTvUseLimit(String tvUseLimit) {
        this.tvUseLimit = tvUseLimit;
    }

    public String getTvExpirationDate() {
        return tvExpirationDate;
    }

    public void setTvExpirationDate(String tvExpirationDate) {
        this.tvExpirationDate = tvExpirationDate;
    }

    public PackakeItemWait(int imgLogo, String tvName, String tvPriceNew, String tvPriceOrigin, String tvUseLimit, String tvExpirationDate) {
        this.imgLogo = imgLogo;
        this.tvName = tvName;
        this.tvPriceNew = tvPriceNew;
        this.tvPriceOrigin = tvPriceOrigin;
        this.tvUseLimit = tvUseLimit;
        this.tvExpirationDate = tvExpirationDate;
    }
}
