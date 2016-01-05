package com.ppg.magicmirror.fragments;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.google.gson.Gson;
import com.ppg.magicmirror.R;
import com.google.android.gms.plus.PlusOneButton;
import com.ppg.magicmirror.models.models.FBAlbumModels.FBAlbumsGraphObject;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoGraphObject;
import com.ppg.magicmirror.models.models.FBPhotoModels.FBPhotoImageGraphObject;
import com.squareup.picasso.Picasso;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        //Find the +1 button
        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

        // TODO: TAKE OUT AND PUT SOMEWHERE ELSE
        Log.d("User Id", AccessToken.getCurrentAccessToken().getUserId().toString());
        Log.d("User Name", Profile.getCurrentProfile().getFirstName());
        Log.d("Photo URI", Profile.getCurrentProfile().getProfilePictureUri(50, 50).toString());

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+AccessToken.getCurrentAccessToken().getUserId().toString()+"/albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.d("Result", response.getRawResponse());
                        Gson gson = new Gson();
                        final FBAlbumsGraphObject ob = gson.fromJson(response.getRawResponse(), FBAlbumsGraphObject.class);


                        new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                                "/" + ob.Albums.get(0).id + "/photos",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                                        Gson gson = new Gson();
                                        Log.d("Photos in Album Result", response.getRawResponse());
                                        final FBPhotoGraphObject po = gson.fromJson(response.getRawResponse(), FBPhotoGraphObject.class);
                                        GraphRequest request = new GraphRequest(
                                                AccessToken.getCurrentAccessToken(),
                                                "/"+po.photos.get(0).id,
                                                null,
                                                HttpMethod.GET,
                                                new GraphRequest.Callback() {
                                                    public void onCompleted(GraphResponse response) {
                                                        /* handle the result */
                                                        Gson gson = new Gson();
                                                        final FBPhotoImageGraphObject pio = gson.fromJson(response.getRawResponse(), FBPhotoImageGraphObject.class);
                                                        ImageView iw = (ImageView)getActivity().findViewById(R.id.imageView2);
                                                        Picasso.with(getActivity()).load(pio.images.get(0).getSource()).into(iw);
                                                    }
                                                }


                                        );
                                        Bundle parameters = new Bundle();
                                        parameters.putString("fields", "images");
                                        request.setParameters(parameters);
                                        request.executeAsync();
                                    }
                                }
                        ).executeAsync();

                    }
                }
        ).executeAsync();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
