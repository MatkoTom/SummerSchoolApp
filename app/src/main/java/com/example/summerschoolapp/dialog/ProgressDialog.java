package com.example.summerschoolapp.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseDialog;
import com.example.summerschoolapp.utils.CustomAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressDialog extends BaseDialog {

    public static ProgressDialog ShowProgressDialog(Context context, boolean isCancelable) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(isCancelable);

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }

        return progressDialog;
    }

    @BindView(R.id.tv_status)
    TextView tvStatus;

    public ProgressDialog(Context context) {
        super(context, R.style.Theme_Dialog_Dim);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
    }

    public void handleStatus(final String text) {

        if (TextUtils.isEmpty(text) && tvStatus.getVisibility() == View.VISIBLE) {

            CustomAnimationUtils.fadeOut(tvStatus, 100, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    tvStatus.setVisibility(View.INVISIBLE);
                }
            });

        } else {

            if (tvStatus.getVisibility() == View.VISIBLE) {

                CustomAnimationUtils.fadeOut(tvStatus, 100, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        tvStatus.setVisibility(View.INVISIBLE);
                        tvStatus.setText(text);

                        CustomAnimationUtils.fadeIn(tvStatus, 100, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                tvStatus.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

            } else {

                CustomAnimationUtils.fadeIn(tvStatus, 100, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);

                        tvStatus.setText(text);
                        tvStatus.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

}