package com.example.summerschoolapp.view.login.onboarding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.login.login.LoginFragment;
import com.example.summerschoolapp.view.login.signup.SignupFragment;

import butterknife.ButterKnife;

public class OnboardingActivity extends AppCompatActivity implements LoginFragment.OnFragmentLoginClickListener, SignupFragment.OnSignupFragmentClicListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ButterKnife.bind(this);

        runLoginFragment();
    }

    public void runLoginFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new LoginFragment(), "login");
        transaction.addToBackStack("login");
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
        transaction.addToBackStack("login");
        transaction.commit();
    }
}
