package com.lpb.lifecardsdk.data.model;

public class CardDeatilsInFor {
//    imgLogo
//            tvName
//    tvUseLimit
//            tvLimitRemaining
//    tvExpirationDate

    private int imgLogo;
    private String tvName, tvUseLimit, tvLimitRemaining, tvExpirationDate;
    private boolean stats;

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

    public String getTvUseLimit() {
        return tvUseLimit;
    }

    public void setTvUseLimit(String tvUseLimit) {
        this.tvUseLimit = tvUseLimit;
    }

    public String getTvLimitRemaining() {
        return tvLimitRemaining;
    }

    public void setTvLimitRemaining(String tvLimitRemaining) {
        this.tvLimitRemaining = tvLimitRemaining;
    }

    public String getTvExpirationDate() {
        return tvExpirationDate;
    }

    public void setTvExpirationDate(String tvExpirationDate) {
        this.tvExpirationDate = tvExpirationDate;
    }

    public boolean isStats() {
        return stats;
    }

    public void setStats(boolean stats) {
        this.stats = stats;
    }

    public CardDeatilsInFor(int imgLogo, String tvName, String tvUseLimit, String tvLimitRemaining, String tvExpirationDate, boolean stats) {
        this.imgLogo = imgLogo;
        this.tvName = tvName;
        this.tvUseLimit = tvUseLimit;
        this.tvLimitRemaining = tvLimitRemaining;
        this.tvExpirationDate = tvExpirationDate;
        this.stats = stats;
    }
}
