package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.RegisterError;
import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.repositories.AuthorisationRepository;
import com.example.summerschoolapp.utils.JWTUtils;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.Event;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class OnboardingViewModel extends BaseViewModel {
    private AuthorisationRepository authRepo;

    public OnboardingViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthorisationRepository();
    }

    public void makeLogin(RequestLogin user) {

        startProgress();
        authRepo.postLoginQuery(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
                    @Override
                    public void onSuccess(BigDataResponse user) {
                        Timber.d("BigUserResponse: %s", user.toString());
                        Tools.getSharedPreferences(getApplication()).saveUserToPreferences(user);
                        stopProgress();
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopProgress();
                        dispose();
                    }
                });
    }


    //TODO create error package, make loginerror class and implement code here, in fragments, and activity hosting fragments
    public void registerUser(RequestRegister user) {
        startProgress();
        authRepo.postRegisterQuery(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
                    @Override
                    public void onSuccess(BigDataResponse newUser) {
                        try {
                            Timber.d("Big response: %s", newUser.data.user.getJwt());
                            Tools.getSharedPreferences(getApplication()).saveUserToPreferences(newUser);
                            String userJWT = newUser.data.user.getJwt();
                            JWTUtils.decoded(userJWT);
                        } catch (Throwable e) {
                            Timber.e("Error: " + newUser.data.error.getError_code() + " " + newUser.data.error.getError_description());
                            onError(e);
                            Timber.e(e.toString());
                        }
                        stopProgress();
                        dispose();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.d("Failed: %s", throwable.toString());

                        BaseError error;
                        if (throwable instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
                            String extraInfo = responseBody.toString();
                            error = RegisterError.Create(RegisterError.Error.SOMETHING_WENT_WRONG);
                            error.setExtraInfo(extraInfo);
                        } else if (throwable instanceof SocketTimeoutException) {
                            error = RegisterError.Create(RegisterError.Error.SOMETHING_WENT_WRONG);
                        } else if (throwable instanceof IOException) {
                            error = RegisterError.Create(RegisterError.Error.SOMETHING_WENT_WRONG);
                        } else {
                            error = RegisterError.Create(RegisterError.Error.ERROR_WHILE_REGISTERING);
                            error.setExtraInfo(throwable.getMessage());
                        }

                        stopProgress();
                        getBaseErrors().setValue(new Event<>(error));
                        Timber.d("Error: %s", error.toString());

                        stopProgress();
                        dispose();
                    }
                });
    }
}
