package com.ppg.magicmirror.models.models.FBPhotoModels;

import com.google.gson.annotations.SerializedName;
import com.ppg.magicmirror.models.models.Paging;

import java.util.List;

/**
 * Created by Daryl on 12/26/2015.
 */
public class FBPhotoGraphObject {

    @SerializedName("data")
    public List<FBPhoto> photos;

    @SerializedName("paging")
    public Paging paging;
}
