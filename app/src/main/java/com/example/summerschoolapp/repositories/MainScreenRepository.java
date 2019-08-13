package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainScreenRepository {

    public MainScreenRepository() {

    }

    public Single<BigDataResponse> getUserList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchUserList(token)
                .subscribeOn(Schedulers.io());
    }
}
