package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.repositories.AuthorisationRepository;

public class OnboardingViewModel extends BaseViewModel {
    private AuthorisationRepository authRepo;

    public OnboardingViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthorisationRepository();
    }

    public LiveData<User> makeLogin(RequestLogin user) {
        return authRepo.postLoginQuery(user);
    }

    public LiveData<User> makeRegistry(RequestRegister user) {
        return authRepo.postRegisterQuery(user);
    }
}
