package com.ppg.magicmirror.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.ppg.magicmirror.R;
import com.ppg.magicmirror.models.models.User;
import com.ppg.magicmirror.utility.Util;

/**
 * Login with facebook
 */
public class LoginActivity extends AppCompatActivity{

    private CallbackManager callbackManager;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        ref = new Firebase("https://sweltering-heat-7703.firebaseio.com");

        ((LoginButton)this.findViewById(R.id.login_button)).setReadPermissions("public_profile", "user_photos");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onFacebookAccessTokenChange(AccessToken.getCurrentAccessToken());
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

    private void onFacebookAccessTokenChange(final AccessToken token) {
        if (token != null) {
            ref.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    User.createUserOnDatabase(token);
                    // The Facebook user is now authenticated with your Firebase app
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);

                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                    Log.d("error","error");
                }
            });
        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            ref.unauth();
            Log.d("unauth", "unauth");
        }
    }
}

