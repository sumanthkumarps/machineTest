package com.effone.machinetest.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sumanth.peddinti on 1/18/2018.
 */

public class AppPreferene {
    private static final String PREFS_NAME = "prefs";
    private static AppPreferene instance;
    private final SharedPreferences sharedPreferences;
    private static final String EMAIL_ID = "email_id" ;

    public AppPreferene(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferene with(Context context) {

        if (instance == null) {
            instance = new AppPreferene(context);
        }
        return instance;
    }

    public void setEmail(String   email) {

        sharedPreferences
                .edit()
                .putString(EMAIL_ID, email)
                .apply();
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL_ID, "");
    }
}
