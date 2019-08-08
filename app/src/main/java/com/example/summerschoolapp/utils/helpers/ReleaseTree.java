package com.example.summerschoolapp.utils.helpers;

import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

//TODO check out release tree
public class ReleaseTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(@Nullable String tag, int priority) {
        // Only log Warn, ERROR and WTF
        return priority != Log.DEBUG && priority != Log.INFO && priority != Log.VERBOSE;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (isLoggable(tag, priority)) {

			// we are not using this at the moment, provide more details about pricing in firebase
            // if (priority == Log.ERROR && t != null) {
            //    Crashlytics.log(priority, tag, message);
            // }

            // Message is short enough, does not have to be broken in chunks
            int MAX_LOG_LENGTH = 4000;
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Timber.wtf(message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            for (int i = 0, length = message.length(); i < length; i++) {
                int newLine = message.indexOf("\n", i);
                newLine = newLine != -1 ? newLine : length;
                do {
                    int end = Math.min(newLine, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Timber.wtf(part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newLine);
            }
        }
    }
}
