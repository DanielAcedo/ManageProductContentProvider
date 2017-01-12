package com.danielacedo.manageproductrecycler.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.danielacedo.manageproductrecycler.interfaces.Preferences;

/**
 * Created by Daniel on 10/11/16.
 */

public class AccountPreferenceImpl implements Preferences {

    private static AccountPreferenceImpl instance;
    private Context context;

    public static final String FILE = "com.danielacedo.manageproductrecycler_preferences";

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    private SharedPreferences sharedPreferences;


    private AccountPreferenceImpl(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //Singleton
    public static AccountPreferenceImpl getInstance(Context context){
        if(instance == null){
            instance = new AccountPreferenceImpl(context);
        }

        return instance;
    }

    public void putUser(String user){
        getEditor().putString(USER, user).apply();
    }

    public void putPassword(String password){
        getEditor().putString(PASSWORD, password).apply();
    }

    public void putEmail(String email){
        getEditor().putString(EMAIL, email).apply();
    }

    public String readUser(){
        return sharedPreferences.getString(USER, "");
    }

    public String readPassword(){
        return sharedPreferences.getString(PASSWORD, "");
    }

    public String readEmail(){
        return sharedPreferences.getString(EMAIL, "");
    }

    private SharedPreferences.Editor getEditor(){
        return sharedPreferences.edit();
    }
}
