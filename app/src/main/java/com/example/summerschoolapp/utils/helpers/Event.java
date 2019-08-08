package com.example.summerschoolapp.utils.helpers;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
public class Event<T> {

    private T content;
    private boolean isHandled = false;

    public boolean isHandled() {
        return isHandled;
    }

    public Event(T content) {
        this.content = content;
    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {
        if (isHandled) {
            return null;
        } else {
            isHandled = true;
            return content;
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public T peekContent() {
        return content;
    }
}
