package com.example.summerschoolapp.view.newUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.model.newuser.RequestNewUser;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.main.MainScreenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewUserActivity extends BaseActivity {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CreateNewUserActivity.class));
    }

    @BindView(R.id.btn_create_new_user)
    Button btnCreateNewUser;

    @BindView(R.id.ibtn_hide_show)
    ImageButton ibtnHideShow;

    @BindView(R.id.et_create_user_password)
    EditText etCreateUserPassword;

    @BindView(R.id.et_new_user_name)
    EditText etCreateUserName;

    @BindView(R.id.et_new_user_mail)
    EditText etCreateUserEmail;

    @BindView(R.id.et_new_user_oib)
    EditText etCreateUserOib;

    private boolean isVisible = false;
    private CreateNewUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CreateNewUserViewModel.class);

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
                if (value instanceof NewUserError) {
                    message = getString(((NewUserError.Error) value.getError()).getValue());
                } else {
                    message = String.format("%s \n --- \n %s", message, value.getExtraInfo());
                }
                ErrorDialog.CreateInstance(CreateNewUserActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    SuccessDialog.CreateInstance(this, getString(R.string.success), getString(R.string.user_successfully_created), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            MainScreenActivity.StartActivity(CreateNewUserActivity.this);
                        }

                        @Override
                        public void onNegativeInteraction() {
                            // TODO @Matko
                            // mark similar methods with a below comment if they are not used
                            // ignored
                        }
                    });
                    break;
            }
        });


    }

    @OnClick(R.id.ibtn_hide_show)
    public void showHidePassword() {
        if (!isVisible) {
            etCreateUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ibtnHideShow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_icon));
            isVisible = true;
        } else {
            etCreateUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ibtnHideShow.setImageDrawable(getResources().getDrawable(R.drawable.log_in_lozinka_hiden_icon));
            isVisible = false;
        }
    }

    @OnClick(R.id.civ_new_user_picture)
    public void setNewUserPicture() {

    }

    @OnClick(R.id.btn_create_new_user)
    public void createNewUser() {
        if (!(etCreateUserEmail.getText().toString().length() == 0 ||
                etCreateUserPassword.getText().toString().length() == 0 ||
                etCreateUserName.getText().toString().length() == 0 ||
                etCreateUserOib.getText().toString().length() == 0)) {
            viewModel.createNewUser(sendData(), Tools.getSharedPreferences(this).getSavedUserData().getJwt());
        } else {
            Toast.makeText(this, getString(R.string.plsea_fill_out_all_fields), Toast.LENGTH_LONG).show();
        }

    }

    private RequestNewUser sendData() {
        RequestNewUser user = new RequestNewUser();
        user.email = etCreateUserEmail.getText().toString();
        String[] splitString = etCreateUserName.getText().toString().trim().split(" ");
        user.firstName = splitString[0];
        user.lastName = splitString[1];
        user.oib = etCreateUserOib.getText().toString();
        user.password = Tools.md5(etCreateUserPassword.getText().toString());
        return user;
    }

    @OnClick(R.id.ibtn_back)
    public void imageButtonBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void textViewBack() {
        finish();
    }
}