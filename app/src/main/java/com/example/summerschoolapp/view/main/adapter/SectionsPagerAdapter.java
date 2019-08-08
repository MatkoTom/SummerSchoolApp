package com.example.summerschoolapp.view.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.main.NewsFragment;
import com.example.summerschoolapp.view.main.ProfileFragment;
import com.example.summerschoolapp.view.main.RequestsFragment;
import com.example.summerschoolapp.view.main.UsersFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String tabTitles[] = new String[]{"Vijesti", "Zahtjevi", "Profil", "Korisnici"};
    private int[] imageResId = {R.drawable.nav_news_icon, R.drawable.nav_requests_icon, R.drawable.nav_users_icon, R.drawable.nav_users_icon};
    private int[] imageResIdChecked = {R.drawable.nav_news_selected_icon,
            R.drawable.nav_requests_selected_icon,
            R.drawable.nav_users_selected_icon,
            R.drawable.nav_users_selected_icon};

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
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
            return null;
        }
    }

    //TODO get user from SharedPref and check role. Change number of pages accordingly
    @Override
    public int getCount() {
        return 4; // Show 4 total pages.
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        Button btnTab = v.findViewById(R.id.btn_tab_icon);
        btnTab.setCompoundDrawablesWithIntrinsicBounds(null, image, null, null);
        btnTab.setText(tabTitles[position]);
        return v;
    }
}