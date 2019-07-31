package com.example.summerschoolapp.dialog;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class BaseDialog extends AlertDialog {

    protected BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}