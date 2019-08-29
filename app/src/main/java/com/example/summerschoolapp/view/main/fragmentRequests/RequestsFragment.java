package com.example.summerschoolapp.view.main.fragmentRequests;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseFragment;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.editRequest.EditRequestActivity;
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

    @BindView(R.id.btn_new_request)
    Button btnNewRequest;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private RequestListAdapter requestListAdapter;
    private RequestFragmentViewModel viewModel;

    public RequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, rootView);

        requestListAdapter = new RequestListAdapter(request -> EditRequestActivity.StartActivity(getContext(), request));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRequests.setLayoutManager(layoutManager);
        rvRequests.setAdapter(requestListAdapter);

        viewModel = ViewModelProviders.of(this).get(RequestFragmentViewModel.class);

        viewModel.getRequestList().observeEvent(this, requests -> {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }

            if (requests.size() > 0 && viewModel.isAdmin()) {
                requestListAdapter.setData(requests);
                noRequestLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            } else if (requests.size() == 0 && viewModel.isAdmin()) {
                noRequestLayout.setVisibility(View.VISIBLE);
                requestListAdapter.clearList(requests);
            } else if (requests.size() > 0 && !viewModel.isAdmin()) {
                requestListAdapter.setData(requests);
                noRequestLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                fabCreateNewRequest.setVisibility(View.VISIBLE);
            } else if (requests.size() == 0 && !viewModel.isAdmin()) {
                btnNewRequest.setVisibility(View.VISIBLE);
                requestListAdapter.clearList(requests);
            }
        });

        getRequestList();
        populateSpinner();
        filterRequests();
        swipeRefresh();

        return rootView;
    }

    public void swipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> getRequestList());
    }

    @OnClick(R.id.btn_new_request)
    public void createNewRequest() {
        CreateNewRequestActivity.StartActivity(getActivity());
    }

    @OnClick(R.id.fab_create_new_request)
    public void fabCreateNewRequest() {
        CreateNewRequestActivity.StartActivity(getActivity());
    }

    public void getRequestList() {
        viewModel.printRequestList();
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapterGray = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_new_request, getResources().getStringArray(R.array.requestFilter));
        spinnerRequestItems.setAdapter(adapterGray);
    }

    private void filterRequests() {
        if (Integer.parseInt(Tools.getSharedPreferences(getActivity()).getSavedUserData().getRole()) == Const.Preferences.USER_ROLE) {
            spinnerRequestItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String query = adapterView.getItemAtPosition(position).toString();
                    if (query.equals(getString(R.string.all))) {
                        viewModel.printRequestList();
                    } else {
                        viewModel.printFilteredRequestList(query);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //ignore
                }
            });
        } else {
            spinnerRequestItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String query = adapterView.getItemAtPosition(position).toString();
                    if (query.equals(getString(R.string.all))) {
                        viewModel.printRequestList();
                    } else {
                        viewModel.printFilteredRequestList(query);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //ignore
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestList();
    }
}
