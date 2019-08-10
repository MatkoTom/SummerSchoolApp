package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.Data;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.repositories.AuthorisationRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
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
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Timber.d("BigUserResponse: %s", user.toString());
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

//    public LiveData<Data> registerUser(RequestRegister user) {
//        return authRepo.postRegisterQuery(user);
//    }


    //TODO create error package, make loginerror class and implement code here, in fragments, and activity hosting fragments
    public void registerUser(RequestRegister user) {
        startProgress();
        authRepo.postRegisterQuery(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User newUser) {
                        Timber.d("BigUserResponse: %s", newUser.toString());
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
}
