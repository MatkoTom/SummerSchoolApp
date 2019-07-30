package com.example.summerschoolapp.view.login.signup;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.main.MainScreenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    @BindView(R.id.et_signup_password)
    EditText etPassword;

    @BindView(R.id.et_signup_email)
    EditText etEmail;

    @BindView(R.id.et_signup_oib)
    EditText etOib;

    @BindView(R.id.tv_email_in_use)
    TextView tvEmailInUse;

    @BindView(R.id.tv_signup_mail)
    TextView tvSignupMail;

    @BindView(R.id.tv_signup_oib)
    TextView tvSignupOib;

    @BindView(R.id.tv_oib_in_use)
    TextView tvOibInUse;

    OnSignupFragmentClicListener listener;

    private Boolean isVisible = false;
    private boolean isValidMail = false;
    private boolean isValidOib = false;

    public interface OnSignupFragmentClicListener {
        void onSignupItemClicked();
    }

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (OnSignupFragmentClicListener) context;
    }

    @OnClick(R.id.ibtn_hide_show)
    public void hideShowPassword() {
        if (!isVisible) {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isVisible = true;
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isVisible = false;
        }
    }

    @OnClick(R.id.tv_login)
    public void goToLogin() {
        listener.onSignupItemClicked();
    }

    @OnClick(R.id.btn_signup)
    public void signUpUser() {

        if (!isValidEmail(etEmail.getText().toString().trim())) {
            tvEmailInUse.setTextColor(Color.RED);
            tvEmailInUse.setText("Nije email!");
            tvSignupMail.setTextColor(Color.RED);
        } else {
            isValidMail = true;
        }

        if (etOib.length() == 0) {
            tvSignupOib.setTextColor(Color.RED);
            tvOibInUse.setTextColor(Color.RED);
            tvOibInUse.setText("OIB se već koristi");
        } else {
            isValidOib = true;
        }

        if (isValidOib && isValidMail) {
            Intent i = new Intent(getActivity(), MainScreenActivity.class);
            startActivity(i);
        }
    }

    private static boolean isValidEmail(CharSequence target) {  // Email validator, checks if field has correct input
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
