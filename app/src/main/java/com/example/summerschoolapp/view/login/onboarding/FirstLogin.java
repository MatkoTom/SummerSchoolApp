package com.example.summerschoolapp.view.login.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.summerschoolapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstLogin extends AppCompatActivity {

    @BindView(R.id.btn_continue)
    Button btnContinue;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("firstLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkIfFirstEntry();
    }

    @OnClick(R.id.btn_continue)
    public void goToNextAct() {
        editor.putString("clicked", "true");
        editor.commit();
        Intent i = new Intent(FirstLogin.this, OnboardingActivity.class);
        startActivity(i);
    }

    private void checkIfFirstEntry() {
        String getStatus = sharedPreferences.getString("clicked", "nill");
        if (getStatus != null && getStatus.equals("true")) {
            Intent i = new Intent(FirstLogin.this, OnboardingActivity.class);
            startActivity(i);
        }
    }
}
