package com.example.summerschoolapp.view.newRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.model.newRequest.RequestNewRequest;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.main.MainScreenActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateNewRequestActivity extends BaseActivity {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CreateNewRequestActivity.class));
    }

    @BindView(R.id.spinner_new_request_items)
    Spinner spinnerNewRequestItems;

    @BindView(R.id.et_request_name)
    EditText etRequestName;

    @BindView(R.id.et_request_message)
    EditText etRequestMessage;

    @BindView(R.id.et_request_address)
    EditText etRequestAddress;

    @BindView(R.id.ibtn_request_location)
    ImageView ibtnRequestLocation;

    private CreateNewRequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_request);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CreateNewRequestViewModel.class);

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
                ErrorDialog.CreateInstance(CreateNewRequestActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case MAIN:
                    SuccessDialog.CreateInstance(this, getString(R.string.success), getString(R.string.request_created), getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            MainScreenActivity.StartActivity(CreateNewRequestActivity.this);
                        }

                        @Override
                        public void onNegativeInteraction() {

                        }
                    });
                    break;
            }
        });


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

    @OnClick(R.id.btn_post_new_request)
    public void postNewRequest() {
        viewModel.postNewRequest(Tools.getSharedPreferences(this).getSavedUserData().getJwt(), sendData());
    }

    @OnClick(R.id.ibtn_back)
    public void imageButtonBack() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void textViewBack() {
        finish();
    }

    @OnClick(R.id.ibtn_request_location)
    public void requestLocation() {
        // TODO @Matko
        // this is probably pending work so it should be marked with todo
        // also add a short comment like pending work
        Glide.with(this)
                .asBitmap()
                .load("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318&markers=color:red%7Clabel:C%7C40.718217,-73.998284&key=AIzaSyD_S_EijHqDQvHbgkvSzPLz5KD-0dEKNTQ")
                .into(ibtnRequestLocation);

    }

    public RequestNewRequest sendData() {
        RequestNewRequest request = new RequestNewRequest();
        String latitude = getLocationFromAddress(this, etRequestAddress.getText().toString()).toString();
        Timber.d("ADRESS: %s", latitude);
        request.title = etRequestName.getText().toString();
        request.message = etRequestMessage.getText().toString();
        request.requestType = spinnerNewRequestItems.getSelectedItem().toString();
        request.location_longitude = String.valueOf(getLocationFromAddress(this, etRequestAddress.getText().toString()).longitude);
        request.location_latitude = String.valueOf(getLocationFromAddress(this, etRequestAddress.getText().toString()).latitude);

        return request;
    }
}
