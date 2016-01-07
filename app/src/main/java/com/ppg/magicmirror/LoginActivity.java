package com.ppg.magicmirror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ppg.magicmirror.utility.Util;

import java.util.Arrays;

/**
 * Login with facebook
 */
public class LoginActivity extends AppCompatActivity{

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        Util.getApplicationHashKey(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LoginManager.getInstance().logInWithReadPermissions(
                                LoginActivity.this,
                                Arrays.asList("public_profile,user_photos"));
                        Intent intent = new Intent(LoginActivity.this, PhotoPickActivity.class);
                        LoginActivity.this.finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}

