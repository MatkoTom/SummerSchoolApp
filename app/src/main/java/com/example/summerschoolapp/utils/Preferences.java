package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    private SharedPreferences preferences;

    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // TODO @Matko
    // method name is not saying anything about its use
    // should be something like shouldShowFirstLogin()
    public Boolean getBoolean() {
        return preferences.getBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, false);
    }

    // TODO @Matko
    // method name is not saying anything about its use
    // should be something like setShouldShowFirstLogin(Boolean shouldShow)
    public void setBoolean(Boolean b) {
        preferences.edit()
                .putBoolean(Const.Preferences.BOOLEAN_SHARED_KEY, b)
                .apply();
    }

    // TODO @Matko
    // method name is ambiguous
    public String getString() {
        return preferences.getString(Const.Preferences.STRING_SHARED_KEY, "");
    }

    // TODO @Matko
    // method name is ambiguous
    public void setString(String s) {
        preferences.edit()
                .putString(Const.Preferences.STRING_SHARED_KEY, s)
                .apply();
    }

    // TODO @Matko
    // is this necessary since we should save user object
    public String getEmail() {
        return preferences.getString(Const.Preferences.STRING_USER_EMAIL, "");
    }

    // TODO @Matko
    // is this necessary since we should save user object
    public void setEmail(String s) {
        preferences.edit()
                .putString(Const.Preferences.STRING_USER_EMAIL, s)
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
