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

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.utils.Tools;

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

    @BindView(R.id.tv_signup_password)
    TextView tvSignupPassword;

    @BindView(R.id.tv_oib_in_use)
    TextView tvOibInUse;

    @BindView(R.id.tv_wrong_password)
    TextView tvWrongPassword;

    @BindView(R.id.ibtn_hide_show)
    ImageButton ibtnHideShow;

    @BindView(R.id.btn_signup)
    Button btnSignup;

    @BindView(R.id.btn_remember_me)
    Button btnRememberMe;

    private OnSignupFragmentClicListener listener;
    private OnSignupLogin loginListener;

    private boolean isVisible = false;
    private ColorStateList oldColor;
    private boolean isValidMail = false;
    private boolean isValidOib = false;
    private boolean isValidPassword = false;
    private boolean isPressed = false;

    public interface OnSignupFragmentClicListener {
        void onSignupItemClicked();
    }

    public interface OnSignupLogin {
        void onSignupClicked(RequestRegister user);
    }

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);
        oldColor = tvSignupOib.getTextColors();
        canUserSignup();
        textChangedListener();
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (OnSignupFragmentClicListener) context;
        loginListener = (OnSignupLogin) context;
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
            isValidMail = false;
        } else {
            isValidMail = true;
        }

        if (etOib.length() == 0 || etOib.length() > 11 || etOib.length() < 11) {
            tvSignupOib.setTextColor(Color.RED);
            tvOibInUse.setTextColor(Color.RED);
            tvOibInUse.setText("OIB se već koristi");
            isValidOib = false;
        } else {
            isValidOib = true;
        }

        if (etPassword.length() == 0) {
            tvSignupPassword.setTextColor(Color.RED);
            tvWrongPassword.setTextColor(Color.RED);
            tvWrongPassword.setText("Kriva lozinka");
            isValidPassword = false;
        } else {
            isValidPassword = true;
        }

        if (isValidOib && isValidMail && isValidPassword) {
            // TODO @Matko
            // saving to shared prefs is not necessary since we should store user object
            // delete after implementing the above
            Tools.getSharedPreferences(getActivity()).setEmail(etEmail.getText().toString());
            Tools.getSharedPreferences(getActivity()).setPassword(etPassword.getText().toString());
            loginListener.onSignupClicked(sendData());
        }
    }

    private void textChangedListener() {
        etOib.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canUserSignup();
                if (etOib.length() == 0 || etOib.length() > 11 || etOib.length() < 11) {
                    tvSignupOib.setTextColor(Color.RED);
                    tvOibInUse.setTextColor(Color.RED);
                    tvOibInUse.setText("OIB mora imati 11 znakova");
                } else {
                    tvSignupOib.setTextColor(oldColor);
                    tvOibInUse.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canUserSignup();
                if (!isValidEmail(etEmail.getText().toString().trim())) {
                    tvEmailInUse.setTextColor(Color.RED);
                    tvEmailInUse.setText("Nije email!");
                    tvSignupMail.setTextColor(Color.RED);
                } else {
                    tvSignupMail.setTextColor(oldColor);
                    tvEmailInUse.setText("");
                }

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
                canUserSignup();
                if (etPassword.length() == 0) {
                    tvSignupPassword.setTextColor(Color.RED);
                    tvWrongPassword.setTextColor(Color.RED);
                    tvWrongPassword.setText("Kriva lozinka");
                } else {
                    tvSignupPassword.setTextColor(oldColor);
                    tvWrongPassword.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private static boolean isValidEmail(CharSequence target) {  // Email validator, checks if field has correct input
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void canUserSignup() {
        if (!isValidEmail(etEmail.getText().toString().trim()) || etPassword.length() == 0 || etOib.length() < 11 || etOib.length() > 11) {
            btnSignup.setEnabled(false);
            btnSignup.setAlpha(0.5f);
            btnRememberMe.setEnabled(false);
            btnRememberMe.setAlpha(0.5f);
        } else {
            btnSignup.setEnabled(true);
            btnSignup.setAlpha(1.0f);
            btnRememberMe.setEnabled(true);
            btnRememberMe.setAlpha(1.0f);
        }
    }

    private RequestRegister sendData() {
        RequestRegister user = new RequestRegister();
        user.oib = etOib.getText().toString();
        user.email = etEmail.getText().toString();
        user.password = Tools.md5(etPassword.getText().toString());

        return user;
    }

    @OnClick(R.id.btn_remember_me)
    public void rememberMeButton() {
        if (!isPressed) {
            btnRememberMe.setText(getString(R.string.forget_me));
            // TODO @Matko
            // R.drawable.remember_me_x_icon is missing, I have the latest code
            // probably forgot to add it in git
//            btnRememberMe.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.remember_me_x_icon), null);
            isPressed = true;
        } else {
            btnRememberMe.setText(R.string.remember_me);
            // TODO @Matko
            // R.drawable.remember_me_checkmark_icon is missing, I have the latest code
            // probably forgot to add it in git
//            btnRememberMe.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.remember_me_checkmark_icon), null);
            isPressed = false;
        }
    }
}
