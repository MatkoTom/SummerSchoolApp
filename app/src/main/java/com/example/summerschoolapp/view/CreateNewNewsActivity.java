package com.example.summerschoolapp.view;

import android.os.Bundle;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewNewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_news);
        ButterKnife.bind(this);
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
