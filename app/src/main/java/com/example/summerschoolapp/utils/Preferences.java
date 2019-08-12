package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.Error;
import com.google.gson.Gson;

import static com.example.summerschoolapp.utils.Const.Preferences.BOOLEAN_USERCANREGISTER_KEY;
import static com.example.summerschoolapp.utils.Const.Preferences.ERROR_REGISTER_SHARED_KEY;
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

    public BigDataResponse getSavedUserData() {
        Gson gson = new Gson();
        String json = preferences.getString(USER_SHARED_KEY, "");
        return gson.fromJson(json, BigDataResponse.class);
    }

    public void saveUserToPreferences(BigDataResponse user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        preferences.edit()
                .putString(USER_SHARED_KEY, json)
                .apply();
    }

    public Boolean getRememberMeStatus() {
        return preferences.getBoolean(Const.Preferences.BOOLEAN_REMEMBERME_SHARED_KEY, false);
    }

    public void setRememberMeStatus(Boolean userRemembered) {
        preferences.edit()
                .putBoolean(Const.Preferences.BOOLEAN_REMEMBERME_SHARED_KEY, userRemembered)
                .apply();
    }

    public Boolean getUserCanRegister() {
        return preferences.getBoolean(BOOLEAN_USERCANREGISTER_KEY, false);
    }

    public void setUserCanRegister(Boolean userCanRegister) {
        preferences.edit()
                .putBoolean(BOOLEAN_USERCANREGISTER_KEY, userCanRegister)
                .apply();
    }

    public void setRegisterError(BigDataResponse error) {
        Gson gson = new Gson();
        String json = gson.toJson(error);
        preferences.edit()
                .putString(ERROR_REGISTER_SHARED_KEY, json)
                .apply();
    }

    public BigDataResponse getRegisterError() {
        Gson gson = new Gson();
        String json = preferences.getString(ERROR_REGISTER_SHARED_KEY, "");
        return gson.fromJson(json, BigDataResponse.class);
    }
}
