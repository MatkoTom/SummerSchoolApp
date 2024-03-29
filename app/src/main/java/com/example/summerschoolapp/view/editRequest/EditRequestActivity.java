package com.example.summerschoolapp.view.editRequest;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.model.Request;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.utils.helpers.ScrollAdapter;
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

public class EditRequestActivity extends BaseActivity {

    public static void StartActivity(Context context, Request requestForEditing) {

        Intent intent = new Intent(context, EditRequestActivity.class);
        intent.putExtra(Const.Intent.REQUEST_DATA, requestForEditing);
        context.startActivity(intent);
    }

    @BindView(R.id.spinner_new_request_items)
    Spinner spinnerNewRequestItems;

    @BindView(R.id.et_request_name)
    EditText etRequestName;

    @BindView(R.id.et_request_message)
    EditText etRequestMessage;

    @BindView(R.id.et_request_address)
    EditText etRequestAddress;

    @BindView(R.id.scroll_view_request)
    ScrollAdapter svRequest;

    private EditRequestViewModel viewModel;
    private GoogleMap mMap;
    private MapView mapView;
    Request requestForEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            requestForEditing = getIntent().getParcelableExtra(Const.Intent.REQUEST_DATA);
        }

        viewModel = ViewModelProviders.of(this).get(EditRequestViewModel.class);

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
                ErrorDialog.CreateInstance(EditRequestActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    SuccessDialog.CreateInstance(this, getString(R.string.success), getString(R.string.request_edit_success), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
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

        mapView = findViewById(R.id.mv_request_location);
        mapView.onCreate(savedInstanceState);
        setField();
        Timber.d(requestForEditing.getTitle());
        mapChange();
        populateSpinner();
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(this, R.layout.spinner_item_new_request, getResources().getStringArray(R.array.requestArray));
        spinnerNewRequestItems.setAdapter(adapterGray);
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

    public void setField() {
        if (requestForEditing.getTitle() != null) {
            etRequestName.setText(requestForEditing.getTitle());
        }
        etRequestMessage.setText(requestForEditing.getMessage());
        etRequestAddress.setText(requestForEditing.getAddress());
    }

    @OnClick(R.id.btn_edit_request)
    public void postNewRequest() {

        String id = requestForEditing.getID();
        String title = etRequestName.getText().toString();
        String type = spinnerNewRequestItems.getSelectedItem().toString();
        String message = etRequestMessage.getText().toString();
        String latitude = String.valueOf(getLocationFromAddress(this, etRequestAddress.getText().toString()).latitude);
        String longitude = String.valueOf(getLocationFromAddress(this, etRequestAddress.getText().toString()).longitude);
        String address = etRequestAddress.getText().toString();

        if (requiredFieldsFull()) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", title)
                    .addFormDataPart("Request_type", type)
                    .addFormDataPart("message", message)
                    .addFormDataPart("location_latitude", latitude)
                    .addFormDataPart("location_longitude", longitude)
                    .addFormDataPart("Address", address)
                    .build();

            viewModel.postEditRequest(id, requestBody);
        } else {
            Toast.makeText(this, getString(R.string.enter_required_fields), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean requiredFieldsFull() {
        return etRequestAddress.length() != 0 && etRequestMessage.length() != 0 && etRequestName.length() != 0;
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
            if (requestForEditing.getLocation_latitude() != null && requestForEditing.getLocation_longitude() != null) {
                LatLng location = new LatLng(Double.parseDouble(requestForEditing.getLocation_latitude()), Double.parseDouble(requestForEditing.getLocation_longitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            } else {
                LatLng location = new LatLng(Const.Location.ZAGREB_LATITUDE, Const.Location.ZAGREB_LONGITUDE);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            }
            mMap.setOnCameraIdleListener(() -> {
                LatLng centerOfMap = mMap.getCameraPosition().target;
                svRequest.setEnableScrolling(true);

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
                    etRequestAddress.setText(streetName);
                }

            });
            mMap.setOnCameraMoveStartedListener(i -> svRequest.setEnableScrolling(false));
        });
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
