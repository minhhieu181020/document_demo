package com.lpb.lifecardsdk.data.model.response.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vannh.lvt on 12/08/2020
 */
public class Style implements Serializable {

    @SerializedName("textSizeMin")
    @Expose
    private Integer textSizeMin;

    @SerializedName("textSizeMax")
    @Expose
    private Integer textSizeMax;

    @SerializedName("paddingTop")
    @Expose
    private Integer paddingTop;

    @SerializedName("paddingLeft")
    @Expose
    private Integer paddingLeft;

    @SerializedName("paddingRight")
    @Expose
    private Integer paddingRight;

    @SerializedName("paddingBottom")
    @Expose
    private Integer paddingBottom;

    @SerializedName("gravityVertical")
    @Expose
    private String gravityVertical;

    @SerializedName("gravityHorizontal")
    @Expose
    private String gravityHorizontal;

    @SerializedName("shadowX")
    @Expose
    private Float shadowX;

    @SerializedName("shadowY")
    @Expose
    private Float shadowY;

    @SerializedName("shadowRadius")
    @Expose
    private Float shadowRadius;

    @SerializedName("shadowColor")
    @Expose
    private String shadowColor;

    public Integer getTextSizeMin() {
        return textSizeMin;
    }

    public Integer getTextSizeMax() {
        return textSizeMax;
    }

    public Integer getPaddingTop() {
        return paddingTop;
    }

    public Integer getPaddingLeft() {
        return paddingLeft;
    }

    public Integer getPaddingRight() {
        return paddingRight;
    }

    public Integer getPaddingBottom() {
        return paddingBottom;
    }

    public String getGravityVertical() {
        return gravityVertical;
    }

    public String getGravityHorizontal() {
        return gravityHorizontal;
    }

    public Float getShadowX() {
        return shadowX;
    }

    public Float getShadowY() {
        return shadowY;
    }

    public Float getShadowRadius() {
        return shadowRadius;
    }

    public String getShadowColor() {
        return shadowColor;
    }






    public void setTextSizeMin(Integer textSizeMin) {
        this.textSizeMin = textSizeMin;
    }

    public void setTextSizeMax(Integer textSizeMax) {
        this.textSizeMax = textSizeMax;
    }

    public void setPaddingTop(Integer paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingLeft(Integer paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(Integer paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingBottom(Integer paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setGravityVertical(String gravityVertical) {
        this.gravityVertical = gravityVertical;
    }

    public void setGravityHorizontal(String gravityHorizontal) {
        this.gravityHorizontal = gravityHorizontal;
    }

    public void setShadowX(Float shadowX) {
        this.shadowX = shadowX;
    }

    public void setShadowY(Float shadowY) {
        this.shadowY = shadowY;
    }

    public void setShadowRadius(Float shadowRadius) {
        this.shadowRadius = shadowRadius;
    }

    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }
}
