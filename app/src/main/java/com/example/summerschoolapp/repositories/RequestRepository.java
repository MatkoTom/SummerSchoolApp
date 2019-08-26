package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editRequest.ResponseEditRequest;
import com.example.summerschoolapp.model.newRequest.ResponseNewRequest;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class RequestRepository {

    public RequestRepository() {
    }

    public Single<ResponseNewRequest> createNewRequest(String token, RequestBody requestBody) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewRequest(token, requestBody)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseEditRequest> editRequest(String token, String id, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .editRequest(token, id,  body)
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

    public Single<ResponseRequestList> getSearchedRequestListAdmin(String token, String searchQuerry) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchFilteredRequestAdmin(token, searchQuerry)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseRequestList> fetchAdminList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchRequestListAdmin(token)
                .subscribeOn(Schedulers.io());
    }
}
