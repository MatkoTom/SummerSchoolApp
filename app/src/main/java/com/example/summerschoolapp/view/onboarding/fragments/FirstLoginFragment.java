package com.example.summerschoolapp.view.onboarding.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.utils.Tools;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstLoginFragment extends Fragment {

    private static final String TAG = "STASEDOGADJA";

    private OnFirstLoginFragmentLoginListener loginListener;
    private OnFirstLoginFragmentRegisterListener registerListener;

    public FirstLoginFragment() {
        // Required empty public constructor
    }

    public interface OnFirstLoginFragmentLoginListener {
        void onFirstLoginItemClicked();
    }

    public interface OnFirstLoginFragmentRegisterListener {
        void onFirstLoginSignup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first_login, container, false);
        ButterKnife.bind(this, rootView);

        checkIfFirstEntry();
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        loginListener = (OnFirstLoginFragmentLoginListener) context;
        registerListener = (OnFirstLoginFragmentRegisterListener) context;
    }

    @OnClick(R.id.tv_login_oib)
    public void goToSignupFragment() {
        Tools.getSharedPreferences(getActivity()).setShouldShowFirstLogin(true);
        loginListener.onFirstLoginItemClicked();
    }

    @OnClick(R.id.btn_continue)
    public void goToLoginFragment() {
        Tools.getSharedPreferences(getActivity()).setShouldShowFirstLogin(true);
        registerListener.onFirstLoginSignup();
    }

    private void checkIfFirstEntry() {
        Boolean showFirstLogin = Tools.getSharedPreferences(getActivity()).shouldShowFirstLogin();
        if (showFirstLogin) {
            loginListener.onFirstLoginItemClicked();
        }
    }
}
