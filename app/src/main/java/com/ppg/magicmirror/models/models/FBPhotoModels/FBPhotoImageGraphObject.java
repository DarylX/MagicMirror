package com.ppg.magicmirror.models.models.FBPhotoModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daryl on 1/5/2016.
 */
public class FBPhotoImageGraphObject {

    @SerializedName("images")
    public List<FBImage> images;
}
