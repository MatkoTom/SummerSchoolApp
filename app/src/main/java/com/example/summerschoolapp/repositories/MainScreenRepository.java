package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainScreenRepository {

    public MainScreenRepository() {

    }

    public Single<ResponseUsersList> getUserList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchUserList(token)
                .subscribeOn(Schedulers.io());
    }
}
