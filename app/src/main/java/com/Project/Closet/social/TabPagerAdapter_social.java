package com.Project.Closet.social;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.social.subfragment.Fragment_Feed;

public class TabPagerAdapter_social extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_social(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return Fragment_Feed.newInstance("following","xLarge");
            case 1:
                return Fragment_Feed.newInstance("best","xLarge");
            case 2:
                return Fragment_Feed.newInstance("newest","xLarge");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
