package com.example.summerschoolapp.view.main.fragmentProfile;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.AuthError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.editProfile.ResponseEditProfile;
import com.example.summerschoolapp.repositories.MainScreenRepository;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.Event;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class ProfileFragmentViewModel extends BaseViewModel {

    public enum Navigation {
        LOGIN
    }

    public enum Response {
        OK
    }

    private SingleLiveEvent<ProfileFragmentViewModel.Navigation> navigation = new SingleLiveEvent<>();
    private SingleLiveEvent<ProfileFragmentViewModel.Response> response = new SingleLiveEvent<>();

    private MainScreenRepository mainRepo;

    public ProfileFragmentViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainScreenRepository();
    }

    public SingleLiveEvent<Navigation> getNavigation() {
        return navigation;
    }
    public SingleLiveEvent<Response> getResponse() {
        return response;
    }

    public void logout(String token) {
        startProgress();
        mainRepo.logout(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Object>() {
                    @Override
                    public void onSuccess(Object s) {
                        stopProgress();
                        Timber.d("Successfully logged out!");
                        Tools.getSharedPreferences(getApplication()).setRememberMeStatus(false);
                        getNavigation().setValue(ProfileFragmentViewModel.Navigation.LOGIN);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable throwable) {
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

    public void editProfile(String token, RequestBody body) {
        startProgress();
        mainRepo.editProfile(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseEditProfile>() {
                    @Override
                    public void onSuccess(ResponseEditProfile responseEditProfile) {
                        stopProgress();
                        Timber.d("Successfully logged out!");
                        Tools.getSharedPreferences(getApplication()).setRememberMeStatus(false);
                        getResponse().setValue(Response.OK);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable throwable) {
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

    public void logoutUser() {
        logout(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt());
    }

    public void editUserProfile(RequestBody body) {
        editProfile(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt(), body);
    }
}
