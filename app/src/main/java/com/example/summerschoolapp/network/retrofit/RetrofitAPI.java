package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Const;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    // TODO @Matko
    // move endpoints to Const class
    @GET("/posts")
    Flowable<List<User>> getSomething();

    @POST(Const.Network.API_LOGIN)
    Flowable<User> login(@Body RequestLogin user);

    @POST(Const.Network.API_REGISTER)
    Flowable<User> register(@Body RequestRegister register);
}
