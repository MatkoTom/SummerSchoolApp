package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.repositories.MainScreenRepository;

public class UsersFragmentViewModel extends BaseViewModel {

    private MainScreenRepository mainRepo;

    public UsersFragmentViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainScreenRepository();
    }
//
//    public void getUserList(String token) {
//        startProgress();
//        mainRepo.getUserList(token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableSingleObserver<BigDataResponse>() {
//                    @Override
//                    public void onSuccess(BigDataResponse response) {
//                        Timber.d("createdNewUser%s", response.toString());
//                        stopProgress();
//                        dispose();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.d("Failed: %s", e.toString());
//                        stopProgress();
//                        dispose();
//                    }
//                });
//    }
}
