package com.example.summerschoolapp.view.main.fragmentNews;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.MapsActivity;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.main.MainScreenViewModel;
import com.example.summerschoolapp.view.newNews.CreateNewNewsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    @BindView(R.id.btn_publish_news)
    Button btnPublishNews;

    public NewsFragment() {
        // Required empty public constructor
    }

    private MainScreenViewModel mainScreenActivityViewModel;
    private NewsFragmentViewModel newsFragmentViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        mainScreenActivityViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);
        newsFragmentViewModel = ViewModelProviders.of(this).get(NewsFragmentViewModel.class);

        checkUserRole();
        // Inflate the layout for this fragment
        return rootView;
    }

    public void checkUserRole() {
        // TODO @Matko
        // better to add this logic to fragments viewModel or host activity viewModel if necessary there, another option would be to add it to Tools
        // better way to check this would be Method.isAdmin();
        // 1 constant should be stored in Const as Const.ADMIN_ROLE
        if (Integer.parseInt(Tools.getSharedPreferences(getActivity()).getSavedUserData().getRole()) == 1) {
            btnPublishNews.setVisibility(View.VISIBLE);
        } else {
            btnPublishNews.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_publish_news)
    public void publishNewNews() {
       CreateNewNewsActivity.StartActivity(getActivity());
    }
}
