package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @GET("/posts")
    Flowable<List<User>> getSomething();

    @POST("login")
    Flowable<User> login(@Body RequestLogin user);

    @POST("register")
    Flowable<User> register(@Body RequestRegister register);
}
