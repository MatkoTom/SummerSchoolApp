package com.example.summerschoolapp.view.newUser;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.newuser.RequestNewUser;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.repositories.UserRepository;
import com.example.summerschoolapp.utils.Const;
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

public class CreateNewUserViewModel extends BaseViewModel {

    public enum Navigation {
        MAIN
    }

    private SingleLiveEvent<CreateNewUserViewModel.Navigation> navigation = new SingleLiveEvent<>();

    private UserRepository newUserRepo;

    public CreateNewUserViewModel(@NonNull Application application) {
        super(application);
        newUserRepo = new UserRepository();
    }

    public SingleLiveEvent<CreateNewUserViewModel.Navigation> getNavigation() {
        return navigation;
    }

    public void createNewUser(RequestNewUser newUser, String token) {

        startProgress();
        newUserRepo.postNewUser(newUser, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseNewUser>() {
                    @Override
                    public void onSuccess(ResponseNewUser newResponse) {
                        stopProgress();
                        if (newResponse.data.error == null) {
                            Timber.d("createdNewUser");
                            getNavigation().setValue(Navigation.MAIN);
                        } else {
                            Timber.d("Big response: %s", newResponse.data.error.getError_code() + " " + newResponse.data.error.getError_description());
                            if (Const.Errors.EMAIL_IN_USE == Integer.parseInt(newResponse.data.error.getError_code())) {
                                getBaseErrors().setValue(new Event<>(NewUserError.Create(NewUserError.Error.ERROR_WHILE_REGISTERING_EMAIL_IN_USE)));
                            } else if (Const.Errors.OIB_IN_USE == Integer.parseInt(newResponse.data.error.getError_code())) {
                                getBaseErrors().setValue(new Event<>(NewUserError.Create(NewUserError.Error.ERROR_WHILE_REGISTERING_OIB_IN_USE)));
                            } else {
                                getBaseErrors().setValue(new Event<>(NewUserError.Create(NewUserError.Error.SOMETHING_WENT_WRONG)));
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