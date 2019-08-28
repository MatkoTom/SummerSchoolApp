package com.example.summerschoolapp;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.room.Room;

import com.example.summerschoolapp.database.RoomDb;
import com.example.summerschoolapp.utils.helpers.ReleaseTree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class MyCityApplication extends Application implements LifecycleObserver {

    public static RoomDb database;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
                    return super.createStackElementTag(element) + " : " + element.getLineNumber();
                }
            });
        } else {
            Timber.plant(new ReleaseTree());
        }

        database = Room.databaseBuilder(getApplicationContext(), RoomDb.class, "RoomDb")
                .build();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAppResumed() {
        // app came to foreground
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onAppPaused() {
        // app went to background
    }
}
