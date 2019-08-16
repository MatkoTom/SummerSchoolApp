package com.example.summerschoolapp.view.main.fragmentRequests;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.summerschoolapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    @BindView(R.id.spinner_request_items)
    Spinner spinner_requestItems;

    @BindView(R.id.spinner_new_request_items)
    Spinner spinnerNewRequestItems;

    @BindView(R.id.cl_no_requests)
    ConstraintLayout clNoRequests;

    @BindView(R.id.cl_create_new_request)
    ConstraintLayout clCreateNewRequest;

//    @BindView(R.id.fab_create_new_request)
//    FloatingActionButton fabCreateNewRequest;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, rootView);

        populateSpinner();
        return rootView;
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, getActivity().getResources().getStringArray(R.array.testArray));
        spinner_requestItems.setAdapter(adapter);

        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_new_request, getActivity().getResources().getStringArray(R.array.testArray));
        spinnerNewRequestItems.setAdapter(adapterGray);
    }

    @OnClick(R.id.btn_new_request)
    public void createNewRequest() {
        clNoRequests.setVisibility(View.GONE);
        clCreateNewRequest.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_post_new_request)
    public void postNewRequest() {
        clNoRequests.setVisibility(View.VISIBLE);
        clCreateNewRequest.setVisibility(View.GONE);
    }

//    @OnClick(R.id.fab_create_new_request)
//    public void createNewRequest() {
//
//    }

}
