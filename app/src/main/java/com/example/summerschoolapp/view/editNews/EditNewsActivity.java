package com.example.summerschoolapp.view.editNews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.database.entity.NewsArticle;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.utils.helpers.ScrollAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class EditNewsActivity extends BaseActivity {

    public static void StartActivity(Context context, NewsArticle newsForEditing) {

        Intent intent = new Intent(context, EditNewsActivity.class);
        intent.putExtra(Const.Intent.NEWS_DATA, newsForEditing);
        context.startActivity(intent);
    }

    @BindView(R.id.et_news_address)
    EditText etNewsAddress;

    @BindView(R.id.et_news_title)
    EditText etNewsTitle;

    @BindView(R.id.et_news_text)
    EditText etNewsText;

    @BindView(R.id.ibtn_upload_document)
    ImageButton ibtnUploadDocument;

    @BindView(R.id.ibtn_upload_photo)
    ImageButton ibtnUploadPhoto;

    @BindView(R.id.sv_news_item)
    ScrollAdapter svNewsItem;

    @BindView(R.id.mv_news_location)
    MapView mapView;

    private EditNewsViewModel viewModel;
    private GoogleMap mMap;
    NewsArticle newsForEditing;
    private static final int PICK_FROM_GALLERY = 1;
    private File image;
    private String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            newsForEditing = getIntent().getParcelableExtra(Const.Intent.NEWS_DATA);
        }

        viewModel = ViewModelProviders.of(this).get(EditNewsViewModel.class);

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
                ErrorDialog.CreateInstance(EditNewsActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    SuccessDialog.CreateInstance(this, getString(R.string.success), getString(R.string.news_edited_success), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            finish();
                        }

                        @Override
                        public void onNegativeInteraction() {
                            //ignore
                        }
                    });
                    break;
            }
        });
        Timber.d(String.valueOf(newsForEditing.getArticle_id()));
        mapView.onCreate(savedInstanceState);
        setField();
        mapChange();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latitude_longitude = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            latitude_longitude = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return latitude_longitude;
    }

    public void setField() {
        etNewsTitle.setText(newsForEditing.getTitle_log());
        etNewsText.setText(newsForEditing.getMessage_log());
        etNewsAddress.setText(newsForEditing.getAddress());
    }

    @OnClick(R.id.btn_edit_news)
    public void postEditNews() {

        String title = etNewsTitle.getText().toString();
        String message = etNewsText.getText().toString();
        String latitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).latitude);
        String longitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).longitude);
        String address = etNewsAddress.getText().toString();

        if (filePath.equals("") && requiredFieldsFull()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", title)
                    .addFormDataPart("message", message)
                    .addFormDataPart("location_latitude", latitude)
                    .addFormDataPart("location_longitude", longitude)
                    .addFormDataPart("Address", address)
                    .build();

            viewModel.postEditNews(newsForEditing.getArticle_id(), requestBody);
        } else if (!filePath.equals("") && requiredFieldsFull()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", title)
                    .addFormDataPart("message", message)
                    .addFormDataPart("location_latitude", latitude)
                    .addFormDataPart("location_longitude", longitude)
                    .addFormDataPart("Address", address)
                    .addFormDataPart("photo", "image.png", uploadPicture(filePath))
                    .build();

            viewModel.postEditNews(newsForEditing.getArticle_id(), requestBody);
        } else {
            Toast.makeText(this, getString(R.string.enter_required_fields), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean requiredFieldsFull() {
        return etNewsAddress.length() != 0 && etNewsText.length() != 0 && etNewsTitle.length() != 0;
    }

    @OnClick(R.id.ibtn_back)
    public void imageButtonBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void textViewBack() {
        finish();
    }

    public void mapChange() {
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            if (newsForEditing.getLocation_latitude() != null && newsForEditing.getLocation_longitude() != null) {
                LatLng location = new LatLng(Double.parseDouble(newsForEditing.getLocation_latitude()), Double.parseDouble(newsForEditing.getLocation_longitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            } else {
                LatLng location = new LatLng(Const.Location.ZAGREB_LATITUDE, Const.Location.ZAGREB_LONGITUDE);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            }
            mMap.setOnCameraIdleListener(() -> {
                LatLng centerOfMap = mMap.getCameraPosition().target;
                svNewsItem.setEnableScrolling(true);

                double latitude = centerOfMap.latitude;
                double longitude = centerOfMap.longitude;

                Timber.d("LATLNG:" + centerOfMap.latitude + " " + centerOfMap.longitude);

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String[] street = address.getAddressLine(0).split(",");
                    String streetName = street[0];
                    Timber.d("ADRESS%s", streetName);
                    etNewsAddress.setText(streetName);
                }

            });
            mMap.setOnCameraMoveStartedListener(i -> svNewsItem.setEnableScrolling(false));
        });
    }

    @OnClick(R.id.ibtn_upload_photo)
    public void setNewUserPicture() {
        try {
            if (ActivityCompat.checkSelfPermission(EditNewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditNewsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
                    .into(ibtnUploadPhoto);

            Timber.d("FILE PATH: %s", filePath);
        } else {
            Toast.makeText(this, getString(R.string.failed_to_load), Toast.LENGTH_SHORT).show();
        }
    }

    public RequestBody uploadPicture(String filepath) {
        File file = new File(filepath);

        if (filepath != null) {
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/png"));

            return fileBody;
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}


