package com.Project.Closet.closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Clothes_small;

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
                return new TabFragment_Clothes_small("all");
            case 1:
                return new TabFragment_Clothes_small("top");
            case 2:
                return new TabFragment_Clothes_small("bottom");
            case 3:
                return new TabFragment_Clothes_small("suit");
            case 4:
                return new TabFragment_Clothes_small("outer");
            case 5:
                return new TabFragment_Clothes_small("shoes");
            case 6:
                return new TabFragment_Clothes_small("bag");
            case 7:
                return new TabFragment_Clothes_small("accessory");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
