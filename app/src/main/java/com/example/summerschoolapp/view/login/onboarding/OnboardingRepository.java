package com.example.summerschoolapp.view.login.onboarding;

public class OnboardingRepository {
    private static OnboardingRepository instance;

    public static synchronized OnboardingRepository getInstance() {
        if (instance == null) {
            instance = new OnboardingRepository();
        }
        return instance;
    }

    private OnboardingRepository() {

    }
}
