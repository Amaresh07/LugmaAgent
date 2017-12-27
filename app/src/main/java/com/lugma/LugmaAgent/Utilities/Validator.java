package com.lugma.LugmaAgent.Utilities;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by CodeBele-PC on 27-12-2017.
 */

public class Validator {
    public  static boolean emailValidation(String targetEmail){
        return Patterns.EMAIL_ADDRESS.matcher(targetEmail).matches();
    }
    public static boolean websiteValidation(String targetWebsite){
      return true;
    }
}
