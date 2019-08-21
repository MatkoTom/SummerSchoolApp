package com.example.summerschoolapp.view.newNews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.view.main.MainScreenActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNewNewsActivity extends BaseActivity {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CreateNewNewsActivity.class));
    }

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
