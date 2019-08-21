package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.login.RequestLogin;
import com.example.summerschoolapp.model.signup.RequestSignup;
import com.example.summerschoolapp.model.login.ResponseLogin;
import com.example.summerschoolapp.model.signup.ResponseSignup;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AuthorisationRepository {

    public AuthorisationRepository() {
    }

    public Single<ResponseLogin> postLoginQuery(RequestLogin user) {
        return RetrofitAdapter.getRetrofitClient()
                .login(user)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseSignup> postSignupQuery(RequestSignup user) {
        return RetrofitAdapter.getRetrofitClient()
                .register(user)
                .subscribeOn(Schedulers.io());
    }
}
