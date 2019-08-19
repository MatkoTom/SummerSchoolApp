package com.example.summerschoolapp.view.newRequest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.summerschoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewRequestActivity extends AppCompatActivity {

    @BindView(R.id.spinner_new_request_items)
    Spinner spinnerNewRequestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_request);
        ButterKnife.bind(this);

        populateSpinner();
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(this, R.layout.spinner_item_new_request, getResources().getStringArray(R.array.testArray));
        spinnerNewRequestItems.setAdapter(adapterGray);
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
