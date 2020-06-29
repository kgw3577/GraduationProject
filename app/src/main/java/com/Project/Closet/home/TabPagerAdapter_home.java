package com.Project.Closet.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Clothes_medium;
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
                return new TabFragment_Clothes_medium("like"); //favorite으로 추후 수정
            case 1:
                return new TabFragment_Codi_large("favorite");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
