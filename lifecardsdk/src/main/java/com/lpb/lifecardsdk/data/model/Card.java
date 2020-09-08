package com.lpb.lifecardsdk.data.model;

public class Card {
    private int imgAvatar;
    private String tvNumberCard;

    public int getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(int imgAvatar) {
        this.imgAvatar = imgAvatar;
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

    public Card(int imgAvatar, String tvNumberCard, String tvNameCards) {
        this.imgAvatar = imgAvatar;
        this.tvNumberCard = tvNumberCard;
        this.tvNameCards = tvNameCards;
    }

    private String tvNameCards;
}
