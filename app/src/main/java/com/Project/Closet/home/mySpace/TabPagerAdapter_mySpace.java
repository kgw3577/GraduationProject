package com.Project.Closet.home.mySpace;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.social.space.subfragment.Fragment_UserspaceFeed;

public class TabPagerAdapter_mySpace extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_mySpace(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return Fragment_MyspaceFeed.newInstance("my","medium");
            case 1:
                return Fragment_MyspaceFeed.newInstance("heart","medium");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
