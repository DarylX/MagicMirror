package com.ppg.magicmirror.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Daryl on 1/8/2016.
 */
public class UserStorage {

    static public void savePictures(Context context, ArrayList <String> urls){
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < urls.size(); i++){
            editor.putString("userPhoto"+i,urls.get(i));
        }
        editor.commit();
    }

    static public ArrayList<String> getPictures(Context context){

        ArrayList<String> strings = new ArrayList<>();
        String tempString;

        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        for(int i = 0; i < 5; i++){
            tempString = prefs.getString("userPhoto"+i,null);
            if(tempString!=null){
                strings.add(tempString);
            }
        }

        return strings;
    }

    static public void saveToDatabase(){
        Firebase myFirebaseRef = new Firebase("https://sweltering-heat-7703.firebaseio.com/");
    }

}
