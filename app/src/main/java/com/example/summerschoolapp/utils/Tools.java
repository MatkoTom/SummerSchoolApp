package com.example.summerschoolapp.utils;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {

    // TODO @Matko
    // check this out
//    private static Preferences appPreferences;
//
//    public static Preferences getSharedPreferences(Context context) {
//
//        // Note: If both context and appPreferences is null, returned result will be null and probably the app will crash. We can try and handle it later if it happens.
//        if (context == null && appPreferences == null && ZenScreenApp.getAppInstance() == null) {
//            return null;
//        }
//
//        if (appPreferences == null) {
//            appPreferences = new Preferences(context != null ? context : ZenScreenApp.getAppInstance().getApplicationContext());
//        }
//
//        return appPreferences;
//    }

    private static Preferences appPreferences;

    public static Preferences getSharedPreferences(Context context) {
        if (context == null && appPreferences == null) {
            return null;
        }

        if (appPreferences == null) {
            appPreferences = new Preferences(context);
        }

        return appPreferences;
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
