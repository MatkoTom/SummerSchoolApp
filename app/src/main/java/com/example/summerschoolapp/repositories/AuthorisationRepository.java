package com.example.summerschoolapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.schedulers.Schedulers;

public class AuthorisationRepository {

    public AuthorisationRepository() {
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