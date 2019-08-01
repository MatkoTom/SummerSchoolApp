package com.example.summerschoolapp.view.login.login;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.network.retrofit.RetrofitAdapter;
import com.example.summerschoolapp.utils.Preferences;
import com.example.summerschoolapp.view.login.onboarding.OnboardingViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "STASEDOGADJA";

    private OnFragmentLoginClickListener listener;
    private OnFragmentLoginNextActivity loginListener;

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

    private OnboardingViewModel viewModel;

    private ColorStateList oldColor;
    private boolean isVisible = false;
    private boolean isValidMail = false;
    private boolean isValidPassword = false;

    private Preferences preferences;

    public interface OnFragmentLoginClickListener {
        void onLoginItemClicked();
    }

    public interface OnFragmentLoginNextActivity {
        void onLoginClicked();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        preferences = new Preferences(getActivity());
        fetchStoredUser();
        canUserLogIn();
        textChangedListener();
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
        if (etPassword.length() == 0 || !(etPassword.getText().toString().equals(preferences.getPassword()))) {
            tvLoginPassword.setTextColor(Color.RED);
            tvWrongPassword.setText("Kriva lozinka!");
            tvWrongPassword.setTextColor(Color.RED);
        } else {
            isValidPassword = true;

        }

        if (!isValidEmail(etEmail.getText().toString().trim()) || !(etEmail.getText().toString().equals(preferences.getEmail()))) {
            tvLoginMail.setTextColor(Color.RED);
            tvWrongEmail.setText("Korisnik ne postoji!");
            tvWrongEmail.setTextColor(Color.RED);
        } else {
            isValidMail = true;
        }

        if (isValidPassword && isValidMail) {
            loginListener.onLoginClicked();
            sendUserData();
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
                canUserLogIn();
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
                canUserLogIn();
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
        if (!preferences.getEmail().equals("") && !preferences.getPassword().equals("")) {
            etEmail.setText(preferences.getEmail());
            etPassword.setText(preferences.getPassword());
        }
    }

    private void canUserLogIn() {
        if (!isValidEmail(etEmail.getText().toString().trim()) ||
                !(etEmail.getText().toString().equals(preferences.getEmail())) ||
                !(etPassword.getText().toString().equals(preferences.getPassword()))) {
            btnLogin.setEnabled(false);
            btnLogin.setAlpha(0.5f);
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setAlpha(1.0f);
        }
    }

    private void sendUserData() {
        Map<String, String> userData = new HashMap<>();
        userData.put("title", etEmail.getText().toString().trim());
        userData.put("text", etPassword.getText().toString());

        Call<User> call = RetrofitAdapter.getRequestAPI().createUser(userData);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userResponse = response.body();

                Log.d(TAG, "onResponse: " + response.code() + "  " + userResponse.getEmail() + " " + userResponse.getPassword());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
