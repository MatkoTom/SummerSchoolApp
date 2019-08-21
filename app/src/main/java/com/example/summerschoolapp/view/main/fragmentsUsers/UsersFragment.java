package com.example.summerschoolapp.view.main.fragmentsUsers;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.newUser.CreateNewUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    @BindView(R.id.fab_create_new_user)
    FloatingActionButton fabCreateNewUser;

    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;

    @BindView(R.id.sv_user_search)
    androidx.appcompat.widget.SearchView svUserSearch;

    private UserListAdapter userListAdapter;
    private UsersFragmentViewModel viewModel;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, rootView);

        userListAdapter = new UserListAdapter(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(userListAdapter);

        viewModel = ViewModelProviders.of(this).get(UsersFragmentViewModel.class);

        viewModel.getRecyclerList().observeEvent(this, users -> {
            userListAdapter.setData(users);
        });

        getUserList();
        searchUsers();
        return rootView;
    }

    private void searchUsers() {
        svUserSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String token = Tools.getSharedPreferences(getActivity()).getSavedUserData().getJwt();
                viewModel.getSearchedUsersList(token, newText);
                return false;
            }
        });
    }

    @OnClick(R.id.fab_create_new_user)
    public void startCreateUserActivity() {
       CreateNewUserActivity.StartActivity(getActivity());
    }

    public void getUserList() {
        String token = Tools.getSharedPreferences(getActivity()).getSavedUserData().getJwt();
        viewModel.getUserList(token);
    }
}
