package com.ppg.magicmirror.models.models.FBPhotoModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daryl on 1/5/2016.
 */
public class FBImage {
    @SerializedName("height")
    public int height;

    @SerializedName("source")
    private String source;

    @SerializedName("width")
    public int width;


    public String getSource() {
        return source.replaceAll("\\\\","");
    }
}
