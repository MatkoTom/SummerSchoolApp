package com.example.summerschoolapp.view.main.fragmentProfile;


import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseFragment;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.InsertTextDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.onboarding.OnboardingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;

    @BindView(R.id.tv_profile_oib)
    TextView tvProfileOib;

    @BindView(R.id.tv_profile_mail)
    TextView tvProfileMail;

    @BindView(R.id.et_change_password)
    EditText etChangePassword;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    @BindView(R.id.et_repeat_new_password)
    EditText etRepeatNewPassword;

    @BindView(R.id.ibtn_hide_show)
    ImageButton ibtnHideSHow;

    @BindView(R.id.layout_forgotten_password)
    ConstraintLayout layoutForgottenPassword;

    private ProfileFragmentViewModel viewModel;
    private boolean isVisible = false;
    private String firstName = "";
    private String lastName = "";
    private String mail = "";
    private String oib = "";

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);

        viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);

        viewModel.getBaseErrors().observe(this, new EventObserver<BaseError>() {
            @Override
            public void onEventUnhandledContent(BaseError value) {
                super.onEventUnhandledContent(value);
                String message = getString(R.string.text_try_again);
                ErrorDialog.CreateInstance(getActivity(), getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getProgressStatus().observe(getActivity(), progressStatus -> {
            switch (progressStatus) {
                case START_PROGRESS:
                    showProgress();
                    break;
                case STOP_PROGRESS:
                    hideProgress();
                    break;
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case LOGIN:
                    SuccessDialog.CreateInstance(getActivity(), getResources().getString(R.string.success), getString(R.string.logout_success), getResources().getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            Tools.getSharedPreferences(getActivity()).logoutUser();
                            Tools.getSharedPreferences(getActivity()).setShouldShowFirstLogin(true);
                            OnboardingActivity.StartActivity(getActivity());
                        }

                        @Override
                        public void onNegativeInteraction() {
                            // ignored
                        }
                    });
            }
        });

        viewModel.getResponse().observeEvent(this, response -> {
            switch (response) {
                case OK:
                    SuccessDialog.CreateInstance(getActivity(), getString(R.string.success), getString(R.string.edit_profile_success), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            etChangePassword.setText("");
                            etNewPassword.setText("");
                            etRepeatNewPassword.setText("");

                            if (firstName.length() > 0 && lastName.length() > 0) {
                                tvProfileName.setText(String.format("%s %s", firstName, lastName));
                            }

                            if (oib.length() > 0) {
                                tvProfileOib.setText(oib);
                            }

                            if (mail.length() > 0) {
                                tvProfileMail.setText(mail);
                            }
                        }

                        @Override
                        public void onNegativeInteraction() {
                            //ignore
                        }
                    });
            }
        });
        setData();

        return rootView;
    }

    private void setData() {
        User user = Tools.getSharedPreferences(getActivity()).getSavedUserData();
        tvProfileName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        tvProfileMail.setText(user.getEmail());
        tvProfileOib.setText(user.getOib());
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        viewModel.logoutUser();
    }

    @OnClick(R.id.btn_change_password)
    public void editUserData() {

        String currentPassword = Tools.md5(etChangePassword.getText().toString());
        String newPassword = Tools.md5(etNewPassword.getText().toString());
        String repeatPassword = Tools.md5(etRepeatNewPassword.getText().toString());

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("currentPassword", currentPassword)
                .addFormDataPart("password", newPassword)
                .addFormDataPart("newPassword2", repeatPassword)
                .build();

        viewModel.editUserProfile(body);
    }

    //TODO in progress
    @OnClick(R.id.tv_profile_name)
    public void changeProfileName() {
        InsertTextDialog.CreateInstance(getActivity(), getString(R.string.name_surname), getString(R.string.ok), getString(R.string.cancel), text -> {
            String[] name = text.split(" ");
            firstName = name[0];
            lastName = name[1];

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("firstName", firstName)
                    .addFormDataPart("lastName", lastName)
                    .build();

            viewModel.editUserProfile(body);
        });
    }

    @OnClick(R.id.tv_profile_oib)
    public void changeProfileOib() {
        InsertTextDialog.CreateInstance(getActivity(), getString(R.string.oib), getString(R.string.ok), getString(R.string.cancel), text -> {
            oib = String.valueOf(text);

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("oib", oib)
                    .build();

            viewModel.editUserProfile(body);
        });
    }

    @OnClick(R.id.tv_profile_mail)
    public void changeProfileMail() {
        InsertTextDialog.CreateInstance(getActivity(), getString(R.string.email), getString(R.string.ok), getString(R.string.cancel), text -> {
            mail = text;

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", mail)
                    .build();

            viewModel.editUserProfile(body);
        });
    }

    @OnClick(R.id.ibtn_hide_show)
    public void hideShowPassword() {
        if (!isVisible) {
            etChangePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ibtnHideSHow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_icon));
            isVisible = true;
        } else {
            etChangePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ibtnHideSHow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_hiden_icon));
            isVisible = false;
        }
    }

    @OnClick(R.id.btn_forgotten_password)
    public void changePassword() {
        if (layoutForgottenPassword.getVisibility() == View.GONE) {
            layoutForgottenPassword.setVisibility(View.VISIBLE);
        } else {
            layoutForgottenPassword.setVisibility(View.GONE);
        }
    }
}