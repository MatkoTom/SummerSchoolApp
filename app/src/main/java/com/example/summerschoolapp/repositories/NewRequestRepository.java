package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.newRequest.RequestNewRequest;
import com.example.summerschoolapp.model.newRequest.ResponseNewRequest;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewRequestRepository {

    public NewRequestRepository() {
    }

    public Single<ResponseNewRequest> createNewRequest(String token, RequestNewRequest requestNewRequest) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewRequest(token, requestNewRequest)
                .subscribeOn(Schedulers.io());
    }
}
