package com.example.summerschoolapp.view.main.fragmentRequests;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseFragment;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.newRequest.CreateNewRequestActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends BaseFragment {

    @BindView(R.id.fab_create_new_request)
    FloatingActionButton fabCreateNewRequest;

    @BindView(R.id.rv_requests)
    RecyclerView rvRequests;

    @BindView(R.id.spinner_request_items)
    Spinner spinnerRequestItems;

    @BindView(R.id.request_list_layout)
    ConstraintLayout requestListLayout;

    @BindView(R.id.no_request_layout)
    ConstraintLayout noRequestLayout;

    private RequestListAdapter requestListAdapter;
    private RequestFragmentViewModel viewModel;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, rootView);

        requestListAdapter = new RequestListAdapter(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRequests.setLayoutManager(layoutManager);
        rvRequests.setAdapter(requestListAdapter);

        viewModel = ViewModelProviders.of(this).get(RequestFragmentViewModel.class);

        viewModel.getRecyclerList().observeEvent(this, requests -> {
            if (requests != null) {
                requestListAdapter.setData(requests);
                noRequestLayout.setVisibility(View.GONE);
                requestListLayout.setVisibility(View.VISIBLE);
            }
        });

        getUserList();
        populateSpinner();

        return rootView;
    }

    @OnClick(R.id.btn_new_request)
    public void createNewRequest() {
        CreateNewRequestActivity.StartActivity(getActivity());
    }

    @OnClick(R.id.fab_create_new_request)
    public void fabCreateNewRequest() {

    }

    public void getUserList() {
        String token = Tools.getSharedPreferences(getActivity()).getSavedUserData().getJwt();
        viewModel.fetchRequestList(token);
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_new_request, getResources().getStringArray(R.array.requestArray));
        spinnerRequestItems.setAdapter(adapterGray);
    }
}
