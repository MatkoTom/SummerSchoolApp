package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.repositories.MainScreenRepository;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import timber.log.Timber;

public class UsersFragmentViewModel extends BaseViewModel {

    private SingleLiveEvent<List<User>> sendList = new SingleLiveEvent<>();

    private MainScreenRepository mainRepo;

    public UsersFragmentViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainScreenRepository();
    }

    public SingleLiveEvent<List<User>> getRecyclerList() {
        return sendList;
    }


    public void getUserList(String token) {
        startProgress();
        mainRepo.getUserList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseUsersList>() {
                    @Override
                    public void onSuccess(ResponseUsersList response) {
                        Timber.d("createdNewUser%s", response.data.user);
                        getRecyclerList().setValue(response.data.user);
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

    public void getSearchedUsersList(String token, RequestBody body) {
        startProgress();
        mainRepo.getSearchedUsersList(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseUsersList>() {
                    @Override
                    public void onSuccess(ResponseUsersList response) {
                        Timber.d("SearchedUsers: %s", response.data.user);
                        getRecyclerList().setValue(response.data.user);
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

    public void printUsers() {
        getUserList(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt());
    }

    public void printUsersSearched(RequestBody body) {
        getSearchedUsersList(Tools.getSharedPreferences(getApplication()).getSavedUserData().getJwt(), body);
    }
}
