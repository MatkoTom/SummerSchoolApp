package com.example.summerschoolapp.view.main.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.summerschoolapp.view.main.NewsFragment;
import com.example.summerschoolapp.view.main.RequestsFragment;
import com.example.summerschoolapp.view.main.UserFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else if (position == 1) {
            return new RequestsFragment();
        } else if (position == 2) {
            return new UserFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3; // Show 3 total pages.
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Vijesti";
            case 1:
                return "Zahtjevi";
            case 2:
                return "Profil";
        }
        return null;
    }
}