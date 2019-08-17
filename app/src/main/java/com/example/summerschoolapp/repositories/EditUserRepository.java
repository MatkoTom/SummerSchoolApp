package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editUser.RequestEditUser;
import com.example.summerschoolapp.model.editUser.ResponseEditUser;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EditUserRepository {

    public EditUserRepository() {
    }

    public Single<ResponseEditUser> editUser(RequestEditUser editUser, String token) {
        return RetrofitAdapter.getRetrofitClient()
                .editUser(token, editUser)
                .subscribeOn(Schedulers.io());
    }
}
