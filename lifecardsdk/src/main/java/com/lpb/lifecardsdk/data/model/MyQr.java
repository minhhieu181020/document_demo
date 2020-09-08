package com.lpb.lifecardsdk.data.model;

public class MyQr {
    private int imgBackgroundCard;
    private int[] rvLogoCard;
    private String tvNumberCard, tvNameCards, tvContent;

    public int getImgBackgroundCard() {
        return imgBackgroundCard;
    }

    public void setImgBackgroundCard(int imgBackgroundCard) {
        this.imgBackgroundCard = imgBackgroundCard;
    }

    public int[] getRvLogoCard() {
        return rvLogoCard;
    }

    public void setRvLogoCard(int[] rvLogoCard) {
        this.rvLogoCard = rvLogoCard;
    }

    public String getTvNumberCard() {
        return tvNumberCard;
    }

    public void setTvNumberCard(String tvNumberCard) {
        this.tvNumberCard = tvNumberCard;
    }

    public String getTvNameCards() {
        return tvNameCards;
    }

    public void setTvNameCards(String tvNameCards) {
        this.tvNameCards = tvNameCards;
    }

    public String getTvContent() {
        return tvContent;
    }

    public void setTvContent(String tvContent) {
        this.tvContent = tvContent;
    }

    public MyQr(int imgBackgroundCard, int[] rvLogoCard, String tvNumberCard, String tvNameCards, String tvContent) {
        this.imgBackgroundCard = imgBackgroundCard;
        this.rvLogoCard = rvLogoCard;
        this.tvNumberCard = tvNumberCard;
        this.tvNameCards = tvNameCards;
        this.tvContent = tvContent;
    }
}
