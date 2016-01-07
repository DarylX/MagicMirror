package com.ppg.magicmirror.models.models.FBPhotoModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daryl on 1/6/2016.
 */
public class FBCoverPhoto {
    @SerializedName("cover_photo")
    public FBPhoto coverphoto;

    @SerializedName("id")
    public String id;
}
