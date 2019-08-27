package com.example.summerschoolapp.repositories;

import com.example.summerschoolapp.model.editNews.ResponseEditNews;
import com.example.summerschoolapp.model.newNews.ResponseNewNews;
import com.example.summerschoolapp.model.newsList.ResponseNewsList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewsRepository {

    public NewsRepository() {}

    public Single<ResponseNewNews> createNewNews(String token, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewNews(token, body)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseNewsList> fetchNewsList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchNewsList(token)
                .subscribeOn(Schedulers.io());
    }

    public Single<ResponseEditNews> editNews(String token, int id, MultipartBody.Part body) {
        return RetrofitAdapter.getRetrofitClient()
                .editNews(token, body, id)
                .subscribeOn(Schedulers.io());
    }
}
