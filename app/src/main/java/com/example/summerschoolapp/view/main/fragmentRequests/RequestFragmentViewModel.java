package com.example.summerschoolapp.view.main.fragmentRequests;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.summerschoolapp.common.BaseViewModel;
import com.example.summerschoolapp.model.Request;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.repositories.RequestRepository;
import com.example.summerschoolapp.utils.helpers.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RequestFragmentViewModel extends BaseViewModel {

    private RequestRepository requestRepository;

    private SingleLiveEvent<List<Request>> sendList = new SingleLiveEvent<>();

    public RequestFragmentViewModel(@NonNull Application application) {
        super(application);
        requestRepository = new RequestRepository();
    }

    public SingleLiveEvent<List<Request>> getRecyclerList() {
        return sendList;
    }

    public void fetchRequestList(String token) {
        startProgress();
        requestRepository.fetchList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ResponseRequestList>() {
                    @Override
                    public void onSuccess(ResponseRequestList response) {
                        Timber.d("createdNewUser%s", response.data.requestList);
                        getRecyclerList().setValue(response.data.requestList);
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
