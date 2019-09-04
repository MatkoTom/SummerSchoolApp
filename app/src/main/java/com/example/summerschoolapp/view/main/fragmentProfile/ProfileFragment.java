package com.example.summerschoolapp.view.main.fragmentProfile;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseFragment;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.InsertTextDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.onboarding.OnboardingActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

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

    @BindView(R.id.iv_user_picture_icon)
    ImageView ivUserPictureIcon;

    @BindView(R.id.civ_user_image)
    CircleImageView civUserimage;

    private ProfileFragmentViewModel viewModel;
    private boolean isVisible = false;
    private String firstName = "";
    private String lastName = "";
    private String mail = "";
    private String oib = "";
    private static final int PICK_FROM_GALLERY = 1;
    private File image;
    private String filePath = "";

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
        if (user.getFirstName() == null || user.getLastName() == null) {
            tvProfileName.setText(getString(R.string.set_name));
        } else {
            tvProfileName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        }
        tvProfileMail.setText(user.getEmail());
        tvProfileOib.setText(user.getOib());

        if (user.getPhoto() != null) {
            Glide.with(getActivity())
                    .asBitmap()
                    .load(Const.Api.API_GET_IMAGE + user.getPhoto())
                    .into(civUserimage);

            ivUserPictureIcon.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.ibtn_change_name)
    public void btnChangeProfileName() {
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

    @OnClick(R.id.ibtn_change_oib)
    public void btnChangeProfileOib() {
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

    @OnClick(R.id.ibtn_change_mail)
    public void btnChangeProfileMail() {
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

    @OnClick(R.id.civ_user_image)
    public void choosePicture() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
                    .into(civUserimage);

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("photo", "image", uploadPicture(filePath))
                    .build();

            viewModel.editUserProfile(body);

            ivUserPictureIcon.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), getString(R.string.failed_to_load), Toast.LENGTH_SHORT).show();
        }
    }

    private RequestBody uploadPicture(String filepath) {
        if (filepath != null) {
            File file = new File(filepath);

            if (filepath != null) {
                RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/*"));
                return fileBody;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}