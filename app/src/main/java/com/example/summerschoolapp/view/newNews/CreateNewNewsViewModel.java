package com.example.summerschoolapp.view.newNews;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.AuthError;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.newNews.ResponseNewNews;
import com.example.summerschoolapp.repositories.NewsRepository;
import com.example.summerschoolapp.repositories.RequestRepository;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.Event;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;
import com.example.summerschoolapp.view.newRequest.CreateNewRequestViewModel;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class CreateNewNewsViewModel extends BaseViewModel {

    public enum Navigation {
        MAIN
    }

    private SingleLiveEvent<CreateNewNewsViewModel.Navigation> navigation = new SingleLiveEvent<>();

    private NewsRepository newsRepository;

    public CreateNewNewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository();
    }

    public SingleLiveEvent<CreateNewNewsViewModel.Navigation> getNavigation() {
        return navigation;
    }

    public void createNewNews(String token, RequestBody body) {
        startProgress();
        newsRepository.createNewNews(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseNewNews>() {
                    @Override
                    public void onSuccess(ResponseNewNews responseNewNews) {
                        stopProgress();
                        if (responseNewNews.data.getMessage().equals(responseNewNews.data.message)) {
                            Timber.d("createdNewUser");
                            getNavigation().setValue(CreateNewNewsViewModel.Navigation.MAIN);
                        } else {
                            getBaseErrors().setValue(new Event<>(NewUserError.Create(NewUserError.Error.SOMETHING_WENT_WRONG)));

                        }
                        dispose();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.d("Failed: %s", throwable.toString());

                        BaseError error;
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).response().code() == 401) {
                                error = AuthError.Create(AuthError.Error.UNATUHORISED);
                            } else {
                                ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
                                String extraInfo = responseBody.toString();
                                error = SignupError.Create(SignupError.Error.SOMETHING_WENT_WRONG);
                                error.setExtraInfo(extraInfo);
                            }
                        } else if (throwable instanceof SocketTimeoutException) {
                            error = SignupError.Create(SignupError.Error.SOMETHING_WENT_WRONG);
                        } else if (throwable instanceof IOException) {
                            error = SignupError.Create(SignupError.Error.SOMETHING_WENT_WRONG);
                        } else {
                            error = SignupError.Create(SignupError.Error.SOMETHING_WENT_WRONG);
                            error.setExtraInfo(throwable.getMessage());
                        }

                        stopProgress();
                        getBaseErrors().setValue(new Event<>(error));
                        Timber.d("Error: %s", error.toString());

                        dispose();
                    }
                });
    }

    public void postNewNews(RequestBody body) {
        createNewNews(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt(), body);
    }
}
