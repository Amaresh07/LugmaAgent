package com.lugma.LugmaAgent.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lugma.LugmaAgent.SigninActivity;

import java.util.HashMap;

/**
 * Created by CodeBele-PC on 18-12-2017.
 */

public class UserSessionManager {
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor,editor1;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "LugmaPref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_id = "emp_id";
    public static final String KEY_email = "email";
    public static final String KEY_phone = "phone";
    // Email address (make variable public to access from outside)
    public static final String KEY_dob = "dob";
    public static final String KEY_gnder = "gender";
    public static final String KEY_address="address";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor1=pref.edit();
    }
    //Create login session
    public void createUserLoginSession(String emp_id,String email_id,String phone,String name,String dob,String gender,String address){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        editor.putString(KEY_id, emp_id);
        editor.putString(KEY_email,email_id);
        editor.putString(KEY_phone,phone);
        editor.putString(KEY_dob,dob);
        editor.putString(KEY_gnder,gender);
        editor.putString(KEY_address,address);
        // commit changes
        editor.commit();
    }
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SigninActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        //session_id
        user.put(KEY_id, pref.getString(KEY_id, null));
        //session_name
        user.put(KEY_email, pref.getString(KEY_email, null));
        user.put(KEY_phone,pref.getString(KEY_phone,null));
        //mobile
        user.put(KEY_dob,pref.getString(KEY_dob,null));
        //subscribe
        user.put(KEY_gnder,pref.getString(KEY_gnder,null));
        user.put(KEY_address,pref.getString(KEY_address,null));
        // return user
        return user;
    }
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, SigninActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
