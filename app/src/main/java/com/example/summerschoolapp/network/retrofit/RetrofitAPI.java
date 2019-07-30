package com.example.summerschoolapp.network.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    @GET()
    Call<List<Object>> getSomething();
}
