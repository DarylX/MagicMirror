package com.ppg.magicmirror.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ppg.magicmirror.R;
import com.ppg.magicmirror.activities.MainActivity;
import com.ppg.magicmirror.activities.PhotoPickActivity;
import com.ppg.magicmirror.models.models.User;

/**
 * Created by Daryl on 1/14/2016.
 */
public class FirstTimeFragment extends Fragment {
    Firebase ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ref = new Firebase("https://sweltering-heat-7703.firebaseio.com/users");
        User.createUserOnDatabase(AccessToken.getCurrentAccessToken());
        ref = ref.child(AccessToken.getCurrentAccessToken().getUserId()).child("photo");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "YES", Toast.LENGTH_SHORT).show();
                    getActivity().findViewById(R.id.button5).setEnabled(true);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            });
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_time, container, false);

        view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PhotoPickActivity.class);
                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        view.setEnabled(false);
        view.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
