package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.summerschoolapp.model.Data;
import com.google.gson.Gson;

import static com.example.summerschoolapp.utils.Const.Preferences.USER_SHARED_KEY;

public class Preferences {
    private SharedPreferences preferences;

    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Boolean shouldShowFirstLogin() {
        return preferences.getBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, false);
    }

    public void setShouldShowFirstLogin(Boolean firstLogin) {
        preferences.edit()
                .putBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, firstLogin)
                .apply();
    }

    public Data getSavedUserData() {
        Gson gson = new Gson();
        String json = preferences.getString(USER_SHARED_KEY, "");
        return gson.fromJson(json, Data.class);
    }

    public void saveUserToPreferences(Data user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        preferences.edit()
                .putString(USER_SHARED_KEY, json)
                .apply();
    }
}
