package com.Project.Closet.social.space;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.social.subfragment.Fragment_Feed;
import com.Project.Closet.social.subfragment.Fragment_UserspaceFeed;

public class TabPagerAdapter_space extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_space(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return Fragment_UserspaceFeed.newInstance("my","medium");
            case 1:
                return Fragment_UserspaceFeed.newInstance("heart","medium");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
