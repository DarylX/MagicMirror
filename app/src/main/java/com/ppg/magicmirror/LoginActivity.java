package com.ppg.magicmirror;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        Log.d("Signature", FacebookSdk.getApplicationSignature(getApplicationContext()));
    }
}

