package com.ppg.magicmirror.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ppg.magicmirror.R;
import com.ppg.magicmirror.activities.MainActivity;

/**
 * Created by Daryl on 1/14/2016.
 */
public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
    private Firebase ref;

    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ref = new Firebase("https://sweltering-heat-7703.firebaseio.com");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        callbackManager = CallbackManager.Factory.create();
        ((LoginButton)view.findViewById(R.id.login_button)).setReadPermissions("public_profile", "user_photos");
        ((LoginButton)view.findViewById(R.id.login_button)).setFragment(this);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onFacebookAccessTokenChange(AccessToken.getCurrentAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Canceled", "Canceled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("Error", exception.toString());
                    }
                });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void onFacebookAccessTokenChange(final AccessToken token) {
        if (token != null) {
            ref.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    checkForFirstTimeLogin();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                    Log.d("error", firebaseError.toString());
                }
            });
        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            ref.unauth();
            Log.d("unauth", "unauth");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkForFirstTimeLogin() {
        Firebase reff = ref.child("users").child(AccessToken.getCurrentAccessToken().getUserId());
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                    goToFirstTimeLogin();
                } else {
                    Toast.makeText(getContext(), "YES", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("error", firebaseError.toString());
            }
        });
    }

    private void goToFirstTimeLogin(){
        FirstTimeFragment newFragment = new FirstTimeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container_login, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
