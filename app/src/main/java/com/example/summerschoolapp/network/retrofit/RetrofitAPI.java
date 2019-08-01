package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @GET("/posts")
    Flowable<List<User>> getSomething();

    @FormUrlEncoded
    @POST("posts")
    Call<User> createUser(@FieldMap Map<String, String> parameters);
}
