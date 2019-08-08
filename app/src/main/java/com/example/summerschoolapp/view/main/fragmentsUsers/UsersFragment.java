package com.example.summerschoolapp.view.main.fragmentsUsers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;

    @BindView(R.id.sv_user_search)
    androidx.appcompat.widget.SearchView svUserSearch;

    private UserListAdapter userListAdapter;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, rootView);
        userListAdapter = new UserListAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(userListAdapter);
        addUsers();
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
                userListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void addUsers() {
        List<User> example = new ArrayList<>();
        example.add(new User("sldjfsl.lsjdf@glkdg.com", "saasd"));
        example.add(new User("bnvnvb.lsjdf@glkdg.com", "saasd"));
        example.add(new User("dshreh.lsjdf@glkdg.com", "saasd"));
        example.add(new User("popo.lsjdf@glkdg.com", "saasd"));
        example.add(new User("oiuoiu.lsjdf@glkdg.com", "saasd"));

        userListAdapter.setData(example);
    }

}
