package com.example.summerschoolapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.summerschoolapp.R;

public class InsertTextDialog extends DialogFragment {

    private DialogCallback dialogCallback;

    public void setDialogCallback(DialogCallback setDialogCallback) {
      this.dialogCallback = setDialogCallback;
    }

    public static void CreateInstance(Activity activity, String title, @NonNull String positiveButton, @NonNull String negativeButton, InsertTextDialog.DialogCallback callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.text_dialog, null);
        EditText etChangeData = v.findViewById(R.id.et_change_data);
        builder.setView(v);
        builder.setTitle(title);
        builder.setPositiveButton(positiveButton, (dialogInterface, i) -> {
                callback.onDialogCallbackFinished(etChangeData.getText().toString());
        });

        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton, (dialogInterface, i) -> {

            });
        }

        builder.create().show();
    }

    public interface DialogCallback {
        void onDialogCallbackFinished(String text);
    }
}
