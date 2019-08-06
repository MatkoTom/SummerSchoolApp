package com.example.summerschoolapp.view.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.view.main.NewsFragment;
import com.example.summerschoolapp.view.main.RequestsFragment;
import com.example.summerschoolapp.view.main.ProfileFragment;
import com.example.summerschoolapp.view.main.UsersFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String tabTitles[] = new String[] { "Vijesti", "Nalozi", "Profil", "Korisnici" };
    private int[] imageResId = { R.drawable.nav_news_icon, R.drawable.nav_requests_icon, R.drawable.nav_users_icon, R.drawable.nav_users_icon };

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
        }else {
            return null;
        }
    }
    //TODO get user from SharedPref and check role. Change number of pages accordingly
    @Override
    public int getCount() {
        return 4; // Show 3 total pages.
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
//        switch (position) {
//            case 0:
//                return "Vijesti";
//            case 1:
//                return "Zahtjevi";
//            case 2:
//                return "Profil";
//        }
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = v.findViewById(R.id.imgView);
        img.setImageResource(imageResId[position]);
        return v;
    }
}