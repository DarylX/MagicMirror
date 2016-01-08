package com.ppg.magicmirror.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.ppg.magicmirror.R;
import com.ppg.magicmirror.adapters.AlbumPickerAdapter;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBAlbumsGraphObject;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBFullAlbumData;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBCoverPhoto;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoImageGraphObject;

import java.util.ArrayList;

/**
 * Created by Daryl on 1/7/2016.
 */
public class AlbumPickerFragment extends Fragment {

    FBFullAlbumData fullAlbumData;
    Context context;
    public AlbumPickerFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_picker, container, false);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId().toString() + "/albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(final GraphResponse response) {
                        final Gson gson = new Gson();
                        fullAlbumData = new FBFullAlbumData();
                        fullAlbumData.fbAlbumsGraphObject = gson.fromJson(response.getRawResponse(), FBAlbumsGraphObject.class);
                        fullAlbumData.fbCoverPhotos = new ArrayList<>();
                        fullAlbumData.fbPictures = new ArrayList<>();

                        GraphRequestBatch batch = new GraphRequestBatch();
                        final GraphRequestBatch batch2 = new GraphRequestBatch();
                        for(int i = 0; i < fullAlbumData.fbAlbumsGraphObject.Albums.size(); i++) {
                            Log.d("call picture", "/" + fullAlbumData.fbAlbumsGraphObject.Albums.get(i).id);



                            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                                    "/" + fullAlbumData.fbAlbumsGraphObject.Albums.get(i).id,
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse r) {
                                            fullAlbumData.fbCoverPhotos.add(gson.fromJson(r.getRawResponse(), FBCoverPhoto.class));
                                            Log.d("response", r.getRawResponse());
                                        }
                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "cover_photo");
                            request.setParameters(parameters);
                            batch.add(request);
                        }


                        batch.addCallback(new GraphRequestBatch.Callback() {
                            @Override
                            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                                for(int i = 0; i < fullAlbumData.fbCoverPhotos.size(); i++) {
                                    Log.d("call picture", "/" + fullAlbumData.fbCoverPhotos.get(i).id);

                                    GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                                            "/" + fullAlbumData.fbCoverPhotos.get(i).coverphoto.id,
                                            null,
                                            HttpMethod.GET,
                                            new GraphRequest.Callback() {
                                                public void onCompleted(GraphResponse r) {
                                                    fullAlbumData.fbPictures.add(gson.fromJson(r.getRawResponse(), FBPhotoImageGraphObject.class));
                                                    Log.d("response", r.getRawResponse());
                                                }
                                            });

                                    Bundle parameters = new Bundle();
                                    parameters.putString("fields", "images");
                                    request.setParameters(parameters);
                                    batch2.add(request);

                                }
                                batch2.executeAsync();

                                batch2.addCallback(new GraphRequestBatch.Callback() {
                                    @Override
                                    public void onBatchCompleted(GraphRequestBatch graphRequests) {
                                        RecyclerView albumList = (RecyclerView) ((Activity)context).findViewById(R.id.album_recycler_view);
                                        AlbumPickerAdapter albumAdapter = new AlbumPickerAdapter(fullAlbumData, context);
                                        albumList.setAdapter(albumAdapter);
                                        albumList.setLayoutManager(new LinearLayoutManager(context));
                                    }});
                            }
                        });
                        batch.executeAsync();
                    }
                }).executeAsync();

        return view;
    }
}
