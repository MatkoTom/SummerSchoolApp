package com.example.summerschoolapp.view.main.fragmentNews;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;

public class NewsFragmentViewModel extends BaseViewModel {
    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean isAdmin() {
        return Integer.parseInt(Tools.getSharedPreferences(getApplication()).getSavedUserData().getRole()) == Const.Preferences.ADMIN_ROLE;
    }
}
