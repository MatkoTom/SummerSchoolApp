package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.newRequest.RequestNewRequest;
import com.example.summerschoolapp.model.newRequest.ResponseNewRequest;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RequestRepository {

    public RequestRepository() {
    }

    public Single<ResponseNewRequest> createNewRequest(String token, RequestNewRequest requestNewRequest) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewRequest(token, requestNewRequest)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseNewRequest> editRequest(String token, RequestNewRequest requestNewRequest) {
        return RetrofitAdapter.getRetrofitClient()
                .editRequest(token, requestNewRequest)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseRequestList> fetchList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchRequestList(token)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseRequestList> getSearchedRequestList(String token, String searchQuerry) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchFilteredRequest(token, searchQuerry)
                .subscribeOn(Schedulers.io());
    }
}
