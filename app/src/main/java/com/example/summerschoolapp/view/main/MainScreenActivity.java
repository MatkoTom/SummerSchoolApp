package com.example.summerschoolapp.view.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseActivity;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.errors.NewUserError;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.main.adapter.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class MainScreenActivity extends BaseActivity {

    public static void StartActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MainScreenActivity.class));
        activity.finish();
    }

    private MainScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);

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
                ErrorDialog.CreateInstance(MainScreenActivity.this, getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
        }
    }
}