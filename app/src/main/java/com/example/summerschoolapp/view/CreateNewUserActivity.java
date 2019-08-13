package com.example.summerschoolapp.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.RequestNewUser;
import com.example.summerschoolapp.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewUserActivity extends AppCompatActivity {

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
    private CreateNewUserViewModel newUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        ButterKnife.bind(this);

        newUserViewModel = ViewModelProviders.of(this).get(CreateNewUserViewModel.class);

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

    @OnClick(R.id.ibtn_new_user_picture)
    public void setNewUserPicture() {

    }

    @OnClick(R.id.btn_create_new_user)
    public void createNewUser() {
        if (!(etCreateUserEmail.getText().toString().length() == 0 ||
                etCreateUserPassword.getText().toString().length() == 0 ||
                etCreateUserName.getText().toString().length() == 0 ||
                etCreateUserOib.getText().toString().length() == 0)) {
//            newUserViewModel.createNewUser(sendData(), Tools.getSharedPreferences(this).getSavedUserData().data.user.getJwt());
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
//        user.token = Tools.getSharedPreferences(this).getSavedUserData().data.user.getJwt();
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
