package com.example.summerschoolapp.common;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.helpers.Event;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {
    private MutableLiveData<Const.ProgressStatus> progressStatus = new MutableLiveData<>();
    private MutableLiveData<Event<BaseError>> baseErrors = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Const.ProgressStatus> getProgressStatus() {
        return progressStatus;
    }

    public void startProgress() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            progressStatus.setValue(Const.ProgressStatus.START_PROGRESS);
        } else {
            progressStatus.postValue(Const.ProgressStatus.START_PROGRESS);
        }
    }

    public void stopProgress() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            progressStatus.setValue(Const.ProgressStatus.STOP_PROGRESS);
        } else {
            progressStatus.postValue(Const.ProgressStatus.STOP_PROGRESS);
        }
    }

    public MutableLiveData<Event<BaseError>> getBaseErrors() {
        return baseErrors;
    }

    // TODO @Matko
    // let's better use this method for reporting errors because it is thread safe
    public void reportError(Event<BaseError> error) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            baseErrors.setValue(error);
        } else {
            baseErrors.postValue(error);
        }
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
