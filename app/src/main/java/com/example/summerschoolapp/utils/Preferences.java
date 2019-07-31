package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.summerschoolapp.utils.Const.BOOLEAN_SHARED_KEY;
import static com.example.summerschoolapp.utils.Const.STRING_SHARED_KEY;
import static com.example.summerschoolapp.utils.Const.STRING_USER_EMAIL;
import static com.example.summerschoolapp.utils.Const.STRING_USER_PASSWORD;

public class Preferences {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Boolean getBoolean() {
        editor = preferences.edit();

        return preferences.getBoolean(BOOLEAN_SHARED_KEY, false);
    }

    public void setBoolean(Boolean b) {
        editor = preferences.edit();
        editor.putBoolean(BOOLEAN_SHARED_KEY, b);
        editor.apply();
    }

    public String getString() {
        editor = preferences.edit();

        return preferences.getString(STRING_SHARED_KEY, "");
    }

    public void setString(String s) {
        editor = preferences.edit();
        editor.putString(STRING_SHARED_KEY, s);
        editor.apply();
    }

    public String getEmail() {
        editor = preferences.edit();

        return preferences.getString(STRING_USER_EMAIL, "");
    }

    public void setEmail(String s) {
        editor = preferences.edit();
        editor.putString(STRING_USER_EMAIL, s);
        editor.apply();
    }

    public String getPassword() {
        editor = preferences.edit();

        return preferences.getString(STRING_USER_PASSWORD, "");
    }

    public void setPassword(String s) {
        editor = preferences.edit();
        editor.putString(STRING_USER_PASSWORD, s);
        editor.apply();
    }
}
