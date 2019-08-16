package com.example.summerschoolapp.view.main.fragmentRequests;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.CreateNewRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    @BindView(R.id.fab_create_new_request)
    FloatingActionButton fabCreateNewRequest;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, rootView);

//        populateSpinner();
        return rootView;
    }

    //TODO viewModel implementation
    @OnClick(R.id.btn_new_request)
    public void createNewRequest() {
        Intent i = new Intent(getActivity(), CreateNewRequest.class);
        startActivity(i);
    }

    @OnClick(R.id.fab_create_new_request)
    public void fabCreateNewRequest() {

    }

}
