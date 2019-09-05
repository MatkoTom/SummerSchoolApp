package com.example.summerschoolapp.view.newUser;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    @BindView(R.id.iv_user_picture_icon)
    ImageView ivUserPictureIcon;

    @BindView(R.id.tv_oib_in_use)
    TextView tvOibInUse;

    @BindView(R.id.tv_oib)
    TextView tvOib;

    @BindView(R.id.tv_create_uer_email)
    TextView tvEmail;

    @BindView(R.id.tv_create_user_wring_email)
    TextView tvWrongEmail;

    @BindView((R.id.civ_new_user_picture))
    CircleImageView civNewUserPicture;

    private boolean isVisible = false;
    private CreateNewUserViewModel viewModel;
    private static final int PICK_FROM_GALLERY = 1;
    private File image;
    private ColorStateList oldColor;
    private String filePath = "";

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
                            finish();
                        }

                        @Override
                        public void onNegativeInteraction() {
                            // ignored
                        }
                    });
                    break;
            }
        });

        canUserBeCreated();
        textChangedListener();
        oldColor = tvOib.getTextColors();
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
        try {
            if (ActivityCompat.checkSelfPermission(CreateNewUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateNewUserActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mediaPath = cursor.getString(columnIndex);
            cursor.close();

            filePath = mediaPath;

            image = new File(filePath);
            Glide.with(this)
                    .asBitmap()
                    .load(data.getDataString())
                    .into(civNewUserPicture);

            ivUserPictureIcon.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, getString(R.string.failed_to_load), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_create_new_user)
    public void createNewUser() {
        if (!(etCreateUserEmail.getText().toString().length() == 0 ||
                etCreateUserPassword.getText().toString().length() == 0 ||
                etCreateUserName.getText().toString().length() == 0 ||
                etCreateUserOib.getText().toString().length() == 0)) {
            String email = etCreateUserEmail.getText().toString();
            String name = etCreateUserName.getText().toString();
            String oib = etCreateUserOib.getText().toString();
            String password = Tools.md5(etCreateUserPassword.getText().toString());

            if (filePath.equals("")) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("oib", oib)
                        .addFormDataPart("name", name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", password)
                        .build();

                viewModel.postNewUser(requestBody);
            } else {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("oib", oib)
                        .addFormDataPart("name", name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", password)
                        .addFormDataPart("photo", "image", uploadPicture(filePath))
                        .build();

                viewModel.postNewUser(requestBody);
            }
        } else {
            Toast.makeText(this, getString(R.string.plsea_fill_out_all_fields), Toast.LENGTH_LONG).show();
        }

    }

    private void textChangedListener() {
        etCreateUserOib.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canUserBeCreated();
                if (etCreateUserOib.length() == 0 || etCreateUserOib.length() > 11 || etCreateUserOib.length() < 11) {
                    tvOibInUse.setTextColor(Color.RED);
                    tvOibInUse.setText(R.string.oib_error_11_characters);
                    tvOib.setTextColor(Color.RED);
                } else {
                    tvOib.setTextColor(oldColor);
                    tvOibInUse.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCreateUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canUserBeCreated();
                if (!isValidEmail(etCreateUserEmail.getText().toString().trim())) {
                    tvWrongEmail.setTextColor(Color.RED);
                    tvWrongEmail.setText(R.string.not_an_email);
                    tvEmail.setTextColor(Color.RED);
                } else {
                    tvEmail.setTextColor(oldColor);
                    tvWrongEmail.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCreateUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canUserBeCreated();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCreateUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                canUserBeCreated();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public RequestBody uploadPicture(String filepath) {
        File file = new File(filepath);

        if (filepath != null) {
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/*"));

            return fileBody;
        }
        return null;
    }

    private static boolean isValidEmail(CharSequence target) {  // Email validator, checks if field has correct input
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void canUserBeCreated() {
        if (!isValidEmail(etCreateUserEmail.getText().toString().trim()) || etCreateUserPassword.length() == 0 || etCreateUserOib.length() < 11 || etCreateUserOib.length() > 11) {
            btnCreateNewUser.setEnabled(false);
            btnCreateNewUser.setAlpha(0.5f);
        } else {
            btnCreateNewUser.setEnabled(true);
            btnCreateNewUser.setAlpha(1.0f);
        }
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
