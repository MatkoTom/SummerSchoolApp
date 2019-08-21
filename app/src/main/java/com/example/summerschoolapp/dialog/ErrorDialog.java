package com.example.summerschoolapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.summerschoolapp.R;

public class ErrorDialog extends DialogFragment {

    public static void CreateInstance(Activity activity, String title, String description, @NonNull String positiveButton, String negativeButton, OnErrorDialogInteraction listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setIcon(activity.getResources().getDrawable(R.drawable.log_in_error_icon))
                .setMessage(description)
                .setPositiveButton(positiveButton, (dialogInterface, i) -> {
                    if (listener != null) {
                        listener.onPositiveInteraction();
                    }
                });

        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton, (dialogInterface, i) -> {
                if (listener != null) {
                    listener.onNegativeInteraction();
                }
            });
        }

        builder.create().show();
    }

    public interface OnErrorDialogInteraction {
        void onPositiveInteraction();

        void onNegativeInteraction();
    }
}
