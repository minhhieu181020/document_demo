package com.lpb.lifecardsdk.data.model.response.default_.defcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vannh.lvt on 01/09/2020
 */
public class CardGroup {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("defcard")
    @Expose
    private List<DefCard> defCard = null;


    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<DefCard> getDefCard() {
        return defCard;
    }
}
