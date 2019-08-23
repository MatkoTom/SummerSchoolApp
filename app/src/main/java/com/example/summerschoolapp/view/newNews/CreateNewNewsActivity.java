package com.example.summerschoolapp.view.newNews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.main.MainScreenActivity;
import com.example.summerschoolapp.view.newRequest.CreateNewRequestActivity;
import com.example.summerschoolapp.view.newRequest.CreateNewRequestViewModel;
import com.example.summerschoolapp.view.newRequest.RequestScrollAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    RequestScrollAdapter svNewsItem;

    @BindView(R.id.mv_news_location)
    MapView mapView;

    private CreateNewNewsViewModel viewModel;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_news);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CreateNewNewsViewModel.class);

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
        mapView.onCreate(savedInstanceState);
        mapChange();
    }

    public void mapChange() {
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            LatLng location = new LatLng(45.8150, 15.9819);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
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
            mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int i) {
                    svNewsItem.setEnableScrolling(false);
                }
            });
        });
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

        String latitude = String.valueOf(latitude_longitude.latitude);
        String longitude = String.valueOf(latitude_longitude.longitude);
        Timber.d("LATLNG:" + latitude + " " + longitude);

        return latitude_longitude;
    }

    @OnClick(R.id.btn_post_new_news)
    public void postNewNews() {

        String title = etNewsTitle.getText().toString();
        String message = etNewsText.getText().toString();
        String address = etNewsAddress.getText().toString();
        String latitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).latitude);
        String longitude = String.valueOf(getLocationFromAddress(this, etNewsAddress.getText().toString()).longitude);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Title", title)
                .addFormDataPart("Message", message)
                .addFormDataPart("Location_latitude", latitude)
                .addFormDataPart("Location_longitude", longitude)
                .addFormDataPart("Address", address)
                .build();

        //TODO send to viewModel, do in other activities
        viewModel.createNewNews(Tools.getSharedPreferences(this).getSavedUserData().getJwt(), requestBody);
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
