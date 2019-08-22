package com.example.summerschoolapp.view.newRequest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class RequestScrollAdapter extends ScrollView {

    private boolean enableScrolling = true;

    public boolean isScrollingEnabled() {
        return enableScrolling;
    }

    public void setEnableScrolling(boolean enableScrolling) {
        this.enableScrolling = enableScrolling;
    }

    public RequestScrollAdapter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RequestScrollAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RequestScrollAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isScrollingEnabled()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrollingEnabled()) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}
