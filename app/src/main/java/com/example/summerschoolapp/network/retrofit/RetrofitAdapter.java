package com.example.summerschoolapp.network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAdapter {
    public static final String URL = "";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RetrofitAPI requestAPI = retrofit.create(RetrofitAPI.class);

    public static RetrofitAPI getRequestAPI() {
        return  requestAPI;
    }

}
