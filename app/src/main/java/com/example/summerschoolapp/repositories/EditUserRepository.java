package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editUser.RequestEditUser;
import com.example.summerschoolapp.model.editUser.ResponseEditUser;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserRepository {

    public EditUserRepository() {
    }

    public Single<ResponseEditUser> editUser(String id, String oib, String firstName, String lastName, String email, String password, String token, MultipartBody.Part photo) {
        return RetrofitAdapter.getRetrofitClient()
                .editUser(token, id, oib, firstName, lastName, email, password, photo)
                .subscribeOn(Schedulers.io());
    }
}
