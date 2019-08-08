package com.example.summerschoolapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;

import com.example.summerschoolapp.R;

public class ErrorDialog extends DialogFragment {

    public static void CreateInstance(Activity activity, String title, String description, String positiveButton) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setIcon(activity.getResources().getDrawable(R.drawable.log_in_error_icon))
                .setMessage(description)
                // TODO @Matko
                // implement a listener interface
                .setPositiveButton(positiveButton, (dialogInterface, i) -> {

                });
        builder.create().show();
    }
}
