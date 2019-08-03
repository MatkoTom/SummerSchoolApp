package com.example.summerschoolapp.view.login.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.view.login.login.LoginFragment;
import com.example.summerschoolapp.view.login.signup.SignupFragment;
import com.example.summerschoolapp.view.main.MainScreenActivity;

import butterknife.ButterKnife;

public class OnboardingActivity extends BaseActivity implements SignupFragment.OnSignupLogin, LoginFragment.OnFragmentLoginNextActivity, LoginFragment.OnFragmentLoginClickListener, SignupFragment.OnSignupFragmentClicListener, FirstLoginFragment.OnFirstLoginFragmentRegisterListener, FirstLoginFragment.OnFirstLoginFragmentLoginListener {

    private static final String TAG = "STASEDOGADJA";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private OnboardingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel.class);
        runFirstLoginFragment();
    }

    public void runFirstLoginFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.add(R.id.fragment_container, new FirstLoginFragment(), "firstLogin");
        transaction.addToBackStack("firstLogin");
        transaction.commit();
    }

    @Override
    public void onLoginItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new SignupFragment(), "register");
        transaction.addToBackStack("register");
        transaction.commit();
    }

    @Override
    public void onSignupItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new LoginFragment(), "login");
        transaction.commit();
    }

    @Override
    public void onFirstLoginItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new LoginFragment(), "login");
        transaction.commit();
    }

    @Override
    public void onFirstLoginRegister() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new SignupFragment(), "register");
        transaction.addToBackStack("register");
        transaction.commit();
    }

    @Override
    public void onLoginClicked(RequestLogin user) {
        Log.d(TAG, "onLoginClicked: " + user.password + " " + user.email);
        viewModel.makeLogin(user).observe(this, user1 -> Log.d(TAG, "onChanged: " + user1.getEmail() + " " + user1.getPassword()));
        showProgress();
        try {
            Intent i = new Intent(this, MainScreenActivity.class);
            finish();
            startActivity(i);
        } catch (Exception e) {
            ErrorDialog dialog = new ErrorDialog();
            dialog.show(getSupportFragmentManager(), "error");
        }
    }

    @Override
    public void onSignupClicked(RequestRegister user) {
        Log.d(TAG, "onSignupClicked: " + user.oib + " " + user.email + " " + user.password);
        viewModel.makeRegistry(user).observe(this, user1 -> {
            Log.d(TAG, "onSignupClicked: " + user1.getOib() + " " + user1.getEmail() + " " + user1.getPassword());
        });
        showProgress();
        try {
            Intent i = new Intent(this, MainScreenActivity.class);
            finish();
            startActivity(i);
        } catch (Exception e) {
            ErrorDialog.CreateInstance(this, "Error", e.toString(), "Ok");
        }
    }
}
