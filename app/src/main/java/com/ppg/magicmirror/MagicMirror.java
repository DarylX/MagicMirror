package com.ppg.magicmirror;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.ppg.magicmirror.utility.Util;

/**
 * Created by Daryl on 1/11/2016.
 */
public class MagicMirror extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(this);
        Util.getApplicationHashKey(this);
    }
}
