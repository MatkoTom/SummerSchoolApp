package com.example.summerschoolapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.summerschoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstLogin extends AppCompatActivity {

    @BindView(R.id.btnContinue)
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnContinue)
    public void goToNextAct() {
        Intent i = new Intent(FirstLogin.this, LoginActivity.class);
        startActivity(i);
    }
}
