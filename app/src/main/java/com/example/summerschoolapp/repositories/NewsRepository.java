package com.example.summerschoolapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.summerschoolapp.database.RoomDb;
import com.example.summerschoolapp.database.dao.NewsTableDao;
import com.example.summerschoolapp.database.entity.NewsArticle;
import com.example.summerschoolapp.model.News;
import com.example.summerschoolapp.model.editNews.ResponseEditNews;
import com.example.summerschoolapp.model.newNews.ResponseNewNews;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class NewsRepository {

    private NewsTableDao tableDao;
    private RoomDb dbInstance;

    public NewsRepository() {
    }

    public NewsRepository(Application application) {
        dbInstance = RoomDb.getInstance(application);
        tableDao = dbInstance.newsTableDao();
    }

    public LiveData<List<NewsArticle>> getNewsList() {
        return tableDao.selectAllNews();
    }

    // TODO Do I need this?
    // ignore
//    public void insert(NewsArticle article) {
//        new InsertAsyncTask(tableDao).execute(article);
//    }
//
//    private static class InsertAsyncTask extends AsyncTask<NewsArticle, Void, Void> {
//
//        private NewsTableDao mAsyncTaskDao;
//
//        InsertAsyncTask(NewsTableDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final NewsArticle... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }

    public Single<ResponseNewNews> createNewNews(String token, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .createNewNews(token, body)
                .subscribeOn(Schedulers.io());
    }

    public Single<Integer> fetchNewsList(String token) {
        return RetrofitAdapter.getRetrofitClient()
                .fetchNewsList(token)
                .subscribeOn(Schedulers.io())
                .flatMap(responseNewsList -> {
                    if (responseNewsList == null || responseNewsList.data == null || responseNewsList.data.newsList == null) {
                        return Single.error(new Throwable("e prazno je"));
                    } else {
                        List<NewsArticle> list = new ArrayList<>();
                        for (News news : responseNewsList.data.newsList) {
                            list.add(NewsArticle.convertToNewsArticle(news));
                        }
                        tableDao.insertList(list);
                        return Single.just(1);
                    }

                });
    }

    public Single<ResponseEditNews> editNews(String token, int id, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .editNews(token, body, id)
                .subscribeOn(Schedulers.io());
    }
}
