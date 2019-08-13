package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.model.RequestSignup;
import com.example.summerschoolapp.model.ResponseLogin;
import com.example.summerschoolapp.model.ResponseNewUser;
import com.example.summerschoolapp.model.ResponseSignup;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Network.API_SIGNUP)
    Single<ResponseSignup> register(@Body RequestSignup register);

    @POST(Const.Network.API_CREATE_NEW_USER)
    Single<ResponseNewUser> createNew(@Header ("token") String token,
                                      @Body RequestNewUser requestNew);
}
