package com.beautyteam.smartkettle.Fragments.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.beautyteam.smartkettle.Fragments.NewsFragment;
import com.beautyteam.smartkettle.MainActivity;

/**
 * Created by Admin on 25.10.2014.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 3;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.getInstance(position);
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

