package com.lpb.lifecardsdk.data.model;

public class BuyCardItem {
    private int background;
    private int[] logo;
    private String cardName,priceOrigin,priceNew,description;

    public BuyCardItem(int background, int[] logo, String cardName, String priceOrigin, String priceNew, String description) {
        this.background = background;
        this.logo = logo;
        this.cardName = cardName;
        this.priceOrigin = priceOrigin;
        this.priceNew = priceNew;
        this.description = description;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int[] getLogo() {
        return logo;
    }

    public void setLogo(int[] logo) {
        this.logo = logo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPriceOrigin() {
        return priceOrigin;
    }

    public void setPriceOrigin(String priceOrigin) {
        this.priceOrigin = priceOrigin;
    }

    public String getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(String priceNew) {
        this.priceNew = priceNew;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
