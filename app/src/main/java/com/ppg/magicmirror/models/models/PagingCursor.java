package com.ppg.magicmirror.models.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daryl on 12/26/2015.
 */
public class PagingCursor {

    @SerializedName("before")
    String before;

    @SerializedName("after")
    String after;
}
