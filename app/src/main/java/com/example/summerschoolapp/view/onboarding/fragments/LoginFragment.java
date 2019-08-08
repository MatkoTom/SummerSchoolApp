package com.example.summerschoolapp.view.onboarding.fragments;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.onboarding.OnboardingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "STASEDOGADJA";

    @BindView(R.id.et_signup_password)
    EditText etPassword;

    @BindView(R.id.et_signup_email)
    EditText etEmail;

    @BindView(R.id.tv_wrong_password)
    TextView tvWrongPassword;

    @BindView(R.id.tv_login_mail)
    TextView tvLoginMail;

    @BindView(R.id.tv_wrong_email)
    TextView tvWrongEmail;

    @BindView(R.id.tv_login_password)
    TextView tvLoginPassword;

    @BindView(R.id.ibtn_hide_show)
    ImageButton ibtnHideShow;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.btn_remember_me)
    Button btnRememberMe;

    private OnboardingViewModel viewModel;

    private OnFragmentLoginClickListener listener;
    private OnFragmentLoginNextActivity loginListener;

    private ColorStateList oldColor;
    private boolean isVisible = false;
    private boolean isValidMail = false;
    private boolean isValidPassword = false;
    private boolean isPressed = false;

    public interface OnFragmentLoginClickListener {
        void onLoginItemClicked();
    }

    public interface OnFragmentLoginNextActivity {
        void onLoginClicked(RequestLogin user);
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
//        fetchStoredUser();
//        canUserLogIn();
        textChangedListener();
        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel.class);
        oldColor = tvLoginMail.getTextColors();
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (OnFragmentLoginClickListener) context;
        loginListener = (OnFragmentLoginNextActivity) context;
    }

    @OnClick(R.id.btn_login)
    public void logInUser() {
        if (etPassword.length() == 0) {
            tvLoginPassword.setTextColor(Color.RED);
            tvWrongPassword.setText(getString(R.string.wrong_password));
            tvWrongPassword.setTextColor(Color.RED);
            isValidPassword = false;
        } else {
            isValidPassword = true;

        }

        if (!isValidEmail(etEmail.getText().toString().trim())) {
            tvLoginMail.setTextColor(Color.RED);
            tvWrongEmail.setText(getString(R.string.user_doesnt_exist));
            tvWrongEmail.setTextColor(Color.RED);
            isValidMail = false;
        } else {
            isValidMail = true;
        }

        if (isValidPassword && isValidMail) {
            logInUserData();
            loginListener.onLoginClicked(logInUserData());
        }
    }

    @OnClick(R.id.tv_register)
    public void registerUser() {
        listener.onLoginItemClicked();
    }

    @OnClick(R.id.ibtn_hide_show)
    public void hideShowPassword() {
        if (!isVisible) {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ibtnHideShow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_icon));
            isVisible = true;
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ibtnHideShow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_hiden_icon));
            isVisible = false;
        }
    }

    private void textChangedListener() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                canUserLogIn();
                tvLoginMail.setTextColor(oldColor);
                tvWrongEmail.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                canUserLogIn();
                tvLoginPassword.setTextColor(oldColor);
                tvWrongPassword.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private static boolean isValidEmail(CharSequence target) {  // Email validator, checks if field has correct input
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void fetchStoredUser() {

    }

    private void canUserLogIn() {
        if (!isValidEmail(etEmail.getText().toString().trim())) {
            btnLogin.setEnabled(false);
            btnLogin.setAlpha(0.5f);
            btnRememberMe.setEnabled(false);
            btnRememberMe.setAlpha(0.5f);
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setAlpha(1.0f);
            btnRememberMe.setEnabled(true);
            btnRememberMe.setAlpha(1.0f);
        }
    }

    private RequestLogin logInUserData() {
        RequestLogin user = new RequestLogin();
        user.email = etEmail.getText().toString();
        user.password = Tools.md5(etPassword.getText().toString());

        return user;
    }

    @OnClick(R.id.btn_remember_me)
    public void rememberMeButton() {
        if (!isPressed) {
            btnRememberMe.setText(R.string.forget_me);
            // TODO @Matko

            btnRememberMe.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.remember_me_x_icon), null);
            isPressed = true;
        } else {
            btnRememberMe.setText(R.string.remember_me);

            btnRememberMe.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.remember_me_checkmark_icon), null);
            isPressed = false;
        }
    }
}
