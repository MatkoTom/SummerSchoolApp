package com.example.summerschoolapp.view.login.onboarding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

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

    public LiveData<User> postLoginQuery(RequestLogin user) {
        return LiveDataReactiveStreams.fromPublisher(RetrofitAdapter.getRetrofitClient()
        .login(user)
        .subscribeOn(Schedulers.io()));
    }

    public LiveData<User> postRegisterQuery(RequestRegister user) {
        return LiveDataReactiveStreams.fromPublisher(RetrofitAdapter.getRetrofitClient()
        .register(user)
        .subscribeOn(Schedulers.io()));
    }
}
