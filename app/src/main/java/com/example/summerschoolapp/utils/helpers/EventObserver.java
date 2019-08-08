package com.example.summerschoolapp.utils.helpers;

import androidx.lifecycle.Observer;

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 * <p>
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
public class EventObserver<T> implements Observer<Event<T>> {

    public EventObserver() {
    }

    @Override
    public void onChanged(Event<T> event) {
        T value = event.getContentIfNotHandled();
        if (value != null) {
            onEventUnhandledContent(value);
        }
    }

    public void onEventUnhandledContent(T value) {
    }
}
