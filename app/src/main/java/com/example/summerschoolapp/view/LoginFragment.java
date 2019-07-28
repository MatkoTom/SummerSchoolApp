package com.example.summerschoolapp.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnLogin)
    public void logInUser() {
        Toast.makeText(getActivity(), "Yo!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tvRegister)
    public void registerUser() {
        ((LoginActivity)getActivity()).runRegisterFragment();
    }

}
