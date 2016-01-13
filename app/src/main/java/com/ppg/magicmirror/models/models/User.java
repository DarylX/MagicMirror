package com.ppg.magicmirror.models.models;

import com.facebook.AccessToken;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daryl on 1/11/2016.
 */
public class User {

    public String UserName;
    public String Photo1URL;


    static public void createUserOnDatabase(AccessToken token){
        Firebase ref = new Firebase("https://sweltering-heat-7703.firebaseio.com");
        Firebase usersRef = ref.child("users").child(token.getUserId());

        Map<String, Object> user = new HashMap<String, Object>();
        user.put("photo",null);

        usersRef.updateChildren(user);
    }

    static public void updatePhoto(AccessToken token,String url){
        Firebase ref = new Firebase("https://sweltering-heat-7703.firebaseio.com");
        Firebase usersRef = ref.child("users").child(token.getUserId());

        Map<String, Object> user = new HashMap<String, Object>();
        user.put("photo",url);

        usersRef.updateChildren(user);
    }

}
