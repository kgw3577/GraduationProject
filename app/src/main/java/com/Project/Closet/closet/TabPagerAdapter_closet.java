package com.Project.Closet.closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter_closet extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_closet(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return new TabFragment_allClothes();
            case 1:
                return new TabFragment_top();
            case 2:
                return new TabFragment_bottom();
            case 3:
                return new TabFragment_suit();
            case 4:
                return new TabFragment_outer();
            case 5:
                return new TabFragment_shoes();
            case 6:
                return new TabFragment_bag();
            case 7:
                return new TabFragment_accessory();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
