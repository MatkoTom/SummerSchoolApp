package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<BigDataResponse> login(@Body RequestLogin user);

//    @POST(Const.Network.API_REGISTER)
//    Flowable<Data> register(@Body RequestRegister register);

    @POST(Const.Network.API_REGISTER)
    Single<BigDataResponse> register(@Body RequestRegister register);

    @POST(Const.Network.API_CREATE_NEW_USER)
    Single<BigDataResponse> createNew(@Body RequestNewUser requestNew);
}
