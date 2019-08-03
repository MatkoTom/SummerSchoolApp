package com.example.summerschoolapp.view.login.onboarding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;

import java.util.List;

public class OnboardingViewModel extends ViewModel {
    private OnboardingRepository repository;

    public OnboardingViewModel() {
        repository = OnboardingRepository.getInstance();
    }

    public LiveData<User> makeLogin(RequestLogin user) {
        return repository.postLoginQuery(user);
    }

    public LiveData<User> makeRegistry(RequestRegister user) {
        return repository.postRegisterQuery(user);
    }
}
