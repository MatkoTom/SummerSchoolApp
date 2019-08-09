package com.example.summerschoolapp.view.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.model.RequestLogin;
import com.example.summerschoolapp.model.RequestRegister;
import com.example.summerschoolapp.view.main.MainScreenActivity;
import com.example.summerschoolapp.view.onboarding.fragments.FirstLoginFragment;
import com.example.summerschoolapp.view.onboarding.fragments.LoginFragment;
import com.example.summerschoolapp.view.onboarding.fragments.SignupFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_FIRST_LOGIN;
import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_LOGIN;
import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_REGISTER;

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
        transaction.add(R.id.fragment_container, new FirstLoginFragment(), FRAGMENT_TAG_FIRST_LOGIN);
        transaction.addToBackStack(FRAGMENT_TAG_FIRST_LOGIN);
        transaction.commit();
    }

    @Override
    public void onLoginItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new SignupFragment(), FRAGMENT_TAG_REGISTER);
        transaction.addToBackStack(FRAGMENT_TAG_REGISTER);
        transaction.commit();
    }

    @Override
    public void onSignupItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new LoginFragment(), FRAGMENT_TAG_LOGIN);
        transaction.commit();
    }

    @Override
    public void onFirstLoginItemClicked() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new LoginFragment(), FRAGMENT_TAG_LOGIN);
        transaction.commit();
    }

    @Override
    public void onFirstLoginRegister() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new SignupFragment(), FRAGMENT_TAG_REGISTER);
        transaction.addToBackStack(FRAGMENT_TAG_REGISTER);
        transaction.commit();
    }

    @Override
    public void onLoginClicked(RequestLogin user) {
        Timber.tag(TAG).d("onLoginClicked: " + user.password + " " + user.email);
        viewModel.makeLogin(user).observe(this, user1 -> Timber.tag(TAG).d("onChanged: " + user1.getEmail() + " " + user1.getPassword()));
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

    //TODO initialize vievmodel on activity created to listen all the time
    @Override
    public void onSignupClicked(RequestRegister user) {
        Timber.tag(TAG).d("onSignupClicked: " + user.oib + " " + user.email + " " + user.password);
        viewModel.registerUser(user).observe(this, user1 -> Timber.tag(TAG).d("onSignupClicked: " + user1.user.getEmail() + " " + user1.user.getRole() + " " + user1.user.getToken() + " " + user1.user.getName()));
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
