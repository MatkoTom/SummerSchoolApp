package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
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

    public Single<ResponseUsersList> getSearchedUsersList(String token, String searchQuery) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchSearchedUsers(token, searchQuery)
                .subscribeOn(Schedulers.io());
    }

    public Single<Object> logout(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .logout(token)
                .subscribeOn(Schedulers.io());
    }
}
