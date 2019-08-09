package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.model.Data;
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

    public LiveData<User> makeLogin(RequestLogin user) {
        return authRepo.postLoginQuery(user);
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
                .subscribe(new DisposableSingleObserver<Data>() {
                    @Override
                    public void onSuccess(Data data) {
                        Timber.d("BigUserResponse: %s", data.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Failed: %s", e.toString());
                    }
                });
    }
}
