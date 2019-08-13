package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.BigDataResponse;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.repositories.MainScreenRepository;
import com.example.summerschoolapp.repositories.NewUserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UsersFragmentViewModel extends BaseViewModel {

    private MainScreenRepository mainRepo;

    public UsersFragmentViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainScreenRepository();
    }

    public void getUserList(String token) {
        startProgress();
        mainRepo.getUserList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
                    @Override
                    public void onSuccess(BigDataResponse response) {
                        Timber.d("createdNewUser%s", response.toString());
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
