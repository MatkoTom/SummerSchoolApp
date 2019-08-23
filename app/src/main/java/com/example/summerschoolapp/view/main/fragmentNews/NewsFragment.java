package com.example.summerschoolapp.view.main.fragmentNews;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.News;
import com.example.summerschoolapp.utils.Const;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.editNews.EditNewsActivity;
import com.example.summerschoolapp.view.main.MainScreenViewModel;
import com.example.summerschoolapp.view.newNews.CreateNewNewsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    @BindView(R.id.news_list_layout)
    ConstraintLayout newsListLayout;

    @BindView(R.id.no_news_layout)
    ConstraintLayout noNewsLayout;

    @BindView(R.id.btn_publish_news)
    Button btnPublishNews;

    @BindView(R.id.fab_create_new_news)
    FloatingActionButton fabCreateNewNews;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    public NewsFragment() {
        // Required empty public constructor
    }

    private MainScreenViewModel mainScreenActivityViewModel;
    private NewsFragmentViewModel newsFragmentViewModel;
    private NewsListAdapter newsListAdapter;

    //TODO implement logic for user and admin for news
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        newsListAdapter = new NewsListAdapter(new NewsListAdapter.NewsListInteraction() {
            @Override
            public void onNewsClicked(News news) {
                EditNewsActivity.StartActivity(getContext(), news);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNews.setLayoutManager(layoutManager);
        rvNews.setAdapter(newsListAdapter);

        mainScreenActivityViewModel = ViewModelProviders.of(this).get(MainScreenViewModel.class);
        newsFragmentViewModel = ViewModelProviders.of(this).get(NewsFragmentViewModel.class);

        newsFragmentViewModel.getNewsList().observeEvent(this, news -> {
            if (news.size() > 0 && Integer.parseInt(Tools.getSharedPreferences(getActivity()).getSavedUserData().getRole()) == Const.Preferences.ADMIN_ROLE) {
                newsListAdapter.setData(news);
                noNewsLayout.setVisibility(View.GONE);
                newsListLayout.setVisibility(View.VISIBLE);
                fabCreateNewNews.setVisibility(View.VISIBLE);
            } else if (news.size() == 0 && Integer.parseInt(Tools.getSharedPreferences(getActivity()).getSavedUserData().getRole()) == Const.Preferences.ADMIN_ROLE) {
                noNewsLayout.setVisibility(View.VISIBLE);
                newsListAdapter.clearList(news);
            } else if (news.size() > 0 && Integer.parseInt(Tools.getSharedPreferences(getActivity()).getSavedUserData().getRole()) == Const.Preferences.USER_ROLE) {
                newsListAdapter.setData(news);
                noNewsLayout.setVisibility(View.GONE);
                newsListLayout.setVisibility(View.VISIBLE);
            }
        });

        checkUserRole();
        getNewsList();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void getNewsList() {
        newsFragmentViewModel.fetchNewsList(Tools.getSharedPreferences(getActivity()).getSavedUserData().getJwt());
    }

    public void checkUserRole() {
        if (newsFragmentViewModel.isAdmin()) {
            btnPublishNews.setVisibility(View.VISIBLE);
        } else {
            btnPublishNews.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.fab_create_new_news)
    public void fabCreateNews() {
        CreateNewNewsActivity.StartActivity(getActivity());
    }

    @OnClick(R.id.btn_publish_news)
    public void publishNewNews() {
        CreateNewNewsActivity.StartActivity(getActivity());
    }
}
