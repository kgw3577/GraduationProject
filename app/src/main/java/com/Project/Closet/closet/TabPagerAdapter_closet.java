package com.Project.Closet.closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.Project.Closet.home.TabFragment1;
import com.Project.Closet.home.TabFragment2;

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
                TabFragment_allClothes tabFragment_allClothes = new TabFragment_allClothes();
                return tabFragment_allClothes;
            case 1:
                TabFragment_top tabFragment_top = new TabFragment_top();
                return tabFragment_top;
            case 2:
                TabFragment_bottom tabFragment_bottom = new TabFragment_bottom();
                return tabFragment_bottom;
            case 3:
                TabFragment_suit tabFragment_suit = new TabFragment_suit();
                return tabFragment_suit;
            case 4:
                TabFragment_outer tabFragment_outer = new TabFragment_outer();
                return tabFragment_outer;
            case 5:
                TabFragment_shoes tabFragment_shoes = new TabFragment_shoes();
                return tabFragment_shoes;
            case 6:
                TabFragment_bag tabFragment_bag = new TabFragment_bag();
                return tabFragment_bag;
            case 7:
                TabFragment_accessory tabFragment_accessory = new TabFragment_accessory();
                return tabFragment_accessory;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
