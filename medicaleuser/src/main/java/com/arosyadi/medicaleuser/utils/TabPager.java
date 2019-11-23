package com.arosyadi.medicaleuser.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.arosyadi.medicaleuser.fragment.CompletedFragment;
import com.arosyadi.medicaleuser.fragment.OngoingFragment;
import com.arosyadi.medicaleuser.fragment.ScheduledFragment;

public class TabPager extends FragmentPagerAdapter {

    private static final int NUMBER_ITEM = 3;

    public TabPager(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new OngoingFragment();
            case 2:
                return new CompletedFragment();
            default:
                return new ScheduledFragment();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_ITEM;
    }
}
