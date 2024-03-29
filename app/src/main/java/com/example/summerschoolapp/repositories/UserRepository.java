package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editUser.ResponseEditUser;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class UserRepository {

    public UserRepository() {
    }

    public Single<ResponseNewUser> postNewUser(String token, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewUser(token, body)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseEditUser> editUser(String token, RequestBody body, String id) {
        return RetrofitAdapter.getRetrofitClient()
                .editUser(token, body, id)
                .subscribeOn(Schedulers.io());
    }
}
