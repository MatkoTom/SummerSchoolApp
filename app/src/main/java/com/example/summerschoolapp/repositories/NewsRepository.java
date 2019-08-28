package com.example.summerschoolapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.summerschoolapp.database.RoomDb;
import com.example.summerschoolapp.database.dao.NewsTableDao;
import com.example.summerschoolapp.database.entity.NewsArticle;
import com.example.summerschoolapp.model.editNews.ResponseEditNews;
import com.example.summerschoolapp.model.newNews.ResponseNewNews;
import com.example.summerschoolapp.model.newsList.ResponseNewsList;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class NewsRepository {

    private NewsTableDao tableDao;
    private LiveData<List<NewsArticle>> newsList;

    public NewsRepository(Application application) {
        RoomDb database = RoomDb.getInstance(application);
        tableDao = database.newsTableDao();
        newsList = tableDao.selectAllNews();
    }

    public LiveData<List<NewsArticle>> getNewsList() {
        return newsList;
    }

    public void insert (NewsArticle article) {
        new InsertAsyncTask(tableDao).execute(article);
    }

    private static class InsertAsyncTask extends AsyncTask<NewsArticle, Void, Void> {

        private NewsTableDao mAsyncTaskDao;

        InsertAsyncTask(NewsTableDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsArticle... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

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

    public Single<ResponseEditNews> editNews(String token, int id, RequestBody body) {
        return RetrofitAdapter.getRetrofitClient()
                .editNews(token, body, id)
                .subscribeOn(Schedulers.io());
    }
}
