package com.Project.Closet.closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.fragment.TabFragment_Clothes_inCloset;

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
                return new TabFragment_Clothes_inCloset("all", "small");
            case 1:
                return new TabFragment_Clothes_inCloset("top", "small");
            case 2:
                return new TabFragment_Clothes_inCloset("bottom", "small");
            case 3:
                return new TabFragment_Clothes_inCloset("suit", "small");
            case 4:
                return new TabFragment_Clothes_inCloset("outer", "small");
            case 5:
                return new TabFragment_Clothes_inCloset("shoes", "small");
            case 6:
                return new TabFragment_Clothes_inCloset("bag", "small");
            case 7:
                return new TabFragment_Clothes_inCloset("accessory", "small");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
