package com.example.summerschoolapp.view.login.onboarding;

import androidx.lifecycle.ViewModel;

public class OnboardingViewModel extends ViewModel {
    private OnboardingRepository repository;

    public OnboardingViewModel() {
        repository = OnboardingRepository.getInstance();
    }
}
