package com.example.summerschoolapp.view.newNews;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.model.Photo;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.utils.helpers.ScrollAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

public class CreateNewNewsActivity extends BaseActivity {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CreateNewNewsActivity.class));
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

    @BindView(R.id.rv_news_photos)
    RecyclerView rvNewsPhotos;

    private CreateNewNewsViewModel viewModel;
    private GoogleMap mMap;
    private static final int PICK_FROM_GALLERY = 1;
    public static final int REQUEST_CODE = 2;
    private File image;
    private String filePath = "";
    private String docPath = "";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private NewNewsListAdapter adapter;
    private List<Photo> photosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_news);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CreateNewNewsViewModel.class);

        adapter = new NewNewsListAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvNewsPhotos.setLayoutManager(layoutManager);
        rvNewsPhotos.setAdapter(adapter);


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
                ErrorDialog.CreateInstance(CreateNewNewsActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    SuccessDialog.CreateInstance(this, getString(R.string.success), getString(R.string.news_created), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapView.onCreate(savedInstanceState);
        mapChange();
    }

    @OnClick(R.id.ibtn_upload_photo)
    public void setNewUserPicture() {
        try {
            if (ActivityCompat.checkSelfPermission(CreateNewNewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateNewNewsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ibtn_upload_document)
    public void uploadDocument() {
        try {
            if (ActivityCompat.checkSelfPermission(CreateNewNewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateNewNewsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, REQUEST_CODE);
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

            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, REQUEST_CODE);
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

            //TODO rv horiznontal with images
            //in progress
////            Glide.with(this)
////                    .asBitmap()
////                    .load(data.getDataString())
////                    .into(ibtnUploadPhoto);
//
//
            photosList.add(new Photo(data.getDataString()));
            Timber.d("CONTENT: %s", photosList.toString());
            rvNewsPhotos.setVisibility(View.VISIBLE);
            adapter.setData(photosList);
        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            Uri selectedFile = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedFile, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String documentPath = cursor.getString(columnIndex);
            cursor.close();

            docPath = documentPath;

        } else {
            Toast.makeText(this, getString(R.string.failed_to_load), Toast.LENGTH_SHORT).show();
        }
    }

    public void mapChange() {
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;

            fusedLocationProviderClient.getLastLocation()
                    .addOnFailureListener(this, e -> {
                        LatLng coordinates = new LatLng(Const.Location.ZAGREB_LATITUDE, Const.Location.ZAGREB_LONGITUDE);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                    })
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                        }
                    });

            mMap.setOnCameraIdleListener(() -> {
                LatLng centerOfMap = mMap.getCameraPosition().target;
                svNewsItem.setEnableScrolling(true);

                double latitude = centerOfMap.latitude;
                double longitude = centerOfMap.longitude;

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
                    etNewsAddress.setText(streetName);
                }

            });
            mMap.setOnCameraMoveStartedListener(i -> svNewsItem.setEnableScrolling(false));
        });
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latitude_longitude = null;

        try {
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

    @OnClick(R.id.btn_post_new_news)
    public void postNewNews() {

        String title = etNewsTitle.getText().toString();
        String message = etNewsText.getText().toString();
        String address = etNewsAddress.getText().toString();
        String latitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).latitude);
        String longitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).longitude);

        if (filePath.equals("") && requiredFiledsFull()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", title)
                    .addFormDataPart("Message", message)
                    .addFormDataPart("Location_latitude", latitude)
                    .addFormDataPart("Location_longitude", longitude)
                    .addFormDataPart("Address", address)
                    .build();

            viewModel.postNewNews(requestBody);
        } else if (!filePath.equals("") && requiredFiledsFull()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", title)
                    .addFormDataPart("Message", message)
                    .addFormDataPart("Location_latitude", latitude)
                    .addFormDataPart("Location_longitude", longitude)
                    .addFormDataPart("Address", address)
                    .addFormDataPart("photo", "picture", uploadPicture(filePath))
// ignore                 .addFormDataPart("Files", "file", uploadFile(docPath))
                    .build();

            viewModel.postNewNews(requestBody);
        } else {
            Toast.makeText(this, getString(R.string.enter_required_fields), Toast.LENGTH_SHORT).show();
        }


    }

    public boolean requiredFiledsFull() {
        return etNewsText.length() != 0 && etNewsAddress.length() != 0 && etNewsTitle.length() != 0;
    }

    public RequestBody uploadPicture(String filepath) {
        File file = new File(filepath);

        if (filepath != null) {
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/*"));

            return fileBody;
        }
        return null;
    }

    //TODO in progress
    public RequestBody uploadFile(String filepath) {
        File file = new File(filepath);

        if (filepath != null) {
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/pdf"));

            return fileBody;
        }
        return null;
    }

    @OnClick(R.id.ibtn_back)
    public void imageButtonBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void textViewBack() {
        finish();
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
