package com.example.summerschoolapp.view.main.fragmentNews;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.main.MainScreenViewModel;
import com.example.summerschoolapp.view.onboarding.OnboardingViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    public NewsFragment() {
        // Required empty public constructor
    }

    private MainScreenViewModel mainScreenActivityViewModel;
    private NewsFragmentViewModel newsFragmentViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO @Matko
        // should be initialised in onCreateView or somewhere else
        mainScreenActivityViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);
        newsFragmentViewModel = ViewModelProviders.of(this).get(NewsFragmentViewModel.class);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }

}
