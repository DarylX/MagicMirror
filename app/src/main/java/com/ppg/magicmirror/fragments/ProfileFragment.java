package com.ppg.magicmirror.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ppg.magicmirror.R;
import com.ppg.magicmirror.utility.UserStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Daryl on 1/11/2016.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView iw = (ImageView)getActivity().findViewById(R.id.profile_imageview);
        ArrayList<String> pictures = UserStorage.getPictures(getActivity());
        if(pictures.size() != 0 && pictures.get(0) != null)
            Picasso.with(getActivity()).load(pictures.get(0)).into(iw);
    }
}
