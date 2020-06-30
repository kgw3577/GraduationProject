package com.Project.Closet.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Clothes_inHome;
import com.Project.Closet.fragment.TabFragment_Codi_large;

public class TabPagerAdapter_home extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_home(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return TabFragment_Clothes_inHome.newInstance("favorite","medium");
            case 1:
                return TabFragment_Codi_large.newInstance("favorite");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
