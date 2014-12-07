package com.beautyteam.smartkettle.Fragments.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.beautyteam.smartkettle.Fragments.DevicesFragment;
import com.beautyteam.smartkettle.Fragments.NewsFragment;
import com.beautyteam.smartkettle.MainActivity;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsFragment.getInstance();
            case 1:
                return DevicesFragment.getInstance();
        }
        return NewsFragment.getInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.screenNames[position];
    }

}

