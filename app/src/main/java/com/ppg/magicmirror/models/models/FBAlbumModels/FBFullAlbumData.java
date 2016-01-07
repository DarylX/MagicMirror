package com.ppg.magicmirror.models.models.FBAlbumModels;

import com.ppg.magicmirror.models.models.FBPhotoModels.FBCoverPhoto;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBImage;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhoto;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoImageGraphObject;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPicture;

import java.util.ArrayList;

/**
 * Created by Daryl on 1/5/2016.
 */
public class FBFullAlbumData {
    public FBAlbumsGraphObject fbAlbumsGraphObject;
    public ArrayList<FBCoverPhoto> fbCoverPhotos;
    public ArrayList<FBPhotoImageGraphObject> fbPictures;
}
