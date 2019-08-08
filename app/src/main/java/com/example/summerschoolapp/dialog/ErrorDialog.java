package com.example.summerschoolapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.fragment.app.DialogFragment;

import com.example.summerschoolapp.R;

public class ErrorDialog extends DialogFragment {

    public static void CreateInstance(Activity activity, String title, String description, String positiveButton) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setIcon(activity.getResources().getDrawable(R.drawable.log_in_error_icon))
                .setMessage(description)
                // TODO @Matko
                // lets avoid this kid of testing expression, they usually are left and forgotten causing a bug
                // implement a listener interface
                .setPositiveButton(positiveButton, (dialogInterface, i) -> activity.finish());
        builder.create().show();
    }
}
