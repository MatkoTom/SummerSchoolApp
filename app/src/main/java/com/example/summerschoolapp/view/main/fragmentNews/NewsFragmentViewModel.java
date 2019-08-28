package com.example.summerschoolapp.view.main.fragmentNews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.database.entity.NewsArticle;
import com.example.summerschoolapp.model.News;
import com.example.summerschoolapp.model.newsList.ResponseNewsList;
import com.example.summerschoolapp.repositories.NewsRepository;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewsFragmentViewModel extends BaseViewModel {

    private NewsRepository newsRepository;

    private LiveData<List<NewsArticle>> newsList;

    private SingleLiveEvent<List<News>> sendNewsList = new SingleLiveEvent<>();

    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        newsList = newsRepository.getNewsList();

    }

    public LiveData<List<NewsArticle>> getAllNews() {
        return newsList;
    }

    public void insert(NewsArticle article) {
        newsRepository.insert(article);
    }

    public SingleLiveEvent<List<News>> getNewsList() {
        return sendNewsList;
    }

    public void fetchNewsList(String token) {
        startProgress();
        newsRepository.fetchNewsList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseNewsList>() {
                    @Override
                    public void onSuccess(ResponseNewsList responseNewsList) {
                        Timber.d("createdNewUser%s", responseNewsList.data.newsList);
                        getNewsList().setValue(responseNewsList.data.newsList);

                        //TODO check this with someone
//                        for (News news : responseNewsList.data.newsList) {
//                            insert(new NewsArticle(news.getId(), news.getTitle(), news.getMessage(), news.getFirstName(),
//                                    news.getLastName(), news.getLocation_latitude(), news.getLocation_longitude(), news.getAddress(),
//                                    news.getCreatedAt(), news.getUpdatedAt(), news.getImages(), news.getFiles()));
//                        }
                        stopProgress();
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Failed: %s", e.toString());
                        stopProgress();
                        dispose();
                    }
                });
    }

    public boolean isAdmin() {
        return Integer.parseInt(Tools.getSharedPreferences(getApplication()).getSavedUserData().getRole()) == Const.Preferences.ADMIN_ROLE;
    }

    public void printNewsItem() {
        fetchNewsList(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt());
    }
}
