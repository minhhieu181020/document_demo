package com.lpb.lifecardsdk.data.model.request.default_;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vannh.lvt on 20/07/2020
 */
public class FaceDeleteRequest {
    @SerializedName("uniqueName")
    @Expose
    private String uniqueName;

    public FaceDeleteRequest(String uniqueName) {
        this.uniqueName = uniqueName;
    }
}
