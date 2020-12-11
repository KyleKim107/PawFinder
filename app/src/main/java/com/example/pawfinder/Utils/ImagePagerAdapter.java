package com.example.pawfinder.Utils;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;

    public ImagePagerAdapter(FragmentManager fm, int behavior, ArrayList<Fragment> fragments) {
        super(fm, behavior);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
