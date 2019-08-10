package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<User> login(@Body RequestLogin user);

//    @POST(Const.Network.API_REGISTER)
//    Flowable<Data> register(@Body RequestRegister register);

    @POST(Const.Network.API_REGISTER)
    Single<User> register(@Body RequestRegister register);

    @POST(Const.Network.API_CREATE_NEW_USER)
    Single<User> createNew(@Body RequestNewUser requestNew);
}
