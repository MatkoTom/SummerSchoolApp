package com.example.summerschoolapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.summerschoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginScreen extends AppCompatActivity {

    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvRegister)
    public void goToRegister() {
        Intent i = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(i);
    }

    @OnClick(R.id.btnLogin)
    public void goToRegisterButton() {
        Intent i = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(i);
    }
}
