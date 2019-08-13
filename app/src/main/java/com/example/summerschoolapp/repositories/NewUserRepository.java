package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewUserRepository {

    public NewUserRepository() {

    }

    public Single<BigDataResponse> postNewUser(RequestNewUser newUser) {
        return RetrofitAdapter.getRetrofitClient()
                .createNew(newUser)
                .subscribeOn(Schedulers.io());
    }
}
