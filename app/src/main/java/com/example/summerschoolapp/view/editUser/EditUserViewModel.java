package com.example.summerschoolapp.view.editUser;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.errors.AuthError;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.editUser.ResponseEditUser;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class EditUserViewModel extends BaseViewModel {

    public enum Navigation {
        MAIN
    }

    private SingleLiveEvent<EditUserViewModel.Navigation> navigation = new SingleLiveEvent<>();

    private UserRepository editUserRepo;

    public EditUserViewModel(@NonNull Application application) {
        super(application);
        editUserRepo = new UserRepository();
    }

    public SingleLiveEvent<Navigation> getNavigation() {
        return navigation;
    }

    public void editUser(String token, RequestBody body) {
        startProgress();
        editUserRepo.editUser(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseEditUser>() {
                    @Override
                    public void onSuccess(ResponseEditUser responseEditUser) {
                        stopProgress();
                        if (responseEditUser.data.error == null) {
                            Timber.d("createdNewUser");
                            getNavigation().setValue(EditUserViewModel.Navigation.MAIN);
                        } else {
                            Timber.d("Big ok: %s", responseEditUser.data.error.getError_code() + " " + responseEditUser.data.error.getError_description());
                            if (Const.Errors.EMAIL_IN_USE == Integer.parseInt(responseEditUser.data.error.getError_code())) {
                                getBaseErrors().setValue(new Event<>(NewUserError.Create(NewUserError.Error.ERROR_WHILE_REGISTERING_EMAIL_IN_USE)));
                            } else if (Const.Errors.OIB_IN_USE == Integer.parseInt(responseEditUser.data.error.getError_code())) {
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
}
