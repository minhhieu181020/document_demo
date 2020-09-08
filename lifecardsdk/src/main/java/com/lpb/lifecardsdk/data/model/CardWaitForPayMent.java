package com.lpb.lifecardsdk.data.model;

public class CardWaitForPayMent {
//    imgBackground
//            rcImgLogo
//    tvCardName
//            tvCodeProduct
//    tvPriceNew
//            tvPriceOrigin
//    tvDescription


    private int imgBackground;
    private int[] rcImgLogo;

    public int getImgBackground() {
        return imgBackground;
    }

    public void setImgBackground(int imgBackground) {
        this.imgBackground = imgBackground;
    }

    public int[] getRcImgLogo() {
        return rcImgLogo;
    }

    public void setRcImgLogo(int[] rcImgLogo) {
        this.rcImgLogo = rcImgLogo;
    }

    public String getTvCardName() {
        return tvCardName;
    }

    public void setTvCardName(String tvCardName) {
        this.tvCardName = tvCardName;
    }

    public String getTvCodeProduct() {
        return tvCodeProduct;
    }

    public void setTvCodeProduct(String tvCodeProduct) {
        this.tvCodeProduct = tvCodeProduct;
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

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }

    public CardWaitForPayMent(int imgBackground, int[] rcImgLogo, String tvCardName, String tvCodeProduct, String tvPriceNew, String tvPriceOrigin, String tvDescription) {
        this.imgBackground = imgBackground;
        this.rcImgLogo = rcImgLogo;
        this.tvCardName = tvCardName;
        this.tvCodeProduct = tvCodeProduct;
        this.tvPriceNew = tvPriceNew;
        this.tvPriceOrigin = tvPriceOrigin;
        this.tvDescription = tvDescription;
    }

    private String tvCardName, tvCodeProduct, tvPriceNew, tvPriceOrigin, tvDescription;


}
