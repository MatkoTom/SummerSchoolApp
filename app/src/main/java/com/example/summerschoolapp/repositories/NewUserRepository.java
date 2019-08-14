package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.newuser.RequestNewUser;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewUserRepository {

    public NewUserRepository() {

    }

    public Single<ResponseNewUser> postNewUser(RequestNewUser newUser, String token) {
        return RetrofitAdapter.getRetrofitClient()
                .createNew(token, newUser)
                .subscribeOn(Schedulers.io());
    }
}
