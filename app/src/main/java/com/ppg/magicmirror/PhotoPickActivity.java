package com.ppg.magicmirror;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import com.ppg.magicmirror.fragments.AlbumPickerFragment;

/**
 * Created by Daryl on 1/5/2016.
 */
public class PhotoPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);
        FacebookSdk.sdkInitialize(this);

        AlbumPickerFragment firstFragment = new AlbumPickerFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.picker_container, firstFragment).commit();
    }
}
