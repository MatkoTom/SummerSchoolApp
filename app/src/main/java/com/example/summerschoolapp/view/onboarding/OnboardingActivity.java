package com.example.summerschoolapp.view.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.errors.LoginError;
import com.example.summerschoolapp.errors.SignupError;
import com.example.summerschoolapp.model.login.RequestLogin;
import com.example.summerschoolapp.model.signup.RequestSignup;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.main.MainScreenActivity;
import com.example.summerschoolapp.view.onboarding.fragments.FirstLoginFragment;
import com.example.summerschoolapp.view.onboarding.fragments.LoginFragment;
import com.example.summerschoolapp.view.onboarding.fragments.SignupFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_FIRST_LOGIN;
import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_LOGIN;
import static com.example.summerschoolapp.utils.Const.Fragments.FRAGMENT_TAG_SIGNUP;

public class OnboardingActivity extends BaseActivity implements SignupFragment.OnSignupLogin, LoginFragment.OnFragmentLoginNextActivity, LoginFragment.OnFragmentLoginClickListener, SignupFragment.OnSignupFragmentClicListener, FirstLoginFragment.OnFirstLoginFragmentRegisterListener, FirstLoginFragment.OnFirstLoginFragmentLoginListener {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, OnboardingActivity.class));
        activity.finish();
    }

    // TODO @Matko
    // keep things professional even in logs
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

        viewModel.getProgressStatus().observe(this, progressStatus -> {
            switch (progressStatus) {
                case START_PROGRESS:
                    showProgress();
                    break;
                case STOP_PROGRESS:
                    hideProgress();
                    break;
            }
        });

        viewModel.getBaseErrors().observe(this, new EventObserver<BaseError>() {
            @Override
            public void onEventUnhandledContent(BaseError value) {
                super.onEventUnhandledContent(value);
                String message = getString(R.string.text_try_again);
                if (value instanceof SignupError) {
                    message = getString(((SignupError.Error) value.getError()).getValue());
                } else if (value instanceof LoginError) {
                    message = getString(((LoginError.Error) value.getError()).getValue());
                } else {
                    message = String.format("%s \n --- \n %s", message, value.getExtraInfo());
                }
                ErrorDialog.CreateInstance(OnboardingActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    MainScreenActivity.StartActivity(OnboardingActivity.this);
                    break;
            }
        });

        Timber.d("IsUserSaved: %s", Tools.getSharedPreferences(this).getRememberMeStatus());
        Timber.d("ShowFirstLogin: %s", Tools.getSharedPreferences(this).getShouldShowFirstLogin());
        runFirstLoginFragment();
        autoLogin();
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
        transaction.replace(R.id.fragment_container, new SignupFragment(), FRAGMENT_TAG_SIGNUP);
        transaction.addToBackStack(FRAGMENT_TAG_SIGNUP);
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
    public void onFirstLoginSignup() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        manager.popBackStack();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment_container, new SignupFragment(), FRAGMENT_TAG_SIGNUP);
        transaction.addToBackStack(FRAGMENT_TAG_SIGNUP);
        transaction.commit();
    }

    @Override
    public void onLoginClicked(RequestLogin user) {
        viewModel.makeLogin(user);

    }

    @Override
    public void onSignupClicked(RequestSignup user) {
        viewModel.registerUser(user);
    }

    public void autoLogin() {
        // TODO @Matko
        // we need to check if token and user are also stored 
        if (Tools.getSharedPreferences(this).getRememberMeStatus()) {
            viewModel.getNavigation().setValue(OnboardingViewModel.Navigation.MAIN);
        }
    }
}
