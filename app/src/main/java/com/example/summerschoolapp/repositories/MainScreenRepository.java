package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editProfile.ResponseEditProfile;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class MainScreenRepository {

    public MainScreenRepository() {
    }

    public Single<ResponseUsersList> getUserList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchUserList(token)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseUsersList> getSearchedUsersList(String token, String query) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchSearchedUsers(token, query)
                .subscribeOn(Schedulers.io());
    }

    public Single<Object> logout(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .logout(token)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseEditProfile> editProfile(String token, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .editProfile(token, body)
                .subscribeOn(Schedulers.io());
    }
}
