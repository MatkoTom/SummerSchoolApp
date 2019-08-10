package com.example.summerschoolapp.view.onboarding;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.repositories.AuthorisationRepository;
import com.example.summerschoolapp.utils.JWTUtils;

import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
                    @Override
                    public void onSuccess(BigDataResponse user) {
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
                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
                    @Override
                    public void onSuccess(BigDataResponse newUser) {
                        Timber.d("Big response: " + newUser.data.user.getEmail() + " " + newUser.data.user.getJwt());
                        try {
                            String userJWT = newUser.data.user.getJwt();
                            JWTUtils.decoded(userJWT);
                        } catch (Throwable e) {
                            Timber.e(e.toString());
                        }

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
