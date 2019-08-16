package com.example.summerschoolapp.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.dialog.ProgressDialog;

public abstract class BaseFragment extends Fragment {

    // start: global progress handle
    private ProgressDialog progress;
    private BroadcastReceiver stopProgressFromError = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideProgress();
        }
    };

    public void setProgressStatus(String text) {

        if (progress != null && progress.isShowing()) {
            progress.handleStatus(text);
        }
    }

    public void showProgress() {
        showProgress(true);
    }

    public void showProgress(boolean isCancelable) {

        try {

            if (progress == null || !progress.isShowing()) {

                progress = ProgressDialog.ShowProgressDialog(getActivity(), isCancelable);
                progress.show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void hideProgress() {

        try {

            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }

            progress = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // hide: global progress handle
}
