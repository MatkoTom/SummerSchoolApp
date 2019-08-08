package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    private SharedPreferences preferences;

    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Boolean shouldShowFirstLogin() {
        return preferences.getBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, false);
    }

    public void setShouldShowFirstLogin(Boolean b) {
        preferences.edit()
                .putBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, b)
                .apply();
    }

    // TODO @Matko
    // is this necessary since we should save user object
    public String getPassword() {
        return preferences.getString(Const.Preferences.STRING_USER_PASSWORD, "");
    }

    // TODO @Matko
    // is this necessary since we should save user object
    // password should not be saved to phone under any circumstances
    public void setPassword(String s) {
        preferences.edit()
                .putString(Const.Preferences.STRING_USER_PASSWORD, s)
                .apply();
    }
}
