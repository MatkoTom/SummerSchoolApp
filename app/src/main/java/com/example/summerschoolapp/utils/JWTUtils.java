package com.example.summerschoolapp.utils;

import android.util.Base64;

import com.example.summerschoolapp.model.User;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import timber.log.Timber;

public class JWTUtils {

    public static void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Timber.d("Header: %s", getJson(split[0]));
            Timber.d("Body: %s", getJson(split[1]));

            String email = new Gson().fromJson(getJson(split[1]), User.class).getEmail();
            Timber.d("User email: %s", email);

        } catch (UnsupportedEncodingException e) {
            Timber.e(e.toString());
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}

