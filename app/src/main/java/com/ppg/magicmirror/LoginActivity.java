package com.ppg.magicmirror;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ppg.magicmirror.utility.Util;

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

        final Context context = this;

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("success", "success");
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        LoginActivity.this.finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                        Log.d("success", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("success", "Error");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}

