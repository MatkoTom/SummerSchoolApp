package com.example.summerschoolapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.summerschoolapp.dialog.ProgressDialog;


public class BaseActivity extends AppCompatActivity {

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

                progress = ProgressDialog.ShowProgressDialog(this, isCancelable);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MixpanelManager.initializeMixpanel(this);
//        MandatoryPermissions.handleMandatoryPermissions(this, null);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (!MandatoryPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(stopProgressFromError, InternetManager.INTERNET_FILTER);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        registerReceiver(InternetManager.INTERNET_RECEIVER, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(phoneUUIDChanged, new IntentFilter(Const.DbConsts.PHONE_UUID_CHANGED));
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        unregisterReceiver(InternetManager.INTERNET_RECEIVER);
//
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(phoneUUIDChanged);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        MixpanelManager.flushMixpanel();
//
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(stopProgressFromError);
//    }
//
//    private BroadcastReceiver phoneUUIDChanged = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ErrorDialog.ShowEditDialog(getString(R.string.multiple_phones_title), getString(R.string.multiple_phones_log_out), null, getString(R.string.ok), new ErrorDialog.ErrorDialogInteraction() {
//                @Override
//                public void onTopButtonClick() {
//                    // ignore
//                }
//
//                @Override
//                public void onBottomButtonClick() {
//                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                        Tools.logout(BaseActivity.this);
//                    }
//                }
//            }, BaseActivity.this);
//        }
//    };

}