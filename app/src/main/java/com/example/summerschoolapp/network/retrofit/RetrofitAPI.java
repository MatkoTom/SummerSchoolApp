package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.ResponseLogin;
import com.example.summerschoolapp.model.ResponseNewUser;
import com.example.summerschoolapp.model.ResponseSignup;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Network.API_REGISTER)
    Single<ResponseSignup> register(@Body RequestRegister register);

    // TODO @Matko
    // implement response object
    @POST(Const.Network.API_CREATE_NEW_USER)
    Single<ResponseNewUser> createNew(@Body RequestNewUser requestNew);
}
