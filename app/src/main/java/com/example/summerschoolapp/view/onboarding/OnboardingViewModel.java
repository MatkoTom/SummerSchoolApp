package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.ResponseLogin;
import com.example.summerschoolapp.model.ResponseSignup;
import com.example.summerschoolapp.repositories.AuthorisationRepository;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.Event;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class OnboardingViewModel extends BaseViewModel {

    public enum Navigation {
        MAIN, SIGNUP, LOGIN;
    }

    private SingleLiveEvent<OnboardingViewModel.Navigation> navigation = new SingleLiveEvent<>();

    private AuthorisationRepository authRepo;

    public OnboardingViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthorisationRepository();
    }

    public SingleLiveEvent<Navigation> getNavigation() {
        return navigation;
    }

    public void makeLogin(RequestLogin user) {

        startProgress();
        authRepo.postLoginQuery(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseLogin>() {
                    @Override
                    public void onSuccess(ResponseLogin user) {
                        Timber.d("BigUserResponse: %s", user.toString());
                        // TODO @Matko
//                        Tools.getSharedPreferences(getApplication()).saveUserToPreferences(user);
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

    public void registerUser(RequestRegister user) {
        startProgress();
        authRepo.postRegisterQuery(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseSignup>() {
                    @Override
                    public void onSuccess(ResponseSignup newResponse) {
                        stopProgress();
                        if (newResponse.data.error == null) {
                            Timber.d("Big response: %s", newResponse.data.user.getJwt());
                            Tools.getSharedPreferences(getApplication()).saveUserToPreferences(newResponse.data.user);
                            getNavigation().setValue(OnboardingViewModel.Navigation.MAIN);
                        } else {
                            Timber.d("Big response: %s", newResponse.data.error.getError_code() + " " + newResponse.data.error.getError_description());
                            if (Const.Errors.EMAIL_IN_USE == Integer.parseInt(newResponse.data.error.getError_code())) {
                                getBaseErrors().setValue(new Event<>(SignupError.Create(SignupError.Error.ERROR_WHILE_REGISTERING_EMAIL_IN_USE)));
                            } else if(Const.Errors.OIB_IN_USE == Integer.parseInt(newResponse.data.error.getError_code())) {
                                getBaseErrors().setValue(new Event<>(SignupError.Create(SignupError.Error.ERROR_WHILE_REGISTERING_OIB_IN_USE)));
                            }else {
                                getBaseErrors().setValue(new Event<>(SignupError.Create(SignupError.Error.SOMETHING_WENT_WRONG)));
                            }
                        }
                        dispose();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.d("Failed: %s", throwable.toString());

                        BaseError error;
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).response().code() == 401) {
                                error = SignupError.Create(SignupError.Error.UNATUHORISED);
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
}
