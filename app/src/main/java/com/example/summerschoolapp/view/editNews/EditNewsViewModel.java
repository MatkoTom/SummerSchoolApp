package com.example.summerschoolapp.view.editNews;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.AuthError;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.editNews.ResponseEditNews;
import com.example.summerschoolapp.repositories.NewsRepository;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.Event;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class EditNewsViewModel extends BaseViewModel {

    public enum Navigation {
        MAIN
    }

    private SingleLiveEvent<EditNewsViewModel.Navigation> navigation = new SingleLiveEvent<>();

    private NewsRepository newsRepository;

    public EditNewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository();
    }

    public SingleLiveEvent<Navigation> getNavigation() {
        return navigation;
    }

    public void editNews(String token, int id, RequestBody body) {
        startProgress();
        newsRepository.editNews(token, id, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseEditNews>() {
                    @Override
                    public void onSuccess(ResponseEditNews responseEditNews) {
                        stopProgress();
                        if (responseEditNews.data.getMessage().equals(responseEditNews.data.message)) {
                            Timber.d("createdNewUser");
                            getNavigation().setValue(EditNewsViewModel.Navigation.MAIN);
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

    public void postEditNews(int id, RequestBody body) {
        editNews(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt(), id, body);
    }
}
