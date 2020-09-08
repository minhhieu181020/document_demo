package com.lpb.lifecardsdk.data.model.request.default_.face_verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 15/07/2020
 */
public class ImageUniqueID {

    @SerializedName("base64")
    @Expose
    private String base64;
    @SerializedName("imageType")
    @Expose
    private String imageType;

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}
