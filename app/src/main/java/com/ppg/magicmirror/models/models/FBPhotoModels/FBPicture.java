package com.ppg.magicmirror.models.models.FBPhotoModels;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Daryl on 1/5/2016.
 */
public class FBPicture {
    @SerializedName("is_silhouette")
    public boolean silhouette;

    @SerializedName("url")
    public String url;
}
