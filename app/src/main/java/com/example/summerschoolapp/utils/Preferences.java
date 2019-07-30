package com.example.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.summerschoolapp.utils.Const.BOOLEAN_SHARED_KEY;

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
        Boolean b = preferences.getBoolean(BOOLEAN_SHARED_KEY, false);

        return b;
    }

    public void setBoolean(Boolean b) {
        editor = preferences.edit();
        editor.putBoolean(BOOLEAN_SHARED_KEY, b);
        editor.apply();
    }
}
