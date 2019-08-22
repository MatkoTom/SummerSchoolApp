package com.example.summerschoolapp.view.newRequest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NewRequestScrollAdapter extends ScrollView {

    private boolean enableScrolling = true;

    public boolean isScrollingEnabled() {
        return enableScrolling;
    }

    public void setEnableScrolling(boolean enableScrolling) {
        this.enableScrolling = enableScrolling;
    }

    public NewRequestScrollAdapter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NewRequestScrollAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewRequestScrollAdapter(Context context) {
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
