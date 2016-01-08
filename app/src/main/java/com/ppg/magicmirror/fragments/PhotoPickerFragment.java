package com.ppg.magicmirror.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.common.api.Batch;
import com.google.gson.Gson;
import com.ppg.magicmirror.R;
import com.ppg.magicmirror.adapters.AlbumPickerAdapter;
import com.ppg.magicmirror.adapters.PhotoPickerAdapter;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBAlbum;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBAlbumsGraphObject;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBFullAlbumData;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBCoverPhoto;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBImage;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhoto;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoGraphObject;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoImageGraphObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daryl on 1/7/2016.
 */
public class PhotoPickerFragment extends Fragment {
    private FBAlbum album;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_picker, container, false);


        getPictures(this.album);

        return view;
    }

    public void setAlbum(FBAlbum album) {
        this.album = album;
    }

    private void getPictures(FBAlbum album){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                album.id + "/photos",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        final Gson gson = new Gson();
                        FBPhotoGraphObject fbPhotoGraphObject= new FBPhotoGraphObject();
                        fbPhotoGraphObject = gson.fromJson(response.getRawResponse(), FBPhotoGraphObject.class);

                        final ArrayList<String> photoUrls = new ArrayList<>();

                        GraphRequestBatch batch = new GraphRequestBatch();

                        for(int i = 0; i < fbPhotoGraphObject.photos.size(); i++) {
                            Log.d("call picture", "/" + fbPhotoGraphObject.photos.get(i).id);

                            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                                    "/" + fbPhotoGraphObject.photos.get(i).id,
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse r) {
                                            photoUrls.add(gson.fromJson(r.getRawResponse(), FBPhotoImageGraphObject.class).images.get(0).getSource());
                                            Log.d("response", r.getRawResponse());
                                        }
                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "images");
                            request.setParameters(parameters);
                            batch.add(request);
                        }

                        batch.addCallback(new GraphRequestBatch.Callback() {
                            @Override
                            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                                GridView gridview = (GridView) getActivity().findViewById(R.id.photo_picker_gridview);
                                gridview.setAdapter(new PhotoPickerAdapter(getContext(),photoUrls));

                                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v,
                                                            int position, long id) {
                                        Toast.makeText(getContext(), "" + position,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }});

                        batch.executeAsync();
                    }
                }).executeAsync();
    }
}
