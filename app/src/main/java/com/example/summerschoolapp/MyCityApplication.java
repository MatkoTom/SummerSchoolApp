package com.example.summerschoolapp;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

// TODO @Matko
// I added application class for general lib initialisation
// Question --> Do we need all those fonts, I think we are only using Avenir (normal) and American Typewriter
public class MyCityApplication extends Application implements LifecycleObserver {

    @Override
    public void onCreate() {
        super.onCreate();
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
