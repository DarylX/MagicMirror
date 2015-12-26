package com.ppg.magicmirror.models.models.FBAlbumModels;

import com.google.gson.annotations.SerializedName;
import com.ppg.magicmirror.models.models.Paging;

import java.util.List;

/**
 * Created by Daryl on 12/26/2015.
 */
public class FBAlbumsGraphObject {

    @SerializedName("data")
    public List<FBAlbum> Albums;

    @SerializedName("paging")
    Paging paging;
}
