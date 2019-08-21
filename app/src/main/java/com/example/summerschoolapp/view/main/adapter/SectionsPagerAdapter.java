package com.example.summerschoolapp.view.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.main.fragmentNews.NewsFragment;
import com.example.summerschoolapp.view.main.fragmentProfile.ProfileFragment;
import com.example.summerschoolapp.view.main.fragmentRequests.RequestsFragment;
import com.example.summerschoolapp.view.main.fragmentsUsers.UsersFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else if (position == 1) {
            return new RequestsFragment();
        } else if (position == 2) {
            return new ProfileFragment();
        } else if (position == 3) {
            return new UsersFragment();
        } else {
            // TODO @Matko
            // empty view fragment needs to be returned here or a default position
            return null;
        }
    }

    @Override
    public int getCount() {
        if (Tools.getSharedPreferences(context).getSavedUserData().getRole() != null) {
            if (Integer.parseInt(Tools.getSharedPreferences(context).getSavedUserData().getRole()) == 2) {
                return 3;
            } else {
                return 4; // Show 4 total pages.
            }
        } else {
            return 3;
        }
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        String[] tabTitles = new String[]{context.getString(R.string.news),
                context.getString(R.string.requests),
                context.getString(R.string.profile), context.getString(R.string.users)};
        int[] imageResources = {R.drawable.button_news_states, R.drawable.button_requests_states, R.drawable.button_users_states, R.drawable.button_users_states};

        View customTabView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        Drawable image = context.getResources().getDrawable(imageResources[position]);
        Button btnTab = customTabView.findViewById(R.id.btn_tab_icon);
        btnTab.setCompoundDrawablesWithIntrinsicBounds(null, image, null, null);
        btnTab.setText(tabTitles[position]);
        return customTabView;
    }
}