package com.Project.Closet.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.home.recommend.recommendPagerFragment;
import com.Project.Closet.home.subfragment.TabFragment_Clothes_inHome;
import com.Project.Closet.subfragment.TabFragment_Codi_large;

public class TabPagerAdapter_recommend extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter_recommend(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                //return recommendPagerFragment.newInstance("favorite");
            case 1:
                //return recommendPagerFragment.newInstance("favorite");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
