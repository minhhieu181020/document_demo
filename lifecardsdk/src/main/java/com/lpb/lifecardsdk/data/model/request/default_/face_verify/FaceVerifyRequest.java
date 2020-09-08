package com.lpb.lifecardsdk.data.model.request.default_.face_verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 15/07/2020
 */
public class FaceVerifyRequest {
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;

    @SerializedName("imageLives")
    @Expose
    private List<ImageLive> imageLives;


    @SerializedName("imageUniqueId")
    @Expose
    private ImageUniqueID imageUniqueId;


    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setImageLives(List<ImageLive> imageLives) {
        this.imageLives = imageLives;
    }

    public void setImageUniqueId(ImageUniqueID imageUniqueId) {
        this.imageUniqueId = imageUniqueId;
    }
}
