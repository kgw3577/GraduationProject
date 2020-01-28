package com.Project.Closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                TabFragment1 tabFragment1 = new TabFragment1();
                return tabFragment1;
            case 1:
                TabFragment2 tabFragment2 = new TabFragment2();
                return tabFragment2;
            case 2:
                TabFragment3 tabFragment3 = new TabFragment3();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
