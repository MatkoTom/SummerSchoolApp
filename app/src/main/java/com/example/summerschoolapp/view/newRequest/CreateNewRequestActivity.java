package com.example.summerschoolapp.view.newRequest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.model.newRequest.NewRequest;
import com.example.summerschoolapp.model.newRequest.RequestNewRequest;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.EditUserActivity;
import com.example.summerschoolapp.view.EditUserViewModel;
import com.example.summerschoolapp.view.main.MainScreenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewRequestActivity extends BaseActivity {

    @BindView(R.id.spinner_new_request_items)
    Spinner spinnerNewRequestItems;

    @BindView(R.id.et_request_name)
    EditText etRequestName;

    @BindView(R.id.et_request_message)
    EditText etRequestMessage;

    @BindView(R.id.et_request_address)
    EditText etRequestAddress;

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
        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(this, R.layout.spinner_item_new_request, getResources().getStringArray(R.array.testArray));
        spinnerNewRequestItems.setAdapter(adapterGray);
    }

    @OnClick(R.id.btn_post_new_request)
    public void postnewRequest() {
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

    public RequestNewRequest sendData() {
        RequestNewRequest request = new RequestNewRequest();
        request.title = etRequestName.getText().toString();
        request.message = etRequestMessage.getText().toString();
        request.requestType = spinnerNewRequestItems.getSelectedItem().toString();
        request.location_longitude = String.valueOf(1.1);
        request.location_latitude = String.valueOf(1.1);

        return request;
    }
}
